package com.mao.library.task

import android.content.Context
import com.mao.library.abs.AbsLoadMoreRequest
import com.mao.library.abs.AbsModel
import com.mao.library.http.AbsRequest
import com.mao.library.interfaces.*


/**
 * Created by maozonghong
 * on 2020/6/16
 */
abstract class AbsLoadMoreRefreshListTask<T:AbsModel> :
    AbsRefreshListTask<T>, OnLoadMoreListener {
    private var loadMoreRefreshable: LoadMoreRefreshable? = null

    constructor(listView: LoadMoreRefreshable, request: AbsRequest?,
                onTaskCompleteListener:OnTaskCompleteListener<java.util.ArrayList<T>>?):
            super(listView,request,onTaskCompleteListener)

    constructor(context: Context, request: AbsRequest?,
                onTaskCompleteListener: OnTaskCompleteListener<ArrayList<T>>?):
            super(context, request, onTaskCompleteListener)


    constructor(listView: LoadMoreRefreshable, request: AbsRequest?):
            super(listView,request)

    override fun initListViewListeners(listView: AdapterViewInterface) {
        super.initListViewListeners(listView)
        this.loadMoreRefreshable=listView as LoadMoreRefreshable?
        loadMoreRefreshable?.setOnLoadMoreListener(this)
    }

    override fun onLoadMore(listView: LoadMoreable?) {
        var start = listView?.getAbsAdapter()?.getSize()?:0

        var loadMoreRequest= request as AbsLoadMoreRequest?
        loadMoreRequest?.start = start
        if (start == 0) {
            start()
        } else {
            start(true)
        }
    }

    override fun onTaskComplete(result: ArrayList<T>?) {
        super.onTaskComplete(result)
        loadMoreRefreshable?.setHasMore(result?.size?:0>0)
    }

    override fun onTaskLoadMoreComplete(result: java.util.ArrayList<T>?) {
        super.onTaskLoadMoreComplete(result)
        var loadMoreAdapter =
            loadMoreRefreshable?.getAbsAdapter() as AdapterInterface<T>?
        loadMoreAdapter?.addAll(result)
        loadMoreRefreshable?.setHasMore(result?.size?:0 > 0)
        onTaskFinish()
    }

    override fun onTaskFinish() {
        super.onTaskFinish()
        loadMoreRefreshable?.loadMoreComplete()
    }

    override fun onHeaderRefresh(listView: Refreshable?) {
        var loadMoreRequest = request as AbsLoadMoreRequest?
        loadMoreRequest?.start = 0
        start()
    }
}