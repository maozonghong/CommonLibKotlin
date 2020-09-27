package com.mao.library.abs

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by maozonghong
 * on 2020/8/6
 */
open class AbsDataBindingActivity<VB: ViewDataBinding> :AbsActivity() {
    var dataBinding: VB?=null

    override fun setContentView(layoutResID: Int) {
        dataBinding= DataBindingUtil.inflate(layoutInflater,layoutResID,null,false)
        if(dataBinding==null){
            super.setContentView(layoutResID)
        }else{
            super.setContentView(dataBinding!!.root)
        }
        onSetContentView()
    }


    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        dataBinding= DataBindingUtil.bind(view)
        super.setContentView(view, params)
        onSetContentView()
    }


    override fun setContentView(view: View) {
        dataBinding= DataBindingUtil.bind(view)
        super.setContentView(view)
        onSetContentView()
    }


    override fun onSetContentView() {
        super.onSetContentView()
        dataBinding?.lifecycleOwner=this
    }

}