package com.mao.library.utils

import android.util.Log
import com.mao.library.BuildConfig

/**
 * Created by maozonghong
 * on 2020/8/3
 */
object LogUtils {

    var enable= BuildConfig.DEBUG

    private const val INFO = "INFO"

    private const val ERROR = "ERROR"

    @JvmStatic
    fun info(TAG:String,msg:String?){
        log(
            INFO,
            TAG,
            msg
        )
    }
    @JvmStatic
    fun error(TAG: String,msg: String?){
        log(
            ERROR,
            TAG,
            msg
        )
    }
    @JvmStatic
    fun logAlways(TAG: String,msg: String?){
        Log.e(TAG,msg)
    }

    private fun log(type:String,TAG: String,msg: String?){
        if(!enable) return
        when(type){
            INFO -> Log.i(TAG,msg)
            ERROR ->Log.e(TAG,msg)
            else->Log.d(TAG,msg)
        }
    }

}