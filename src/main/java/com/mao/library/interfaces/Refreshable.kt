package com.mao.library.interfaces

/**
 * Created by maozonghong
 * on 2020/6/12
 */


interface Refreshable:AdapterViewInterface {

    companion object{
        const val STATE_PULL_TO_REFRESH = 2

        const val STATE_RELEASE_TO_REFRESH = 3

        const  val STATE_REFRESHING = 4
    }
    fun setOnRefreshListener(refreshListener: OnRefreshListener)

    fun setPullRefreshEnabled(enable: Boolean)

    fun refreshComplete()
}