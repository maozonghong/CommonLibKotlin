package com.mao.library.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u000bH&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH&\u00a8\u0006\r"}, d2 = {"Lcom/mao/library/interfaces/ActivityInterface;", "", "finish", "", "getActivity", "Landroid/app/Activity;", "getDecorView", "Landroid/view/ViewGroup;", "getWindow", "Landroid/view/Window;", "hasFinishAnimation", "", "setHasFinishAnimation", "Commonlib_debug"})
public abstract interface ActivityInterface {
    
    @org.jetbrains.annotations.NotNull()
    public abstract android.app.Activity getActivity();
    
    @org.jetbrains.annotations.NotNull()
    public abstract android.view.Window getWindow();
    
    @org.jetbrains.annotations.NotNull()
    public abstract android.view.ViewGroup getDecorView();
    
    public abstract void setHasFinishAnimation(boolean hasFinishAnimation);
    
    public abstract void finish();
    
    public abstract boolean hasFinishAnimation();
}