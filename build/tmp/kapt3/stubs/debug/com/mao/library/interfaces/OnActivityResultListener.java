package com.mao.library.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nH&\u00a8\u0006\u000b"}, d2 = {"Lcom/mao/library/interfaces/OnActivityResultListener;", "", "onActivityResult", "", "activity", "Lcom/mao/library/interfaces/ActivityInterface;", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "Commonlib_debug"})
public abstract interface OnActivityResultListener {
    
    public abstract void onActivityResult(@org.jetbrains.annotations.NotNull()
    com.mao.library.interfaces.ActivityInterface activity, int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data);
}