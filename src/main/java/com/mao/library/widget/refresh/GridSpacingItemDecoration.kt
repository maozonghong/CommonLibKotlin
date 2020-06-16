package com.mao.library.widget.refresh

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by maozonghong
 * on 2020/6/15
 */
class GridSpacingItemDecoration: RecyclerView.ItemDecoration {
    private var spanCount = 0
    private var spacing = 0
    private var includeEdge = false
    private var headerNum = 0

    constructor(
        spanCount: Int,
        spacing: Int,
        includeEdge: Boolean,
        headerNum: Int
    ) {
        this.spanCount = spanCount
        this.spacing = spacing
        this.includeEdge = includeEdge
        this.headerNum = headerNum
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) - headerNum // item position
        if (position >= 0) {
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        } else {
            outRect.left = 0
            outRect.right = 0
            outRect.top = 0
            outRect.bottom = 0
        }
    }


}