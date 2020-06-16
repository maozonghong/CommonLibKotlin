package com.mao.library.widget.refresh

import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.mao.library.R
import com.mao.library.interfaces.LoadMoreable
import com.mao.library.manager.ViewDefaultSettingManager
import com.mao.library.utils.ViewUtils


/**
 * Created by maozonghong
 * on 2020/6/12
 */
class DefaultLoadMoreView (loadMoreable: LoadMoreable): LinearLayout(loadMoreable.getContext()) {
    private var parentView: LoadMoreable? = null
    //loadmore
    private var contentLay: LinearLayout? = null
    private var mFootView: ViewGroup? = null
    private var textView: TextView? = null
    private var mProgressBar: ProgressBar? = null
    private var loadMoreable = true
    private var hasMore:Boolean = false
    private var mFootHeight:Int? = 0
    private var params: ViewGroup.LayoutParams? = null

    init {

        this.parentView=loadMoreable

        mFootView = View.inflate(
            context,
            R.layout.listview_loadmore_footer,
            null
        ) as ViewGroup
        addView(
            mFootView,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
        contentLay = mFootView?.findViewById(R.id.contentLay)
        textView = mFootView?.findViewById(android.R.id.text1)
        mProgressBar = mFootView?.findViewById(android.R.id.progress)

        if (ViewDefaultSettingManager.loadmore_text_nomore != null) {
            textView?.text = ViewDefaultSettingManager.loadmore_text_nomore
        }

        if (ViewDefaultSettingManager.loadmore_text_color != 0) {
            textView?.setTextColor(ViewDefaultSettingManager.loadmore_text_color)
        }

        if (ViewDefaultSettingManager.loadmore_text_size > 0) {
            textView?.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                ViewDefaultSettingManager.loadmore_text_size.toFloat()
            )
        }
        ViewUtils.measureViewHeight(contentLay)
        mFootHeight = contentLay?.measuredHeight
        Log.i("mao", "DefaultLoadMoreViewHeight:$mFootHeight")
    }


    fun setFootHeight(height: Int): Boolean {
        var height = height
        if (contentLay == null) {
            return false
        }
        if (params == null) {
            params = contentLay?.layoutParams
        }
        ++height // importance
        if (params?.height !== height) {
            params?.height = height
            contentLay?.requestLayout()
            Log.i("mao", "DefaultLoadMoreView setFooterHeight:$height")
        }
        return false
    }


    fun getFooterHeight(): Int {
        return mFootHeight?:0
    }

    fun addViewAboveFooter(v: View?) {
        mFootView?.addView(v)
        contentLay?.bringToFront()
    }

    fun hideLoadMore() {
        contentLay?.visibility = View.GONE
    }


    fun setHasMore(hasMore: Boolean) {
        if (this.hasMore != hasMore) {
            this.hasMore = hasMore
            contentLay?.visibility = View.VISIBLE
            if (hasMore) {
                mProgressBar?.visibility = View.VISIBLE
                if (ViewDefaultSettingManager.loadmore_text_loading != null) {
                    textView?.text = ViewDefaultSettingManager.loadmore_text_loading
                } else {
                    textView?.text = "正在加载更多"
                }
            } else {
                mProgressBar?.visibility = View.GONE
                if (ViewDefaultSettingManager.loadmore_text_nomore != null) {
                    textView?.text = ViewDefaultSettingManager.loadmore_text_nomore
                } else {
                    textView?.text = "没有更多了(>_<)"
                }
            }
            if (loadMoreable) {
                textView?.visibility = View.VISIBLE
            }
        }
    }

    fun setLoadMoreEnable(loadMoreable: Boolean) {
        this.loadMoreable = loadMoreable
        if (loadMoreable) {
            textView?.visibility = View.VISIBLE
        } else {
            mProgressBar?.visibility = View.GONE
            textView?.visibility = View.GONE
        }
    }
}