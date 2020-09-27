package com.mao.library.abs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mao.library.interfaces.ActivityInterface
import com.mao.library.interfaces.OnActivityResultListener
import com.mao.library.utils.MainHandlerUtil
import java.util.*

/**
 * Created by maozonghong
 * on 2019/11/21
 */
open class AbsActivity : AppCompatActivity(), ActivityInterface {
    private var onActivityResultListeners: HashSet<OnActivityResultListener>? = null
    private var hasFinishAnimation: Boolean = false
    private var isStart: Boolean = false
    private var isStarting: Boolean = false
    private var needRestrictStarting = true
    private val animationTime = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        val compat = AbsActivityCompat.instance
        compat?.onCreate(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        onSetContentView()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        onSetContentView()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        onSetContentView()
    }

    open fun onSetContentView() {
        val compat = AbsActivityCompat.instance
        compat?.onSetContentView(this)
    }

    //after onStart  onRestoreInstanceState
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        MainHandlerUtil.postDelayed(Runnable{
            setHasFinishAnimation(true)
            onFinishAnimation(true)
            isStarting = false
        }, animationTime.toLong())
    }


    //after resume
    override fun onPostResume() {
        super.onPostResume()
    }

    private fun postFinishStarting() {
        MainHandlerUtil.postDelayed(Runnable{ isStarting = false }, animationTime.toLong())
    }

    override fun onStart() {
        super.onStart()
        isStart = true
    }

    override fun onResume() {
        super.onResume()
        AbsActivityManager.getInstance().onResume(this)
    }


    override fun onRestart() {
        super.onRestart()
        MainHandlerUtil.postDelayed(Runnable{
            setHasFinishAnimation(true)
            onFinishAnimation(false)
        }, animationTime.toLong())
    }

    override fun onPause() {
        super.onPause()
        AbsActivityManager.getInstance().onPause(this)
    }

    override fun getDecorView(): ViewGroup {
        return window.decorView as ViewGroup
    }

    override fun onStop() {
        super.onStop()
        isStart = false
        hasFinishAnimation = false
    }


    override fun onDestroy() {
        super.onDestroy()
        AbsActivityManager.getInstance().onDestroy(this)
    }

    override fun setHasFinishAnimation(hasFinishAnimation: Boolean) {
        this.hasFinishAnimation = hasFinishAnimation
    }

    override fun startActivity(intent: Intent) {

        startActivityForResult(intent, -1, true)
    }

    fun startActivity(intent: Intent, reorder: Boolean) {
        startActivityForResult(intent, -1, reorder)
        // if (hasFinishAnimation) {
        // if (reorder && intent.getComponent() != null) {
        // AbsActivity activity =
        // AbsActivityManager.getAcitivity(intent.getComponent().getClassName());
        // if (activity != null && activity !=
        // AbsActivityManager.getCurrentActivity()) {
        // activity.setHasFinishAnimation(true);
        // activity.finish();
        // }
        // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // }
        // super.startActivity(intent);
        // }
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode, true)
    }

    fun startActivityForResult(intent: Intent, requestCode: Int, reorder: Boolean) {
        if (hasFinishAnimation && (!needRestrictStarting || !isStarting)) {
            isStarting = true
            if (reorder && intent.component != null) {
                val activity = AbsActivityManager.getInstance().getActivity(
                    intent.component!!.className
                )
                if (activity != null) {
                    if (activity !== AbsActivityManager.getInstance().currentActivity) {
                        activity.setHasFinishAnimation(true)
                        activity.finish()
                    }
                    //                     else {
                    // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //                     }
                }
            }
            super.startActivityForResult(intent, requestCode)
            postFinishStarting()
        }
    }

    override fun startActivityFromFragment(fragment: Fragment, intent: Intent, requestCode: Int) {
        startActivityFromFragment(fragment, intent, requestCode, true)
    }

   private fun startActivityFromFragment(fragment: Fragment, intent: Intent, requestCode: Int, reorder: Boolean) {
        if (hasFinishAnimation && (!needRestrictStarting || !isStarting)) {
            isStarting = true
            if (reorder && intent.component != null) {
                val activity =
                    AbsActivityManager.getInstance().getActivity(intent.component!!.className)
                if (activity != null) {
                    if (activity !== AbsActivityManager.getInstance().currentActivity) {
                        activity.setHasFinishAnimation(true)
                        activity.finish()
                    } else {
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    }
                }
            }
            super.startActivityFromFragment(fragment, intent, requestCode)
            postFinishStarting()
        }
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun hasFinishAnimation(): Boolean {
        return hasFinishAnimation
    }


    open fun onFinishAnimation(isCreate: Boolean) {

    }


    protected fun setFullScreen(fullScreen: Boolean) {
        val attrs = window.attributes
        if (fullScreen) {
            attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            window.attributes = attrs
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            window.attributes = attrs
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }


    protected fun setWindowFlag() {
        //1.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // 在不隐藏StatusBar的情况下，将view所在window的显示范围扩展到StatusBar下面。
        // 同时Activity的部分内容也因此被StatusBar覆盖遮挡

        //2.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // 作用:在不隐藏导航栏的情况下，将Activity的显示范围扩展到导航栏底部。
        // 同时Activity的部分内容也因此被NavigationBar覆盖遮挡

        //3.View.SYSTEM_UI_FLAG_LAYOUT_STABLE:
        // 稳定布局。当StatusBar和NavigationBar动态显示和隐藏时，
        // 系统为fitSystemWindow=true的view设置的padding大小都不会变化，所以view的内容的位置也不会发生移动。


        //4.View.SYSTEM_UI_FLAG_FULLSCREEN
        //作用是隐藏StatusBar.此Flag会因为各种的交互（如：跳转到其他应用,下拉StatusBar，弹出键盘）的发送而被系统清除。

        //5.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：(>=api16)
        // 作用是隐藏系统NavigationBar。
        //但是用户的任何交互，都会导致此Flag被系统清除，进而导航栏自动重新显示，
        // 同时View.SYSTEM_UI_FLAG_FULLSCREEN也会被自动清除，因此StatusBar也会同时显示出来。

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (onActivityResultListeners != null) {
            val temp = HashSet<OnActivityResultListener>()
            temp.addAll(onActivityResultListeners!!)

            for (onActivityResultListener in temp) {
                onActivityResultListener.onActivityResult(this, requestCode, resultCode, data)
            }
        }
    }

    open fun addOnActivityResultListener(onActivityResultListener: OnActivityResultListener) {
        if (onActivityResultListeners == null) {
            onActivityResultListeners = HashSet()
        }
        onActivityResultListeners!!.add(onActivityResultListener)
    }


   open fun removeOnActivityResultListener(onActivityResultListener: OnActivityResultListener) {
        if (onActivityResultListeners != null) {
            onActivityResultListeners!!.remove(onActivityResultListener)
        }
    }

   open fun setNeedRestrictStarting(needRestrictStarting: Boolean) {
        this.needRestrictStarting = needRestrictStarting
    }


    override fun finish() {
        if (hasFinishAnimation) {
            super.finish()
        }
    }



    open fun onBackPressed(v: View) {
        onBackPressed()
    }
}
