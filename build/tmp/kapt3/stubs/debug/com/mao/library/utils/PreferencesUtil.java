package com.mao.library.utils;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/12/18
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000eJ\u0016\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\fJ\u0016\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0014J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u000eJ\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000eJ\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u000eJ\u0016\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\fJ\u0016\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u0014J\u0016\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u0017J\u0016\u0010\u001f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u0019J\u0018\u0010 \u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000e2\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006!"}, d2 = {"Lcom/mao/library/utils/PreferencesUtil;", "", "()V", "mPreferences", "Landroid/content/SharedPreferences;", "getMPreferences", "()Landroid/content/SharedPreferences;", "mPreferences$delegate", "Lkotlin/Lazy;", "clear", "", "contains", "", "key", "", "delete", "name", "readBoolean", "defaultVal", "readFloat", "", "defaultval", "readInt", "", "readLong", "", "readString", "writeBoolean", "value", "writeFloat", "writeInt", "writeLong", "writeString", "Commonlib_debug"})
public final class PreferencesUtil {
    private static final kotlin.Lazy mPreferences$delegate = null;
    public static final com.mao.library.utils.PreferencesUtil INSTANCE = null;
    
    private final android.content.SharedPreferences getMPreferences() {
        return null;
    }
    
    public final boolean contains(@org.jetbrains.annotations.Nullable()
    java.lang.String key) {
        return false;
    }
    
    public final void clear() {
    }
    
    public final boolean delete(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String readString(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    public final long readLong(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return 0L;
    }
    
    public final int readInt(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int defaultval) {
        return 0;
    }
    
    public final float readFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String name, float defaultval) {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String readString(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String defaultVal) {
        return null;
    }
    
    public final boolean readBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean defaultVal) {
        return false;
    }
    
    public final boolean writeString(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String value) {
        return false;
    }
    
    public final boolean writeLong(@org.jetbrains.annotations.NotNull()
    java.lang.String name, long value) {
        return false;
    }
    
    public final boolean writeInt(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int value) {
        return false;
    }
    
    public final boolean writeFloat(@org.jetbrains.annotations.NotNull()
    java.lang.String name, float value) {
        return false;
    }
    
    public final boolean writeBoolean(@org.jetbrains.annotations.NotNull()
    java.lang.String name, boolean value) {
        return false;
    }
    
    private PreferencesUtil() {
        super();
    }
}