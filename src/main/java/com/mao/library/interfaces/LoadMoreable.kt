package com.mao.library.interfaces

/**
 * Created by maozonghong
 * on 2020/6/12
 */
interface LoadMoreable :AdapterViewInterface {

    fun setLoadingMoreEnabled(enable: Boolean)

    fun setHasMore(hasMore: Boolean)

    fun loadMoreComplete()

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener)
}