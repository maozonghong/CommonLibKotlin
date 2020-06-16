package com.mao.library.widget.refresh

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.mao.library.R
import com.mao.library.interfaces.OnRefreshListener
import com.mao.library.interfaces.Refreshable
import com.mao.library.manager.ViewDefaultSettingManager
import com.mao.library.utils.ViewUtils


/**
 * Created by maozonghong
 * on 2020/6/15
 */
class DefaultReFreshHeadView(refreshable: Refreshable):FrameLayout(refreshable.getContext()) {

    private var mHeaderView: View? = null
    private var mHeaderViewParent:View
    private var mHeaderImageView: ImageView? = null
    private var mHeaderProgressBar: ProgressBar? = null

    private var parentView: Refreshable? = null

    private var mHeaderViewHeight:Int=0

     var mHeaderState: Int = Refreshable.STATE_PULL_TO_REFRESH

    private var params: ViewGroup.LayoutParams? = null
    private var mOnRefreshListener: OnRefreshListener? = null

    private val ROTATE_ANIM_DURATION = 250

    var refreshable = true
    private var mRotateUpAnim: Animation = RotateAnimation(
        0.0f, -180.0f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )
    private  var mRotateDownAnim: Animation



    init {
        mRotateUpAnim.duration = ROTATE_ANIM_DURATION.toLong()
        mRotateUpAnim.fillAfter = true

        mRotateDownAnim = RotateAnimation(
            -180.0f, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        mRotateDownAnim.duration = ROTATE_ANIM_DURATION.toLong()
        mRotateDownAnim.fillAfter = true

        LayoutInflater.from(context).inflate(R.layout.listview_refresh_header,this);

        mHeaderView=findViewById(R.id.pull_to_refresh_header)
        mHeaderViewParent=findViewById(R.id.pull_to_refresh_header_parent)
        mHeaderImageView=findViewById(R.id.pull_to_refresh_image)
        mHeaderProgressBar=findViewById(R.id.pull_to_refresh_progress)

        if (ViewDefaultSettingManager.refresh_icon_res != 0) {
            mHeaderImageView?.setImageResource(ViewDefaultSettingManager.refresh_icon_res)
        }

        ViewUtils.measureViewHeight(mHeaderView)
        mHeaderViewHeight = mHeaderView?.measuredHeight?:0

    }

    fun setHeaderHeight(height: Int): Boolean {
        if (mHeaderViewParent == null) {
            return false
        }
        if (params == null) {
            params = mHeaderViewParent?.layoutParams
        }
        if (params?.height != height) {
            params?.height = height
            mHeaderViewParent?.requestLayout()
            Log.i("DefaultReFreshHeadView", "setHeaderHeight:$height")
        }
        return true
    }


    fun onMotionMove() {
        if (mHeaderViewParent.height >= mHeaderViewHeight) {
            if (mHeaderState === Refreshable.STATE_PULL_TO_REFRESH) {
                mHeaderImageView?.startAnimation(mRotateDownAnim)
                mHeaderState = Refreshable.STATE_RELEASE_TO_REFRESH
            }
        } else if (mHeaderState === Refreshable.STATE_RELEASE_TO_REFRESH) {
            mHeaderImageView?.startAnimation(mRotateUpAnim)
            mHeaderState = Refreshable.STATE_PULL_TO_REFRESH
        }
    }

    fun getHeaderViewHeight(): Int? {
        return mHeaderViewHeight
    }


    fun headerRefreshing(): Boolean {
        if (refreshable && mHeaderState === Refreshable.STATE_RELEASE_TO_REFRESH) {
            Log.i("mao", "headerRefreshing...")
            mHeaderState = Refreshable.STATE_REFRESHING
            mHeaderImageView?.clearAnimation()
            mHeaderImageView?.visibility = View.INVISIBLE
            mHeaderProgressBar?.visibility = View.VISIBLE
                mOnRefreshListener?.onHeaderRefresh(parentView)
            return true
        } else if (mHeaderState === Refreshable.STATE_REFRESHING) {
            return true
        }
        return false
    }

    fun refresh() {
       mOnRefreshListener?.onHeaderRefresh(parentView)
    }

    fun refreshComplete(): Boolean {
        if (mHeaderState === Refreshable.STATE_REFRESHING) {
            mHeaderImageView?.visibility = View.VISIBLE
            mHeaderProgressBar?.visibility = View.INVISIBLE
            mHeaderState = Refreshable.STATE_PULL_TO_REFRESH
            return true
        }
        return false
    }

    fun setPullRefreshEnabled(refreshable: Boolean) {
        this.refreshable = refreshable
        mHeaderView?.visibility = if (refreshable) View.VISIBLE else View.GONE
    }

    fun setOnRefreshListener(mOnRefreshListener: OnRefreshListener?) {
        this.mOnRefreshListener = mOnRefreshListener
    }

    fun setRefreshIcon(id: Int) {
        mHeaderImageView?.setImageResource(id)
    }


    fun headerPrepareToRefresh(scrollY: Int) {
        if (mHeaderView != null) {
            Log.i(
                "headerPrepareToRefresh",
                "scrollY: $scrollY :headViewHeight:$mHeaderViewHeight"
            )
            if (scrollY <=-mHeaderViewHeight) {
                if (mHeaderState === Refreshable.STATE_PULL_TO_REFRESH) {
                    mHeaderImageView?.startAnimation(mRotateDownAnim)
                    mHeaderState = Refreshable.STATE_RELEASE_TO_REFRESH
                }
            } else if (mHeaderState === Refreshable.STATE_RELEASE_TO_REFRESH) {
                mHeaderImageView?.startAnimation(mRotateUpAnim)
                mHeaderState = Refreshable.STATE_PULL_TO_REFRESH
            }
        }
    }
}