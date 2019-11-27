package com.mao.library.abs

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.widget.FrameLayout

import com.mao.library.interfaces.ActivityInterface

/**
 * Created by maozonghong
 * on 2019/11/21
 */
abstract class AbsActivityCompat {

    abstract fun onCreate(absActivity: ActivityInterface, savedInstanceState: Bundle?)

    abstract fun onSetContentView(absActivity: ActivityInterface)

    class MyFrameLayout(context: Context) : FrameLayout(context) {
        private var mPaddingBottom: Int = 0
        private var isChanged: Boolean = false

        override fun fitSystemWindows(insets: Rect): Boolean {
            val insetBottom = insets.bottom

            if (insetBottom > 0) {
                if (!isChanged) {
                    isChanged = true
                    mPaddingBottom = paddingBottom
                }
                setPadding(paddingLeft, paddingTop, paddingRight, insetBottom)
            } else if (isChanged) {
                isChanged = false
                setPadding(paddingLeft, paddingTop, paddingRight, mPaddingBottom)
            }

            return true
        }
    }

    companion object {
        var instance: AbsActivityCompat? = null
    }
}
