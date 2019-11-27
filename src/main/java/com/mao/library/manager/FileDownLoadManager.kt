package com.mao.library.manager

import android.os.Looper
import android.text.TextUtils
import android.util.Log

import com.mao.library.bean.DownloadInfo
import com.mao.library.dbHelper.DownloadInfoDbHelper
import com.mao.library.utils.FileUtils
import com.mao.library.utils.MainHandlerUtil

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile
import java.util.HashSet
import java.util.concurrent.ConcurrentHashMap

import okhttp3.Request
import okhttp3.Response

/**
 * 文件下载管理器
 * Created by maozonghong
 * on 2019/11/21
 */
object FileDownLoadManager {

    private val downloadingUrls = ConcurrentHashMap<String, DownloadObserverSet>()

    private val tempSet = DownloadObserverSet()

    fun download(url: String, observer: DownloadObserver?): File? {
        return download(url, true, observer)
    }

    @JvmOverloads
    fun download(url: String, isCache: Boolean = true, observer: DownloadObserver? = null): File? {
        return download(url, FileUtils.urlToFilename(url, isCache), observer)
    }

    @JvmOverloads
    fun download(url: String, filePath: String, observer: DownloadObserver? = null): File? {
        return download(url, filePath, null, observer)
    }

    /**
     * 主线程中调用必然返回null
     *
     * @param url
     * @param observer
     * @return
     */
    private fun download(url: String, filePath: String, fileDir: String?, observer: DownloadObserver?): File? {
        var file: File? = null

        if (downloadingUrls.containsKey(url)) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                if (observer != null) {
                    getObservers(url).add(observer)
                }
            } else {
                val observers = getObservers(url)

                synchronized(downloadingUrls) {
                    if (observer != null) {
                        observers.add(observer)
                    }
                }

                synchronized(observers) {
                    try {
                        (observers as Object).wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }

                file = observers.file
            }
        } else {
            if (observer != null) {
                getObservers(url).add(observer)
            } else {
                downloadingUrls[url] = tempSet
            }

            if (Looper.myLooper() == Looper.getMainLooper()) {
                ThreadPoolManager.downloadExecute(Runnable {
                    tryDownload(url, filePath, fileDir)
                    downloadingUrls.remove(url)
                })
            } else {
                file = tryDownload(url, filePath, fileDir)
                downloadingUrls.remove(url)
            }
        }
        return file
    }

    @Synchronized
    private fun getObservers(url: String): DownloadObserverSet {
        var observers = downloadingUrls[url]
        if (observers == null || observers === tempSet) {
            observers = DownloadObserverSet()
            downloadingUrls[url] = observers
        }
        return observers
    }

    fun isDownloading(url: String): Boolean {
        return downloadingUrls.containsKey(url)
    }

    private fun tryDownload(url: String, filePath: String, fileDir: String?): File? {
        var filePath = filePath
        if (TextUtils.isEmpty(filePath)) {
            if (!TextUtils.isEmpty(fileDir)) {
                filePath = File(fileDir, FileUtils.getFilename(url)).path
            } else {
                return null
            }
        }

        val file = File(filePath)
        if (file.exists()) {
            onDownloadFinish(url, file, true)
            return file
        }

        var result: DownloadResult?

        var repeatCount = 0

        do {
            repeatCount++
            result = downloadFromNet(url, filePath)
        } while (result!!.file == null && !result.isCancel && repeatCount < 5)

        if (!result.isCancel) {
            if (result.file != null && result.file!!.exists()) {
                onDownloadFinish(url, result.file, false)
            } else {
                onDownloadFail(url, result.error_code)
            }
        }
        return file
    }

    private fun downloadFromNet(url: String, filePath: String): DownloadResult {
        val result = DownloadResult()

        val file = File(filePath)
        file.parentFile!!.mkdirs()

        val temp = File(FileUtils.getTempUrl(filePath))
        temp.parentFile!!.mkdirs()
        var response: Response? = null
        var inputStream: InputStream? = null
        var fs: RandomAccessFile? = null
        var info: DownloadInfo? = null
        val tempInfo: DownloadInfo?
        var count: Long = 0
        try {
            val dbHelper = DownloadInfoDbHelper()
            tempInfo = dbHelper.queryOne(url)
            if (tempInfo != null) {
                if (temp.exists()) {
                    info = tempInfo
                    info.start = tempInfo.read.toLong()
                } else {
                    dbHelper.delete(tempInfo)
                }
            } else if (temp.exists()) {
                temp.delete()
            }
            dbHelper.close()
            if (info == null) {
                info = DownloadInfo(url)
            }
            if (!temp.exists()) {
                temp.createNewFile()
            }
            val requestBuild = Request.Builder().url(url)
            if (info.start != 0L) {
                val builder = StringBuilder("bytes=")
                builder.append(info.start)
                builder.append("-")
                requestBuild.addHeader("RANGE", builder.toString())
            }
            response = OkHttpInstanceManager.httpClient.newCall(requestBuild.build()).execute()

            when (val code = response?.code()) {
                200, 206 -> {
                    val buffer = ByteArray(1024)
                    count = response.body()!!.contentLength() + info.start
                    onDownloadStart(url, count)
                    fs = RandomAccessFile(temp, "rw")
                    if (count > 0) {
                        fs.setLength(count)
                    }

                    if (info.start > 0) {
                        fs.seek(info.start)
                    }

                    if (info.read > 0) {
                        info.newPercent = info.read.toFloat() / count.toFloat() * 100
                        info.percent = info.newPercent.toInt()
                    }

                    if (info.percent > 0) {
                        onDownloadInProgress(url, info.percent)
                    }

                    inputStream = response.body()!!.byteStream()
                    if (!download(url, response, inputStream!!, fs, buffer, count, info)) {
                        result.isCancel = true
                        result.file = file
                        return result
                    }
                }

                else -> {
                    Log.i("mao", "download error : " + url + " : " + code + " : " + response.code())
                    result.error_code = code?:0
                    result.file = null
                    return result
                }
            }
        } catch (e: Exception) {
            Log.i("mao", "下载失败:" + url + ":" + e.javaClass.simpleName + ":" + e.message)
            e.printStackTrace()
            if (info != null && info.read > 0) {
                val dbHelper = DownloadInfoDbHelper()
                dbHelper.saveOrUpdate(info, false)
                dbHelper.close()
            }
        } finally {
            fs?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            inputStream?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            response?.let {
                try {
                    response.close()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }

        if (info != null && info.end > 0) {
            info.percent=100
            info.newPercent=info.percent.toFloat()
            onDownloadInProgress(url, 100)
        }

        //        if (tempInfo != null) {
        //            DownloadInfoDbHelper dbHelper = new DownloadInfoDbHelper();
        //            dbHelper.delete(tempInfo);
        //            dbHelper.close();
        //        }

        if (info != null && info.read > 0 && info.read.toLong() == count) {
            FileUtils.renameTo(temp, file)
            result.file = file
        } else {
            // temp.delete();
            result.file = null
        }
        return result
    }


    @Throws(IOException::class)
    private fun download(url: String, response: Response, inputStream: InputStream, fs: RandomAccessFile,
        buffer: ByteArray, count: Long, info: DownloadInfo): Boolean {
        var read: Int
        while ((inputStream.read(buffer).also { read=it })!=-1) {
            if (isCanceled(url)) {
                Log.i("mao", "cancel:" + info.read + ":" + count)
                fs.close()
                inputStream.close()
                response.close()

                val dbHelper = DownloadInfoDbHelper()
                dbHelper.saveOrUpdate(info, false)
                dbHelper.close()

                return false
            }

            fs.write(buffer, 0, read)

            if (count > 0) {
                info.read += read
                info.newPercent = info.read / count.toFloat() * 100

                if (info.newPercent > 100) {
                    info.newPercent = 100f
                }

                if (info.newPercent - info.percent >= 1) {
                    info.percent = info.newPercent.toInt()
                    onDownloadInProgress(url, info.percent)
                }
            }
        }
        return true
    }

    private fun onDownloadFinish(url: String, file: File?, isExists: Boolean) {
        val observers = downloadingUrls[url]
        if (observers != null && observers !== tempSet) {
            observers.file = file
            synchronized(observers) {
                (observers as Object).notifyAll()
            }
            MainHandlerUtil.post(Runnable {
                synchronized(downloadingUrls) {
                    if (isExists) {
                        for (downloadObserver in observers) {
                            downloadObserver.onFileExists(url, file)
                        }
                    } else {
                        for (downloadObserver in observers) {
                            downloadObserver.onDownloadFinish(url, file)
                        }
                    }
                }
            })
        }
    }

    private fun onDownloadStart(url: String, count: Long) {
        val observers = downloadingUrls[url]
        if (observers != null && observers !== tempSet) {
            MainHandlerUtil.post(Runnable{
                synchronized(downloadingUrls) {
                    for (downloadObserver in observers) {
                        downloadObserver.onDownloadStart(url, count)
                    }
                }
            })
        }
    }

    private fun onDownloadInProgress(url: String, percent: Int) {
        val observers = downloadingUrls[url]
        if (observers != null && observers !== tempSet) {
            MainHandlerUtil.post(Runnable{
                synchronized(downloadingUrls) {
                    for (downloadObserver in observers) {
                        downloadObserver.onDownloadInProgress(url, percent)
                    }
                }
            })
        }
    }

    private fun onDownloadFail(url: String, error_code: Int) {
        val observers = downloadingUrls[url]
        if (observers != null && observers !== tempSet) {
            synchronized(observers) {
                (observers as Object).notifyAll()
            }
            MainHandlerUtil.post(Runnable{
                synchronized(downloadingUrls) {
                    for (downloadObserver in observers) {
                        downloadObserver.onDownloadFail(url, error_code, "下载失败")
                    }
                }
            })
        }
    }

    private fun isCanceled(url: String): Boolean {
        val observers = downloadingUrls[url]
        return if (observers != null && observers !== tempSet) {
            observers.isCanceled
        } else false
    }

    fun stopDownload(url: String) {
        val observers = downloadingUrls[url]
        if (observers != null && observers !== tempSet) {
            observers.isCanceled = true
        }
    }

    private class DownloadObserverSet : HashSet<DownloadObserver>() {
        internal var file: File? = null
        internal var isCanceled: Boolean = false
    }

    private class DownloadResult {
        internal var file: File? = null
        internal var isCancel: Boolean = false
        internal var error_code: Int = 0
    }

    abstract class DownloadObserver {
        private var any: Any?=null

        fun onFileExists(url: String, file: File?) {
            onDownloadFinish(url, file)
        }

        abstract fun onDownloadFinish(url: String, file: File?)

        open fun onDownloadStart(url: String, count: Long) {}

        open fun onDownloadInProgress(url: String, percent: Int) {}

        open fun onDownloadFail(url: String, error_code: Int?, error: String?) {}

        constructor() {}

        constructor(any: Any) {
            this.any = any
        }
    }
}
/**
 * 主线程中调用必然返回null,使用[.download]来处理回调
 */
