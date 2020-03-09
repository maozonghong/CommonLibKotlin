package com.mao.library.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u0017\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010\b\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u0017\u0010\r\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/mao/library/interfaces/OnTaskCompleteListener;", "T", "", "onTaskCancel", "", "onTaskComplete", "result", "(Ljava/lang/Object;)V", "onTaskFailed", "error", "", "code", "", "onTaskLoadMoreComplete", "Commonlib_debug"})
public abstract interface OnTaskCompleteListener<T extends java.lang.Object> {
    
    public abstract void onTaskComplete(@org.jetbrains.annotations.Nullable()
    T result);
    
    public abstract void onTaskFailed(@org.jetbrains.annotations.Nullable()
    java.lang.String error, int code);
    
    public abstract void onTaskCancel();
    
    public abstract void onTaskLoadMoreComplete(@org.jetbrains.annotations.Nullable()
    T result);
}