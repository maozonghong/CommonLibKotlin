package com.mao.library.interfaces

import android.graphics.Bitmap
import android.widget.ImageView

/**
 * Created by maozonghong
 * on 2019/11/21
 */
interface AsyncImageListener {

    fun onLoadFailed(url: String?)

    fun onLoadStart(url: String?)

    fun onLoadFinish(imageView: ImageView, bitmap: Bitmap)
}
