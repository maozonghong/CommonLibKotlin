package com.mao.library.interfaces

import android.app.Application
import com.mao.library.abs.AbsApplication

/**
 * Created by maozonghong
 * on 2020/6/18 组件化都model Application共存
 */
interface IComponentApplication {

   fun onCreate(application:AbsApplication)

   fun getApplication(): Application
}