package com.mao.library.widget.refresh

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mao.library.R
import com.mao.library.interfaces.AdapterInterface
import com.mao.library.interfaces.AdapterViewInterface
import com.mao.library.wrapperadapter.HeaderAndFooterWrapperAdapter
import kotlin.math.abs


/**
 * Created by maozonghong
 * on 2020/6/15
 */
open class OverScrollRecyclerView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet):
    FrameLayout(context,attributeSet), GestureDetector.OnGestureListener, AdapterViewInterface {

    //header
    private val mHeaderViews: SparseArrayCompat<View> = SparseArrayCompat()

    //footer
    private val mFooterViews: SparseArrayCompat<View> = SparseArrayCompat()

    //recyclerView
    protected var recyclerView: RecyclerView = View.inflate(context, R.layout.over_recycler_view,
        null
    ) as RecyclerView

    //gestureDetector
    private var mGestureDetector: GestureDetector? = null

    //scroller
    protected  val mOverScroller: OverScroller by lazy {
        OverScroller(context)
    }

    //wrap adapter
    private var mWrapAdapter: HeaderAndFooterWrapperAdapter? = null

    protected open var mScrollY = 0f
    private var isAtTop = false
    private var isAtBottom:Boolean = false
    private var distanceY = 0f
    private val SPEED: Short = 3
    protected var VISIBLE_THRESHOLD = 2
    //empty view
    private var mEmptyView: View? = null
    private var isEmptyViewShown = false
    //listener
    private var onItemClickListener: OnItemClickListener? = null
    private val onClickListener: OnClickListener? = null

    //data observer
    private var mDataObserver: AdapterDataObserver = DataObserver()

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
                        RecyclerView.SCROLL_STATE_SETTLING -> adapter.lockLoadingImageWhenScrolling(
                            context
                        )
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}
        }



   init {
       recyclerView.apply {
           itemAnimator=null
           overScrollMode=View.OVER_SCROLL_ALWAYS
           layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,
               false)
           addOnScrollListener(onScrollListener)
       }
        addView(recyclerView)
        mGestureDetector = GestureDetector(
            context,
            this
        )
    }


    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        onClickListener?.onClick(this)
        e?.let {
            var child = recyclerView.findChildViewUnder(e.x, e.y)
            if (child != null) {
                onItemClickListener?.onItemClick(
                    recyclerView.getChildViewHolder(child),
                    recyclerView.getChildAdapterPosition(child) - getHeadViewCount()
                )
            }
        }

        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        mOverScroller?.abortAnimation()
       return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float):
            Boolean {
        return false
    }



    //e1:第1个ACTION_DOWN MotionEvent
    //e2:最后一个ACTION_MOVE MotionEvent
    // distanceY：距离上次产生onScroll事件后，Y抽移动的距离
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float):
            Boolean {
        isAtTop = isAtTop()
        isAtBottom = isAtBottom()
        // Log.i("onScroll",MotionEvent.actionToString(e1.getAction())+":"+MotionEvent.actionToString(e2.getAction()));
        // Log.i("onScroll",MotionEvent.actionToString(e1.getAction())+":"+MotionEvent.actionToString(e2.getAction()));
        Log.d(
            "OverScrollRecyclerView",
            "isAtTop: $isAtTop isAtBottom: $isAtBottom distanceY: $distanceY"
        )

        if (isAtTop && isAtBottom) {
            mScrollY += distanceY / SPEED
        } else if (isAtTop && (distanceY < 0 || mScrollY < 0) && abs(distanceX) < abs(distanceY)) {
            mScrollY += distanceY / SPEED
            if (mScrollY > 0) {
                mScrollY = 0f
            }
        } else if (isAtBottom && distanceY > 0 || mScrollY > 0) {
            mScrollY += distanceY / SPEED
            if (mScrollY < 0) {
                mScrollY = 0f
            }
        } else {
            return false
        }

        this.distanceY = distanceY
        mOverScroller.abortAnimation()
        scrollTo(0, mScrollY.toInt())
        // onOverScrolled(0, (int) scrollY,false,false);
         onOverScrolled(0, mScrollY.toInt(), clampedX = false, clampedY = false)
        if (abs(mScrollY) > 3f && parent != null) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun getAbsAdapter(): AdapterInterface<*>? {
        val adapter = recyclerView.adapter as HeaderAndFooterWrapperAdapter?
        return if (adapter != null) {
            if (adapter.innerAdapter is AdapterInterface<*>) adapter.innerAdapter as AdapterInterface<*> else null
        } else null
    }

    override fun hideProgress() {

    }

    override fun showProgress() {

    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val b = mGestureDetector?.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> if (b==true) {
                onMotionMove()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mScrollY != 0f) {
                    onMotionUp(mScrollY.toInt())
                }
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        if (mScrollY == 0f || isAtTop && distanceY < 0 || isAtBottom && distanceY > 0) {
            recyclerView.dispatchTouchEvent(event)
        }
        return true
    }

    protected open fun onMotionMove() {}


    protected open fun onMotionUp(scrollY: Int) {
        if (scrollY != 0) {
            mOverScroller?.abortAnimation()
            mOverScroller?.startScroll(0, scrollY, 0, -scrollY, 400)
            invalidate()
        }
    }


    fun addHeaderView(headView:View) {
        mHeaderViews.put(
            mHeaderViews.size() + HeaderAndFooterWrapperAdapter.BASE_ITEM_TYPE_HEADER,
            headView
        )
        mWrapAdapter?.notifyDataSetChanged()
    }


    fun addFooterView(footView:View){
        mFooterViews.put(
            mFooterViews.size() + HeaderAndFooterWrapperAdapter.BASE_ITEM_TYPE_FOOTER,
            footView
        )
        mWrapAdapter?.notifyDataSetChanged()
    }


    open fun removeHeaderView() {
        if (getHeadViewCount() > 0) {
            mHeaderViews.remove(0)
            mWrapAdapter?.notifyDataSetChanged()
        }
    }


    open fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        adapter?.let {
            mWrapAdapter?.run {
                innerAdapter.unregisterAdapterDataObserver(mDataObserver)
            }
            mWrapAdapter =HeaderAndFooterWrapperAdapter(adapter,mHeaderViews,mFooterViews)
            recyclerView.adapter = mWrapAdapter
            adapter?.registerAdapterDataObserver(mDataObserver)
        }
        //mDataObserver.onChanged();
    }


     fun getHeadViewCount():Int{
        return mHeaderViews.size()
    }

    fun getFootViewCount():Int{
        return mFooterViews.size()
    }


    interface OnItemClickListener {
        fun onItemClick(holder: ViewHolder?, position: Int)
    }


    private fun isAtTop(): Boolean {
        return !recyclerView.canScrollVertically(-1)
    }

    private fun isAtBottom(): Boolean {
        return !recyclerView.canScrollVertically(1)
    }


    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        if (scrollY == 0) {
            setFooterHeight(0)
        } else if (scrollY > 0 && setFooterHeight(scrollY)) { //(scrollY < 0 && setHeaderHeight(-scrollY)) ||
            super.onOverScrolled(scrollX, 0, clampedX, clampedY)
            return
        }
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
        invalidate()
    }

    override fun computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            mScrollY = mOverScroller.currY.toFloat()
            Log.i("OverRecyclerView", "computeScroll:$mScrollY")
            scrollTo(0, mScrollY.toInt())
            onOverScrolled(0, mScrollY.toInt(), clampedX = false, clampedY = false)
            invalidate()
        }
    }

    protected open fun setFooterHeight(height: Int): Boolean {
        return false
    }

    protected open fun setHeaderHeight(height: Int): Boolean {
        return false
    }


    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
        recyclerView?.layoutManager = layoutManager
    }



    //observer
     inner class DataObserver : AdapterDataObserver() {
        override fun onChanged() {
            mWrapAdapter?.notifyDataSetChanged()
            onChang()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
             mWrapAdapter?.notifyItemRangeInserted(positionStart, itemCount)
            onChang()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemRangeChanged(positionStart, itemCount)
            onChang()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mWrapAdapter?.notifyItemRangeChanged(positionStart, itemCount, payload)
            onChang()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemRangeRemoved(positionStart, itemCount)
            onChang()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mWrapAdapter?.notifyItemMoved(fromPosition, toPosition)
            onChang()
        }
    }

     fun onChang() {
        if(mWrapAdapter?.innerAdapter?.itemCount?:0>0){
            hideEmptyView()
            return
        }
        showEmptyView()
    }

    private fun hideEmptyView() {
        if (mEmptyView != null && isEmptyViewShown) {
            removeView(mEmptyView)
            isEmptyViewShown = false
        }
    }

    private fun showEmptyView() {
        if(mEmptyView != null&&!isEmptyViewShown) {
            isEmptyViewShown=true
            if(mEmptyView?.layoutParams == null) {
                if(height == 0) {
                    viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            return if (height != 0) {
                                viewTreeObserver.removeOnPreDrawListener(this)
                                if (mEmptyView != null) {
                                    mEmptyView?.layoutParams = LayoutParams(
                                        LayoutParams.MATCH_PARENT,
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
                    mEmptyView?.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, height)
                }
            }
            addView(mEmptyView)
        }
    }

    open fun setEmptyView(emptyView: View) {
        mEmptyView = if (mEmptyView != null && mEmptyView !== emptyView) {
            removeView(mEmptyView)
            emptyView
        } else {
            emptyView
        }
        mDataObserver.onChanged()
    }

}