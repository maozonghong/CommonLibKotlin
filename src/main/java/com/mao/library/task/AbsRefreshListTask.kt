package com.mao.library.task

import android.content.Context
import com.mao.library.abs.AbsModel
import com.mao.library.http.AbsRequest
import com.mao.library.interfaces.AdapterViewInterface
import com.mao.library.interfaces.OnRefreshListener
import com.mao.library.interfaces.OnTaskCompleteListener
import com.mao.library.interfaces.Refreshable

/**
 * Created by maozonghong
 * on 2020/6/16
 */
abstract class AbsRefreshListTask<T:AbsModel> :AbsListTask<T>, OnRefreshListener {
    private var refreshView: Refreshable? = null

    constructor(listView:Refreshable, request: AbsRequest?,
                onTaskCompleteListener:OnTaskCompleteListener<java.util.ArrayList<T>>?):
            super(listView,request,onTaskCompleteListener)

    constructor(listView:Refreshable, request: AbsRequest?):
            super(listView,request)

    constructor(context: Context, request: AbsRequest?,
                onTaskCompleteListener: OnTaskCompleteListener<ArrayList<T>>?):
            super(context, request, onTaskCompleteListener)


    override fun initListViewListeners(listView: AdapterViewInterface) {
        super.initListViewListeners(listView)
        this.refreshView=listView as? Refreshable
        refreshView?.setOnRefreshListener(this)

    }



    override fun onHeaderRefresh(listView: Refreshable?) {
        start()
    }


    override fun onTaskFinish() {
        super.onTaskFinish()
        refreshView?.refreshComplete()
    }


}


