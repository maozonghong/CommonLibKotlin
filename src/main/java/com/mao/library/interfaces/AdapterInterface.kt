package com.mao.library.interfaces

import android.content.Context
import java.util.ArrayList

interface AdapterInterface<T> {
    fun setList(list: ArrayList<T>?)

    fun addAll(list: List<T>?)

    fun getList(): List<T>?

    fun add(position: Int, item: T)

    fun remove(position: Int)

    fun getSize(): Int

    fun clear()

    fun unLockLoadingImage(context: Context)

    fun lockLoadingImageWhenScrolling(context: Context)
}