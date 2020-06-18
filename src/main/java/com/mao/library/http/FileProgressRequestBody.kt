package com.mao.library.http

import com.mao.library.interfaces.OnFileUploadProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import okio.Source
import java.io.File


/**
 * Created by maozonghong
 * on 2020/6/18
 */
class FileProgressRequestBody: RequestBody {

    @Volatile
    private var listener: OnFileUploadProgressListener? = null
    private var transferred:Long = 0
    private var totalSize:Long = 0
    var percent = 0

    private var SEGMENT_SIZE:Long = 2 * 1024 // okio.Segment.SIZE

    private var file: File? = null
    private var contentType: String? = null

    constructor(file: File,contentType: String,listener: OnFileUploadProgressListener){
        this.file=file
        this.contentType=contentType
        this.listener=listener
    }

    override fun contentType(): MediaType? {
        return MediaType.parse(contentType)
    }

    override fun writeTo(sink: BufferedSink) {
        var source: Source?
        try {
            source = Okio.source(file)
            var read: Long
            totalSize = contentLength()
            while (source.read(sink.buffer(), SEGMENT_SIZE).also { read = it } != -1L) {
                transferred += read
                sink.flush()
                sendPercent()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun contentLength(): Long {
        return file?.length()?:0
    }

   private fun sendPercent() {
        val newPercent = (transferred.toDouble() / totalSize * 100).toInt()
        if (newPercent != percent) {
            percent = newPercent
                listener?.transferred(totalSize, transferred, percent)
            }
        }

}