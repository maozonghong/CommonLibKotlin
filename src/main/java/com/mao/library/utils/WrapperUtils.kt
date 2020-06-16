package com.mao.library.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * Created by maozonghong
 * on 2020/6/12
 */
class WrapperUtils {
    interface SpanSizeCallback {
        fun getSpanSize(
            layoutManager: GridLayoutManager?,
            oldLookup: SpanSizeLookup?,
            position: Int
        ): Int
    }

    companion object{
        @JvmStatic
        fun onAttachedToRecyclerView(
            innerAdapter: RecyclerView.Adapter<*>,
            recyclerView: RecyclerView,
            callback: SpanSizeCallback
        ) {
            innerAdapter.onAttachedToRecyclerView(recyclerView)
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                val spanSizeLookup = layoutManager.spanSizeLookup
                layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return callback.getSpanSize(layoutManager, spanSizeLookup, position)
                    }
                }
                layoutManager.spanCount = layoutManager.spanCount
            }
        }
        @JvmStatic
        fun setFullSpan(holder: ViewHolder) {
            val lp = holder.itemView.layoutParams
            if (lp != null
                && lp is StaggeredGridLayoutManager.LayoutParams
            ) {
                lp.isFullSpan = true
            }
        }
    }

}