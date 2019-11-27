package com.mao.library.abs

import android.content.Context
import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View

/**
 * Created by maozonghong
 * on 2019/11/21
 */
abstract class AbsFragment :Fragment(){
    private var mView: View? = null
    private var isInited: Boolean = false
    private var isSelected: Boolean = false
    private var isStart: Boolean = false
    private var isFinishing:Boolean = false

    /**
     * 实现此方法 返回fragment的布局view
     * @return
     */
     open fun getViewId(): Int {

        return 0
    }

     open fun getRootView(context: Context): View? {
        return null
    }

    fun findViewById(id: Int): View? {
        return if (mView != null) {
            mView!!.findViewById(id)
        } else null
    }

     abstract fun init()

     abstract fun initListeners()


   override fun onAttach(context: Context) {
        super.onAttach(context)
        if (mView == null) {
            if (getViewId() == 0) {
                mView = getRootView(context)
            } else {
                mView = View.inflate(context, getViewId(), null)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null && container != null) {
            if (getViewId() == 0) {
                mView = getRootView(container.context)
            } else {
                mView = View.inflate(container.context, getViewId(), null)
            }
        }
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFinishing=false
    }

    override fun onDestroyView() {
        if(mView?.parent!=null){
            (mView!!.parent as ViewGroup).removeView(mView)
        }
        super.onDestroyView()
    }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isInited) {
            isInited = true
            init()
            initListeners()
        }
    }

    override fun startActivity(intent: Intent) {
        startActivityForResult(intent, -1)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        if (activity != null) {
            if (activity is AbsActivity) {
                (activity as AbsActivity).startActivityFromFragment(this, intent, requestCode)
            } else {
                activity!!.startActivityFromFragment(this, intent, requestCode)
            }
        }
    }

    open fun isSelected(): Boolean {
        return isSelected
    }

   open fun onSelect() {
        isSelected = true
    }

    open fun onUnselect() {
        isSelected = false
    }

    open fun isFinishing(): Boolean {
        return this.isFinishing
    }

    open fun isStart(): Boolean {
        return isStart
    }

    override fun onStart() {
        super.onStart()
        this.isStart = true
    }

    override fun onStop() {
        super.onStop()
        isStart = false
    }

    /**
     * 需要Activity回调
     * @return
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        isFinishing = true
        isInited = false
        mView = null
    }
}