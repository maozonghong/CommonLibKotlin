package com.mao.library.abs;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\b\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\"\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0012\u0010\u001e\u001a\u00020\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010!\u001a\u00020\u000fH\u0014J\u0010\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u0007H\u0016J\b\u0010$\u001a\u00020\u000fH\u0014J\u0012\u0010%\u001a\u00020\u000f2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J\b\u0010&\u001a\u00020\u000fH\u0014J\b\u0010\'\u001a\u00020\u000fH\u0014J\b\u0010(\u001a\u00020\u000fH\u0014J\b\u0010)\u001a\u00020\u000fH\u0004J\b\u0010*\u001a\u00020\u000fH\u0014J\b\u0010+\u001a\u00020\u000fH\u0014J\b\u0010,\u001a\u00020\u000fH\u0002J\u0010\u0010-\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0016J\u0010\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\u001dH\u0016J\u0018\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u000201H\u0016J\u0010\u0010.\u001a\u00020\u000f2\u0006\u00102\u001a\u00020\u0005H\u0016J\u0010\u00103\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u0007H\u0004J\u0010\u00105\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u00106\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u0007H\u0016J\u0010\u00107\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u001aH\u0016J\u0016\u00107\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u001a2\u0006\u00109\u001a\u00020\u0007J\u0018\u0010:\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0005H\u0016J\u001e\u0010:\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u00109\u001a\u00020\u0007J \u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020=2\u0006\u00108\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u0005H\u0016J(\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020=2\u0006\u00108\u001a\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u00109\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006>"}, d2 = {"Lcom/mao/library/abs/AbsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/mao/library/interfaces/ActivityInterface;", "()V", "animationTime", "", "hasFinishAnimation", "", "isStart", "isStarting", "needRestrictStarting", "onActivityResultListeners", "Ljava/util/HashSet;", "Lcom/mao/library/interfaces/OnActivityResultListener;", "addOnActivityResultListener", "", "onActivityResultListener", "finish", "getActivity", "Landroid/app/Activity;", "getDecorView", "Landroid/view/ViewGroup;", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onFinishAnimation", "isCreate", "onPause", "onPostCreate", "onPostResume", "onRestart", "onResume", "onSetContentView", "onStart", "onStop", "postFinishStarting", "removeOnActivityResultListener", "setContentView", "view", "params", "Landroid/view/ViewGroup$LayoutParams;", "layoutResID", "setFullScreen", "fullScreen", "setHasFinishAnimation", "setNeedRestrictStarting", "startActivity", "intent", "reorder", "startActivityForResult", "startActivityFromFragment", "fragment", "Landroidx/fragment/app/Fragment;", "Commonlib_debug"})
public class AbsActivity extends androidx.appcompat.app.AppCompatActivity implements com.mao.library.interfaces.ActivityInterface {
    private java.util.HashSet<com.mao.library.interfaces.OnActivityResultListener> onActivityResultListeners;
    private boolean hasFinishAnimation;
    private boolean isStart;
    private boolean isStarting;
    private boolean needRestrictStarting;
    private final int animationTime = 500;
    private java.util.HashMap _$_findViewCache;
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void setContentView(int layoutResID) {
    }
    
    @java.lang.Override()
    public void setContentView(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.NotNull()
    android.view.ViewGroup.LayoutParams params) {
    }
    
    @java.lang.Override()
    public void setContentView(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    protected final void onSetContentView() {
    }
    
    @java.lang.Override()
    protected void onPostCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onPostResume() {
    }
    
    private final void postFinishStarting() {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onRestart() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.view.ViewGroup getDecorView() {
        return null;
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    public void setHasFinishAnimation(boolean hasFinishAnimation) {
    }
    
    @java.lang.Override()
    public void startActivity(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    public final void startActivity(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, boolean reorder) {
    }
    
    @java.lang.Override()
    public void startActivityForResult(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, int requestCode) {
    }
    
    public final void startActivityForResult(@org.jetbrains.annotations.NotNull()
    android.content.Intent intent, int requestCode, boolean reorder) {
    }
    
    @java.lang.Override()
    public void startActivityFromFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent, int requestCode) {
    }
    
    private final void startActivityFromFragment(androidx.fragment.app.Fragment fragment, android.content.Intent intent, int requestCode, boolean reorder) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.app.Activity getActivity() {
        return null;
    }
    
    @java.lang.Override()
    public boolean hasFinishAnimation() {
        return false;
    }
    
    public void onFinishAnimation(boolean isCreate) {
    }
    
    protected final void setFullScreen(boolean fullScreen) {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    public void addOnActivityResultListener(@org.jetbrains.annotations.NotNull()
    com.mao.library.interfaces.OnActivityResultListener onActivityResultListener) {
    }
    
    public void removeOnActivityResultListener(@org.jetbrains.annotations.NotNull()
    com.mao.library.interfaces.OnActivityResultListener onActivityResultListener) {
    }
    
    public void setNeedRestrictStarting(boolean needRestrictStarting) {
    }
    
    @java.lang.Override()
    public void finish() {
    }
    
    public void onBackPressed(@org.jetbrains.annotations.NotNull()
    android.view.View v) {
    }
    
    public AbsActivity() {
        super();
    }
}