package com.mao.library.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.mao.library.abs.AbsApplication
import java.lang.reflect.Method


/**
 * Created by maozonghong
 * on 2020/6/15
 */
class InputMethodUtil {
    private val imm: InputMethodManager =
        AbsApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    fun showInputMethod(v: View?) {
        if (v != null) {
            imm.showSoftInput(v, 0)
        }
    }

    fun hideInputMethod(v: View?) {
        if (v != null) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun hideInputMethod() {
        try {
            val method: Method = imm.javaClass.getDeclaredMethod("closeCurrentInput")
            method.isAccessible = true
            method.invoke(imm)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}