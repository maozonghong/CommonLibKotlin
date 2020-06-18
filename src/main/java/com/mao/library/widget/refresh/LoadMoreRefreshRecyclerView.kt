package com.mao.library.widget.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mao.library.interfaces.LoadMoreRefreshable
import com.mao.library.interfaces.OnLoadMoreListener
import com.mao.library.interfaces.OnRefreshListener
import com.mao.library.wrapperadapter.LoadMoreWrapperAdapter


/**
 * Created by maozonghong
 * on 2020/6/15
 */
class LoadMoreRefreshRecyclerView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?)
    :OverScrollRecyclerView(context, attributeSet), LoadMoreRefreshable {

    private val defaultReFreshHeadView: DefaultReFreshHeadView by lazy {
        DefaultReFreshHeadView(this)
    }

    private val defaultLoadMoreView: DefaultLoadMoreView by lazy{
        DefaultLoadMoreView(this)
    }
    private var mHeaderViewHeight = 0

    private var mFooterViewHeight:Int = 0

    private var isLoading = false

    private var hasMore:Boolean = false

    private var onLoadMoreListener: OnLoadMoreListener? = null

    private var mLoadMoreWrapperAdapter: LoadMoreWrapperAdapter?=null

    private var mDataObserver: RecyclerView.AdapterDataObserver = MyDataObserver()

    inner class MyDataObserver:RecyclerView.AdapterDataObserver(){

        override fun onChanged() {
            mLoadMoreWrapperAdapter?.notifyDataSetChanged()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mLoadMoreWrapperAdapter?.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mLoadMoreWrapperAdapter?.notifyItemMoved(fromPosition, toPosition)
        }

    }

    private var onScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && onLoadMoreListener != null && hasMore && !isLoading) {
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
                        if (layoutManager.childCount > 0 && lastVisibleItemPosition >=
                            layoutManager.itemCount - VISIBLE_THRESHOLD && layoutManager.itemCount >
                            layoutManager.childCount) {
                            defaultLoadMoreView?.setFootHeight(mFooterViewHeight)
                            onLoadMoreListener?.onLoadMore(this@LoadMoreRefreshRecyclerView)
                            isLoading = true
                        }
                    }

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}
        }

    init {
        //header
        addView(defaultReFreshHeadView)
        mHeaderViewHeight = defaultReFreshHeadView.getHeaderViewHeight()?:0
        defaultReFreshHeadView.translationY = (-mHeaderViewHeight).toFloat()

        //footer
        mFooterViewHeight = defaultLoadMoreView.getFooterHeight()
        val footerParams = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        defaultLoadMoreView.layoutParams = footerParams
       // defaultLoadMoreView.setFootHeight(0)
        recyclerView.addOnScrollListener(onScrollListener)
    }


     override fun onMotionMove() {
        super.onMotionMove()
        defaultReFreshHeadView.headerPrepareToRefresh(mScrollY.toInt())
    }


     override fun onMotionUp(scrollY: Int) {
        mOverScroller.abortAnimation()
        if (defaultReFreshHeadView.refreshable && scrollY < 0 && defaultReFreshHeadView.headerRefreshing()) {
            mOverScroller.startScroll(
                0, scrollY, 0, -scrollY - (defaultReFreshHeadView.getHeaderViewHeight()?:0),
                400
            )
            invalidate()
        } else {
            super.onMotionUp(scrollY)
        }
    }

    override fun setOnRefreshListener(refreshListener: OnRefreshListener) {
        defaultReFreshHeadView.setOnRefreshListener(refreshListener)
    }

    override fun setPullRefreshEnabled(enable: Boolean) {
        defaultReFreshHeadView.setPullRefreshEnabled(enable)
    }

    override fun refreshComplete() {
        if (defaultReFreshHeadView.refreshComplete()) {
            mOverScroller.abortAnimation()
            mOverScroller.startScroll(0, -mHeaderViewHeight, 0, mHeaderViewHeight, 400)
            invalidate()
        }
    }


    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        adapter?.let {
            mLoadMoreWrapperAdapter?.run {
                innerAdapter?.unregisterAdapterDataObserver(mDataObserver)
            }
            mLoadMoreWrapperAdapter=LoadMoreWrapperAdapter(it)
            mLoadMoreWrapperAdapter?.setLoadMoreView(defaultLoadMoreView)
            super.setAdapter(mLoadMoreWrapperAdapter)
            it.registerAdapterDataObserver(mDataObserver)
        }
    }


    override fun setLoadingMoreEnabled(enable: Boolean) {
        defaultLoadMoreView.setLoadMoreEnable(enable)
    }

    override fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        defaultLoadMoreView.setHasMore(hasMore)
    }

    /**
     * 手动调用刷新
     */
    fun refresh() {
        defaultReFreshHeadView.refresh()
    }

    override fun loadMoreComplete() {
        isLoading = false
    }

    override fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

     override fun setFooterHeight(height: Int): Boolean {
        //return defaultLoadMoreView.setFootHeight(height)
         return false
    }


     fun addViewAboveFooter(v: View) {
        defaultLoadMoreView.addViewAboveFooter(v)
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
}