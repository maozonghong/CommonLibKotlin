package com.mao.library.widget.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mao.library.interfaces.AdapterInterface
import com.mao.library.interfaces.LoadMoreRefreshable
import com.mao.library.interfaces.OnLoadMoreListener
import com.mao.library.wrapperadapter.LoadMoreWrapperAdapter


/**
 * Created by maozonghong
 * on 2020/6/12
 */

class LoadMoreSwipeRefreshLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?)
    :VerticalSwipeRefreshLayout(context,attributeSet), LoadMoreRefreshable {

    companion object{
        private const val VISIBLE_THRESHOLD = 2
    }

    private var recyclerView: RecyclerView? = null
    private var isLoading =false
    private var hasMore:Boolean = false
    private var isEmptyViewShown:Boolean = false

    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLoadMoreWrapperAdapter:LoadMoreWrapperAdapter?=null
    private var mEmptyView: View? = null
    private var defaultLoadMoreView: DefaultLoadMoreView? = null
    private var onRefreshListener: com.mao.library.interfaces.OnRefreshListener? = null

    private var onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val adapter = getAbsAdapter()
                if (adapter != null) {
                    when (newState) {
                        RecyclerView.SCROLL_STATE_IDLE -> adapter.unLockLoadingImage(context)
                        RecyclerView.SCROLL_STATE_DRAGGING -> adapter.lockLoadingImageWhenScrolling(
                            context
                        )
                        RecyclerView.SCROLL_STATE_SETTLING -> adapter.lockLoadingImageWhenScrolling(context)
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && onLoadMoreListener != null
                    && hasMore && !isLoading && !isRefreshing) {
                    val layoutManager = recyclerView.layoutManager
                    layoutManager?.let {
                        val lastVisibleItemPosition: Int
                        lastVisibleItemPosition = when (layoutManager) {
                            is GridLayoutManager -> {
                                layoutManager.findLastVisibleItemPosition()
                            }
                            is StaggeredGridLayoutManager -> {
                                val into =
                                    IntArray(layoutManager.spanCount)
                                layoutManager.findLastVisibleItemPositions(
                                    into
                                )
                                findMax(into)
                            }
                            else -> {
                                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                            }
                        }
                        if (layoutManager.childCount>0 && lastVisibleItemPosition >=
                            layoutManager.itemCount - VISIBLE_THRESHOLD && layoutManager.itemCount >
                            layoutManager.childCount
                        ) {
                            onLoadMoreListener?.onLoadMore(this@LoadMoreSwipeRefreshLayout)
                            isLoading = true
                        }
                    }

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}
        }


    init {
        recyclerView = RecyclerView(context).apply {
            layoutManager=LinearLayoutManager(context)
            overScrollMode=View.OVER_SCROLL_ALWAYS
            itemAnimator=null
            addOnScrollListener(onScrollListener)
        }
        defaultLoadMoreView = DefaultLoadMoreView(this)
        addView(recyclerView)
    }


    override fun setOnRefreshListener(refreshListener: com.mao.library.interfaces.OnRefreshListener) {
        onRefreshListener = refreshListener
        setOnRefreshListener {
            onRefreshListener?.onHeaderRefresh(this@LoadMoreSwipeRefreshLayout)
        }
    }


    override fun setPullRefreshEnabled(enable: Boolean) {
            isEnabled=enable
    }


    override fun refreshComplete() {
        isRefreshing=false
    }

    override fun getAbsAdapter(): AdapterInterface<*>? {
        val adapter = mAdapter
        return if (adapter != null) {
            if (adapter is AdapterInterface<*>) adapter else null
        } else null
    }

    override fun hideProgress() {

    }

    override fun showProgress() {

    }


    override fun setLoadingMoreEnabled(enable: Boolean) {
        defaultLoadMoreView?.setLoadMoreEnable(enable)
    }


    override fun setHasMore(hasMore: Boolean) {
        this.hasMore=hasMore
        defaultLoadMoreView?.setHasMore(hasMore)
    }


    override fun loadMoreComplete() {
        isLoading=false
    }


    override fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener=onLoadMoreListener
    }


    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        adapter?.let {
            mAdapter?.unregisterAdapterDataObserver(adapterDataObserver)
            this.mAdapter=adapter
            mLoadMoreWrapperAdapter=LoadMoreWrapperAdapter(adapter)
            mLoadMoreWrapperAdapter?.setLoadMoreView(defaultLoadMoreView)
            recyclerView?.adapter = mLoadMoreWrapperAdapter
            mAdapter?.registerAdapterDataObserver(adapterDataObserver)
        }
    }


    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        recyclerView?.layoutManager = layoutManager
    }



    fun refresh() {
        onRefreshListener?.onHeaderRefresh(this@LoadMoreSwipeRefreshLayout)
    }


    fun getRecyclerView(): RecyclerView? {
        return recyclerView
    }


    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }



    fun setEmptyView(emptyView: View) {
        if (mEmptyView!= null && mEmptyView !== emptyView) {
//            mContainer?.removeView(mEmptyView)
            (rootView as ViewGroup)?.removeView(mEmptyView)
            isEmptyViewShown = false
            mEmptyView = emptyView
        } else {
            mEmptyView = emptyView
        }
        onChang()
    }


    fun setEmptyView(@LayoutRes res: Int) {
        val emptyView =
            LayoutInflater.from(context).inflate(res, null, false)
        setEmptyView(emptyView)
    }

    private var adapterDataObserver=object:
        RecyclerView.AdapterDataObserver(){

        override fun onChanged() {
            super.onChanged()
             mLoadMoreWrapperAdapter?.notifyDataSetChanged()
             onChang()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeChanged(positionStart,itemCount)
            onChang()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeInserted(positionStart, itemCount)
            onChang()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeRemoved(positionStart,itemCount)
            onChang()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mLoadMoreWrapperAdapter?.notifyItemRangeChanged(positionStart, itemCount, payload)
            onChang()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemMoved(fromPosition, toPosition)
            onChang()
        }
    }

    private fun onChang() {
        if(mAdapter?.itemCount?:0>0){
            hideEmptyView()
            return
        }
        showEmptyView()
    }


 private fun hideEmptyView() {
        if(mEmptyView != null&&isEmptyViewShown) {
            (rootView as ViewGroup).removeView(mEmptyView)
            isEmptyViewShown=false
        }
    }

    private fun showEmptyView() {
        if(mEmptyView!=null&&!isEmptyViewShown) {
            isEmptyViewShown=true
            if(mEmptyView?.layoutParams == null) {
                if(height == 0) {
                    viewTreeObserver.addOnPreDrawListener(object:
                        ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            return if(height != 0) {
                                viewTreeObserver.removeOnPreDrawListener(this)
                                mEmptyView?.let {
                                    it.layoutParams = FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.MATCH_PARENT,
                                        height
                                    )
                                }
                                false
                            } else {
                                true
                            }
                        }
                    })
                } else {
                    mEmptyView?.layoutParams =
                        FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height)
                }
            }
//            mContainer?.addView(mEmptyView)
            (rootView as ViewGroup)?.addView(mEmptyView)
        }
    }



}