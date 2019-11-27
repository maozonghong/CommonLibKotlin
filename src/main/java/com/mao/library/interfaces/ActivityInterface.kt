package com.mao.library.interfaces

import android.app.Activity
import android.view.ViewGroup
import android.view.Window

interface ActivityInterface {

    fun getActivity():Activity

    fun getWindow():Window

    fun getDecorView():ViewGroup

    fun setHasFinishAnimation(hasFinishAnimation: Boolean)

    fun finish()

    fun hasFinishAnimation(): Boolean
}
