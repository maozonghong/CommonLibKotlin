package com.mao.library.manager


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.util.Log

import com.mao.library.abs.AbsApplication

import org.json.JSONObject

import java.io.IOException
import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap

import okhttp3.Call
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import okio.GzipSink
import okio.Okio

/**
 * Created by maozonghong
 * on 2019/11/21
 */
class OkHttpManager private constructor() {

    enum class MethodType {
        Post, Get, Restful
    }

    class GzipRequestInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest)
            }

            val compressedRequest = originalRequest.newBuilder()
                .header("Content-Encoding", "gzip")
                .method(originalRequest.method(), gzip(originalRequest.body()))
                .build()
            return chain.proceed(compressedRequest)
        }

        private fun gzip(body: RequestBody?): RequestBody {
            return object : RequestBody() {
                override fun contentType(): MediaType? {
                    return body!!.contentType()
                }

                override fun contentLength(): Long {
                    return -1 // 无法提前知道压缩后的数据大小
                }

                @Throws(IOException::class)
                override fun writeTo(sink: BufferedSink) {
                    val gzipSink = Okio.buffer(GzipSink(sink))
                    body!!.writeTo(gzipSink)
                    gzipSink.close()
                }
            }
        }
    }

    companion object {
        private var gzip_enable = false

        private val requests = ConcurrentHashMap<Int, Call>()

        private var gzipRequestInterceptor: GzipRequestInterceptor? = null

        @JvmStatic
        fun isNetworkAvailable():Boolean{
            val connectivity =
                AbsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity == null) {
                return false
            } else {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        @JvmStatic
        fun isNetworkOnWifi():Boolean{
            val connectivity = AbsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            if (connectivity != null) {
                val info=connectivity.allNetworks
                if (info != null) {
                    for (i in info.indices) {
                        var networkInfo= connectivity.getNetworkInfo(info[i])
                        if (networkInfo.typeName== "WIFI" &&networkInfo.isConnected) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        //网络类型为运营商（移动/联通/电信）
        @JvmStatic
        fun isNetworkOnMobile():Boolean{
            val connectivity =
                AbsApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivity.activeNetworkInfo ?: return false
            val type1 = activeNetworkInfo.type
            return type1 == ConnectivityManager.TYPE_MOBILE
        }



        @Throws(Exception::class)
        @JvmStatic
        fun getResponse(id: Int, url: String, params: Map<String, String>, type: MethodType): Response? {
            return getResponse(id, url, null, params, type)
        }

        @Throws(Exception::class)
        @JvmStatic
        fun getResponse(id: Int, url: String, params: Map<String, String>): Response? {
            return getResponse(id, url, null, params, MethodType.Post)
        }

        @Throws(Exception::class)
        @JvmOverloads
        @JvmStatic
        fun getResponse(id: Int, url: String, headers: HashMap<String, String>?, params: Map<String, String>, type: MethodType = MethodType.Post): Response? {
            return getResponse(id, url, headers, null, params, type)
        }

        @Throws(Exception::class)
        @JvmOverloads
        @JvmStatic
        fun getResponse(id: Int, url: String, headers: HashMap<String, String>?, mpEntity: MultipartBody?, params: Map<String, String>? = null, type: MethodType = MethodType.Post): Response? {
            val requestBuilder = Request.Builder()
            var call: Call? = null
            var requestBody: RequestBody? = null
            var response: Response? = null
            when (type) {
                MethodType.Post -> {
                    var isJsonContent = false
                    requestBuilder.url(url)
                    if (mpEntity != null) {
                        requestBody = mpEntity
                    } else if (params != null && params.isNotEmpty()) {
                        if (headers != null) {
                            for (key in headers.keys) {
                                if (key.equals("Content-Type", ignoreCase = true) && headers[key]!!.startsWith("application/json")) {
                                    isJsonContent = true
                                    break
                                }
                            }
                        }
                        if (isJsonContent) {
                            var jsonStr:String?=null
                            var jsonObject=JSONObject()
                            for ((key, value) in params) {
                                if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)) {
                                    if(key == "null"){
                                        jsonStr=value
                                    }else{
                                        jsonObject.put(key, value)
                                    }
                                }
                            }
                            requestBody = RequestBody.create(MediaType.get("application/json"),jsonStr?:jsonObject.toString())
                        } else {
                            val builder = FormBody.Builder()
                            for ((key, value) in params) {
                                if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)) {
                                    builder.add(key, value)
                                }
                            }
                            requestBody = builder.build()
                        }
                    }
                    if (requestBody != null) {
                        requestBuilder.post(requestBody)
                    } else {
                        requestBuilder.post(RequestBody.create(null, ""))
                    }
                }
                MethodType.Get -> {
                    var builder = StringBuilder(url)
                    if (params != null) {
                        builder.append("?")
                        for ((key, value) in params) {
                            builder.append(key)
                            builder.append("=")
                            builder.append(value)
                            builder.append("&")
                        }
                        builder.deleteCharAt(builder.length - 1)
                    }
                    requestBuilder.url(builder.toString())
                }

                MethodType.Restful -> {
                   var builder = StringBuilder(url)
                    if (!url.endsWith("/")) {
                        builder.append("/")
                    }
                    if (params != null) {
                        for ((_, value) in params) {
                            builder.append(value)
                            builder.append("/")
                        }
                    }
                    builder.deleteCharAt(builder.lastIndex)
                    requestBuilder.url(builder.toString())
                }
            }
            if (headers != null) {
                addHeaders(headers, requestBuilder)
            }

            try {
                call = OkHttpInstanceManager.httpClient.newCall(requestBuilder.build())
                requests[id] = call!!
                response = call.execute()
            } catch (e: Exception) {
                e.printStackTrace()
                if (!call!!.isCanceled) {
                    call.cancel()
                    throw Exception("网络异常")
                }
            } finally {
                requests.remove(id)
            }

            if (response == null) {
                return null
            }

            if (response.isSuccessful) {

            } else {
                response.close()
                throw Exception("网络异常:" + response.code() + ":" + url)
            }
            return response
        }

        fun getResponseByRestful(id: Int, url: String, headers: HashMap<String, String>, list: Map<String, String>): Response? {
            return null
        }

        private fun addHeaders(headers: HashMap<String, String>, request: Request.Builder) {
            for (key in headers.keys) {
                request.addHeader(key, headers[key]!!)
            }
        }

        /**
         * 取消当前网络请求
         *
         * @param id
         */
        @JvmStatic
        fun cancel(id: Int) {
            if (requests != null) {
                val request = requests[id]
                if (request != null) {
                    requests.remove(id)
                    ThreadPoolManager.cacheExecute(Runnable { request.cancel() })
                }
            }
        }

        @JvmStatic
        fun setGzip_enable(gzip_enable: Boolean) {
            OkHttpManager.gzip_enable = gzip_enable
            if (gzip_enable) {
                gzipRequestInterceptor = GzipRequestInterceptor()
                OkHttpInstanceManager.httpClient.interceptors().add(gzipRequestInterceptor)
            } else {
                if (gzipRequestInterceptor != null) {
                    OkHttpInstanceManager.httpClient.interceptors().remove(gzipRequestInterceptor)
                    gzipRequestInterceptor = null
                }
            }
        }
    }
}
