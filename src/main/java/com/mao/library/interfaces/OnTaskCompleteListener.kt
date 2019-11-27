package com.mao.library.interfaces

interface OnTaskCompleteListener<T> {

    fun onTaskComplete(result: T?)

    fun onTaskFailed(error: String?, code: Int)

    fun onTaskCancel()

    fun onTaskLoadMoreComplete(result: T?)
}