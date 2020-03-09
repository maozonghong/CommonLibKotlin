package com.mao.library.abs;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \n2\u00020\u0001:\u0002\n\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\f"}, d2 = {"Lcom/mao/library/abs/AbsActivityCompat;", "", "()V", "onCreate", "", "absActivity", "Lcom/mao/library/interfaces/ActivityInterface;", "savedInstanceState", "Landroid/os/Bundle;", "onSetContentView", "Companion", "MyFrameLayout", "Commonlib_debug"})
public abstract class AbsActivityCompat {
    @org.jetbrains.annotations.Nullable()
    private static com.mao.library.abs.AbsActivityCompat instance;
    public static final com.mao.library.abs.AbsActivityCompat.Companion Companion = null;
    
    public abstract void onCreate(@org.jetbrains.annotations.NotNull()
    com.mao.library.interfaces.ActivityInterface absActivity, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState);
    
    public abstract void onSetContentView(@org.jetbrains.annotations.NotNull()
    com.mao.library.interfaces.ActivityInterface absActivity);
    
    public AbsActivityCompat() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/mao/library/abs/AbsActivityCompat$MyFrameLayout;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "isChanged", "", "mPaddingBottom", "", "fitSystemWindows", "insets", "Landroid/graphics/Rect;", "Commonlib_debug"})
    public static final class MyFrameLayout extends android.widget.FrameLayout {
        private int mPaddingBottom;
        private boolean isChanged;
        private java.util.HashMap _$_findViewCache;
        
        @java.lang.Override()
        protected boolean fitSystemWindows(@org.jetbrains.annotations.NotNull()
        android.graphics.Rect insets) {
            return false;
        }
        
        public MyFrameLayout(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            super(null);
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/mao/library/abs/AbsActivityCompat$Companion;", "", "()V", "instance", "Lcom/mao/library/abs/AbsActivityCompat;", "getInstance", "()Lcom/mao/library/abs/AbsActivityCompat;", "setInstance", "(Lcom/mao/library/abs/AbsActivityCompat;)V", "Commonlib_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.mao.library.abs.AbsActivityCompat getInstance() {
            return null;
        }
        
        public final void setInstance(@org.jetbrains.annotations.Nullable()
        com.mao.library.abs.AbsActivityCompat p0) {
        }
        
        private Companion() {
            super();
        }
    }
}