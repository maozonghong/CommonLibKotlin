package com.mao.library.manager;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J0\u0010\u000f\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\u001c\u0010\u0010\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u0012\u0010\u0011\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007J\u0012\u0010\u0014\u001a\u00020\u00042\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007\u00a8\u0006\u0016"}, d2 = {"Lcom/mao/library/manager/AsyncImageManager;", "", "()V", "doFinishLoad", "", "imageView", "Landroid/widget/ImageView;", "url", "", "bitmap", "Landroid/graphics/Bitmap;", "defaultImage", "", "listener", "Lcom/mao/library/interfaces/AsyncImageListener;", "downloadAsync", "loadVideoScreenshot", "lock", "context", "Landroid/content/Context;", "unlock", "MySimpleTarget", "Commonlib_debug"})
public final class AsyncImageManager {
    public static final com.mao.library.manager.AsyncImageManager INSTANCE = null;
    
    public static final void loadVideoScreenshot(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView imageView, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    public static final void downloadAsync(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView imageView, @org.jetbrains.annotations.Nullable()
    java.lang.String url, int defaultImage, @org.jetbrains.annotations.Nullable()
    com.mao.library.interfaces.AsyncImageListener listener) {
    }
    
    public static final void downloadAsync(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView imageView, @org.jetbrains.annotations.Nullable()
    java.lang.String url, int defaultImage) {
    }
    
    public static final void lock(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
    }
    
    public static final void unlock(@org.jetbrains.annotations.Nullable()
    android.content.Context context) {
    }
    
    private final void doFinishLoad(android.widget.ImageView imageView, java.lang.String url, android.graphics.Bitmap bitmap, int defaultImage, com.mao.library.interfaces.AsyncImageListener listener) {
    }
    
    private AsyncImageManager() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B#\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tB\u000f\b\u0016\u0012\u0006\u0010\n\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u000bR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/mao/library/manager/AsyncImageManager$MySimpleTarget;", "Lcom/bumptech/glide/request/target/CustomViewTarget;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "imageView", "imageUrl", "", "listener", "Lcom/mao/library/interfaces/AsyncImageListener;", "(Landroid/widget/ImageView;Ljava/lang/String;Lcom/mao/library/interfaces/AsyncImageListener;)V", "view", "(Landroid/widget/ImageView;)V", "getImageUrl", "()Ljava/lang/String;", "setImageUrl", "(Ljava/lang/String;)V", "Commonlib_debug"})
    public static abstract class MySimpleTarget extends com.bumptech.glide.request.target.CustomViewTarget<android.widget.ImageView, android.graphics.Bitmap> {
        private com.mao.library.interfaces.AsyncImageListener listener;
        @org.jetbrains.annotations.Nullable()
        private java.lang.String imageUrl;
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String getImageUrl() {
            return null;
        }
        
        public final void setImageUrl(@org.jetbrains.annotations.Nullable()
        java.lang.String p0) {
        }
        
        public MySimpleTarget(@org.jetbrains.annotations.NotNull()
        android.widget.ImageView imageView, @org.jetbrains.annotations.Nullable()
        java.lang.String imageUrl, @org.jetbrains.annotations.Nullable()
        com.mao.library.interfaces.AsyncImageListener listener) {
            super(null);
        }
        
        /**
         * Constructor that defaults `waitForLayout` to `false`.
         *
         * @param view
         */
        public MySimpleTarget(@org.jetbrains.annotations.NotNull()
        android.widget.ImageView view) {
            super(null);
        }
    }
}