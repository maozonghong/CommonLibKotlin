package com.mao.library.interfaces

/**
 * Created by maozonghong
 * on 2020/6/18
 */
interface OnFileUploadProgressListener {
    fun transferred(total:Long, num:Long, percent:Int)
}