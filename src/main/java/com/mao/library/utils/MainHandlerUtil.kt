package com.mao.library.utils

import android.os.Handler
import android.os.Looper

object MainHandlerUtil {
    val handler = Handler(Looper.getMainLooper())

    @JvmStatic
    fun post(r: Runnable) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run()
        } else {
            handler.post(r)
        }
    }
    
    @JvmStatic
    fun postDelayed(r: Runnable, delayMillis: Long) {
        handler.postDelayed(r, delayMillis)
    }
    
    @JvmStatic
    fun removeCallbacks(r: Runnable) {
        handler.removeCallbacks(r)
    }
    
}
