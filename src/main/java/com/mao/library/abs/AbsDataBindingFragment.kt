package com.mao.library.abs

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by maozonghong
 * on 2020/8/6
 */
abstract class AbsDataBindingFragment<VB:ViewDataBinding>:AbsFragment() {

    var dataBinding:VB?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataBinding=DataBindingUtil.bind(mView!!)
        dataBinding?.lifecycleOwner=this
    }
}