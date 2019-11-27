package com.mao.library.abs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.mao.library.interfaces.ActivityInterface
import com.mao.library.interfaces.OnActivityResultListener
import com.mao.library.utils.MainHandlerUtil

import java.util.HashSet

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

    protected fun onSetContentView() {
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
