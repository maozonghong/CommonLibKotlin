package com.mao.library.manager;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/12/17
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J!\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0010\u00a2\u0006\u0002\u0010\u0011J/\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0010\"\u00020\b\u00a2\u0006\u0002\u0010\u0015J)\u0010\u0016\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u00102\u0006\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0007\u001a\u00020\bX\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/mao/library/manager/PermissionsManager;", "", "targetActivity", "Landroid/app/Activity;", "listener", "Lcom/mao/library/manager/PermissionsManager$permissionRequestListener;", "(Landroid/app/Activity;Lcom/mao/library/manager/PermissionsManager$permissionRequestListener;)V", "PACKAGE_URL_SCHEME", "", "mListener", "mTargetActivity", "checkPermissions", "", "requestCode", "", "permissions", "", "(I[Ljava/lang/String;)V", "checkPermissionsInFragment", "fragment", "Landroidx/fragment/app/Fragment;", "(Landroidx/fragment/app/Fragment;I[Ljava/lang/String;)V", "recheckPermissions", "grantResults", "", "(I[Ljava/lang/String;[I)V", "startAppSettings", "context", "Landroid/content/Context;", "permissionRequestListener", "Commonlib_debug"})
public final class PermissionsManager {
    private final java.lang.String PACKAGE_URL_SCHEME = "package:";
    private android.app.Activity mTargetActivity;
    private com.mao.library.manager.PermissionsManager.permissionRequestListener mListener;
    
    /**
     * 检查权限
     *
     * @param requestCode 请求码
     * @param permissions 准备校验的权限
     */
    public final void checkPermissions(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions) {
    }
    
    public final void checkPermissionsInFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String... permissions) {
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
    public final void recheckPermissions(int requestCode, @org.jetbrains.annotations.NotNull()
    java.lang.String[] permissions, @org.jetbrains.annotations.NotNull()
    int[] grantResults) {
    }
    
    /**
     * 进入应用设置
     *
     * @param context context
     */
    public final void startAppSettings(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public PermissionsManager(@org.jetbrains.annotations.NotNull()
    android.app.Activity targetActivity, @org.jetbrains.annotations.Nullable()
    com.mao.library.manager.PermissionsManager.permissionRequestListener listener) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J#\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/mao/library/manager/PermissionsManager$permissionRequestListener;", "", "authorized", "", "requestCode", "", "noAuthorization", "lacksPermissions", "", "", "(I[Ljava/lang/String;)V", "Commonlib_debug"})
    public static abstract interface permissionRequestListener {
        
        public abstract void authorized(int requestCode);
        
        public abstract void noAuthorization(int requestCode, @org.jetbrains.annotations.NotNull()
        java.lang.String[] lacksPermissions);
    }
}