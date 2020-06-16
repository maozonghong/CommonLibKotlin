package com.mao.library.interfaces

import android.content.Context

/**
 * Created by maozonghong
 * on 2020/6/12
 */
interface AdapterViewInterface {

    fun getAbsAdapter(): AdapterInterface<*>?

    fun getContext(): Context

    fun hideProgress()

    fun showProgress()
}