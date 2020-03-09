package com.mao.library.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * Created by maozonghong
 * on 2019/12/17
 */

class PermissionsManager {
    private val PACKAGE_URL_SCHEME = "package:"

    private var mTargetActivity: Activity
    private var mListener: permissionRequestListener? = null

    constructor(targetActivity: Activity, listener: permissionRequestListener?) {
        mTargetActivity = targetActivity
        mListener = listener
    }

    /**
     * 检查权限
     *
     * @param requestCode 请求码
     * @param permissions 准备校验的权限
     */
    fun checkPermissions(requestCode: Int, permissions: Array<String>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val lacks: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(mTargetActivity, permission) == PackageManager.PERMISSION_DENIED) {
                lacks.add(permission)
            }
        }
        if (lacks.isNotEmpty()) { // 有权限没有授权
            var lacksPermissions: Array<String?>? =
                arrayOfNulls(lacks.size)
            lacksPermissions = lacks.toArray(lacksPermissions)
            //申请权限
            requestPermissions(mTargetActivity, lacksPermissions, requestCode)
        } else { // 授权
            mListener?.authorized(requestCode)
        }
    }

    fun checkPermissionsInFragment(fragment: Fragment, requestCode: Int, vararg permissions: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || fragment == null) return
        val lacks: ArrayList<String> = ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    mTargetActivity!!,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                lacks.add(permission)
            }
        }
        if (lacks.isNotEmpty()) { // 有权限没有授权
            var lacksPermissions: Array<String?>? =
                arrayOfNulls(lacks.size)
            lacksPermissions = lacks.toArray(lacksPermissions)
            //申请权限
            fragment.requestPermissions(lacksPermissions, requestCode)
        } else { // 授权
            mListener?.authorized(requestCode)
        }
    }


    /**
     * 复查权限
     *
     *
     * 调用checkPermissions方法后，会提示用户对权限的申请做出选择，选择以后（同意或拒绝）
     * TargetActivity会回调onRequestPermissionsResult方法，
     * 在onRequestPermissionsResult回调方法里，我们调用此方法来复查权限，检查用户的选择是否通过了权限申请
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    fun recheckPermissions(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) { // 未授权
                if (mListener != null) {
                    mListener!!.noAuthorization(requestCode, permissions)
                }
                return
            }
        }
        mListener?.authorized(requestCode)
    }

    /**
     * 进入应用设置
     *
     * @param context context
     */
    fun startAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + context.packageName)
        context.startActivity(intent)
    }


    interface permissionRequestListener {
        fun authorized(requestCode: Int)
        fun noAuthorization(
            requestCode: Int,
            lacksPermissions: Array<String>
        )
    }
}