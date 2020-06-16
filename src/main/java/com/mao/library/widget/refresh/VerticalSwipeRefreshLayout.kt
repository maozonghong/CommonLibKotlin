package com.mao.library.widget.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

/**
 * Created by maozonghong
 * on 2020/6/12
 */
open class VerticalSwipeRefreshLayout @JvmOverloads constructor(context: Context, attrs:AttributeSet?) :
    SwipeRefreshLayout(context, attrs) {

    private var mTouchSlop = 0
    private var mPrevX = 0f
    private var isDisabled = false

    init {

        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    fun disableInterceptTouchEvent(isDisabled: Boolean) {
        this.isDisabled = isDisabled
        parent.requestDisallowInterceptTouchEvent(isDisabled)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPrevX = MotionEvent.obtain(event).x
            MotionEvent.ACTION_MOVE -> {
                if (isDisabled) {
                    return false
                }
                val eventX = event.x
                val xDiff = abs(eventX - mPrevX)
                if (xDiff > mTouchSlop) {
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }
}

