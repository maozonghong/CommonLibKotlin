package com.mao.library.interfaces;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0012\u0010\u000b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&\u00a8\u0006\f"}, d2 = {"Lcom/mao/library/interfaces/AsyncImageListener;", "", "onLoadFailed", "", "url", "", "onLoadFinish", "imageView", "Landroid/widget/ImageView;", "bitmap", "Landroid/graphics/Bitmap;", "onLoadStart", "Commonlib_debug"})
public abstract interface AsyncImageListener {
    
    public abstract void onLoadFailed(@org.jetbrains.annotations.Nullable()
    java.lang.String url);
    
    public abstract void onLoadStart(@org.jetbrains.annotations.Nullable()
    java.lang.String url);
    
    public abstract void onLoadFinish(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView imageView, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap bitmap);
}