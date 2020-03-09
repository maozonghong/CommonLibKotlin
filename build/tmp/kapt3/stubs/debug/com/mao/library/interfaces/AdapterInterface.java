package com.mao.library.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH&J\b\u0010\f\u001a\u00020\u0004H&J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH&J\b\u0010\u000e\u001a\u00020\u0006H&J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0013\u001a\u00020\u00042\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011H&\u00a8\u0006\u0016"}, d2 = {"Lcom/mao/library/interfaces/AdapterInterface;", "T", "", "add", "", "position", "", "item", "(ILjava/lang/Object;)V", "addAll", "list", "", "clear", "getList", "getSize", "lockLoadingImageWhenScrolling", "context", "Landroid/content/Context;", "remove", "setList", "Ljava/util/ArrayList;", "unLockLoadingImage", "Commonlib_debug"})
public abstract interface AdapterInterface<T extends java.lang.Object> {
    
    public abstract void setList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<T> list);
    
    public abstract void addAll(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends T> list);
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.List<T> getList();
    
    public abstract void add(int position, T item);
    
    public abstract void remove(int position);
    
    public abstract int getSize();
    
    public abstract void clear();
    
    public abstract void unLockLoadingImage(@org.jetbrains.annotations.NotNull()
    android.content.Context context);
    
    public abstract void lockLoadingImageWhenScrolling(@org.jetbrains.annotations.NotNull()
    android.content.Context context);
}