package com.mao.library.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
public class PermissionsManager {
    private static final String PACKAGE_URL_SCHEME = "package:";

    private Activity mTargetActivity;
    private  permissionRequestListener mListener;

    public PermissionsManager(Activity targetActivity,permissionRequestListener listener) {
        mTargetActivity = targetActivity;
        this.mListener=listener;
    }
    /**
     * 检查权限
     *
     * @param requestCode 请求码
     * @param permissions 准备校验的权限
     */
    public void checkPermissions(int requestCode, String... permissions) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        ArrayList<String> lacks = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mTargetActivity, permission)==PackageManager.PERMISSION_DENIED) {
                lacks.add(permission);
            }
        }
        if (!lacks.isEmpty()) {
            // 有权限没有授权
            String[] lacksPermissions = new String[lacks.size()];
            lacksPermissions = lacks.toArray(lacksPermissions);
            //申请权限
            ActivityCompat.requestPermissions(mTargetActivity, lacksPermissions, requestCode);
        } else {
            // 授权
            if(mListener!=null){
                mListener.authorized(requestCode);
            }
        }
    }

    public void checkPermissionsInFragment(Fragment fragment, int requestCode, String... permissions) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M || fragment==null)
            return;
        ArrayList<String> lacks = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mTargetActivity, permission)==PackageManager.PERMISSION_DENIED) {
                lacks.add(permission);
            }
        }
        if (!lacks.isEmpty()) {
            // 有权限没有授权
            String[] lacksPermissions = new String[lacks.size()];
            lacksPermissions = lacks.toArray(lacksPermissions);
            //申请权限
            fragment.requestPermissions(lacksPermissions, requestCode);
        } else {
            // 授权
            if(mListener!=null){
                mListener.authorized(requestCode);
            }
        }
    }


    /**
     * 复查权限
     * <p>
     * 调用checkPermissions方法后，会提示用户对权限的申请做出选择，选择以后（同意或拒绝）
     * TargetActivity会回调onRequestPermissionsResult方法，
     * 在onRequestPermissionsResult回调方法里，我们调用此方法来复查权限，检查用户的选择是否通过了权限申请
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授权结果
     */
    public void recheckPermissions(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                // 未授权
                if(mListener!=null){
                    mListener.noAuthorization(requestCode, permissions);
                }
                return;
            }
        }

        if(mListener!=null){
            // 授权
            mListener.authorized(requestCode);
        }
    }
    /**
     * 进入应用设置
     *
     * @param context context
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + context.getPackageName()));
        context.startActivity(intent);
    }


    public interface  permissionRequestListener{
        void authorized(int requestCode);
        void noAuthorization(int requestCode, String[] lacksPermissions);
    }
}
