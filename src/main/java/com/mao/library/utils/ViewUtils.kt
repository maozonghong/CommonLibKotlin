package com.mao.library.utils

import android.view.View
import android.view.ViewGroup


/**
 * Created by maozonghong
 * on 2020/6/12
 */
object ViewUtils {

    fun measureViewHeight(view: View?) {
        if(view==null){
            return
        }
        var p: ViewGroup.LayoutParams = view.layoutParams
        if (p == null) {
            p = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        val childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width)
        val lpHeight = p.height
        val childHeightSpec: Int
        childHeightSpec = if (lpHeight > 0) {
            View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
        } else {
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        }
        view.measure(childWidthSpec, childHeightSpec)
    }
}