package com.mao.library.abs;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0002\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u001e\u0010\u000e\u001a\u00020\u000f2\u0014\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0006H\u0002J\b\u0010\u0011\u001a\u00020\u0004H\u0004J\u0018\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J,\u0010\u0015\u001a\u00020\u00042\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\r2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J.\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00012\b\u0010\u001b\u001a\u0004\u0018\u00010\u00012\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0004\u00a8\u0006\u001c"}, d2 = {"Lcom/mao/library/abs/AbsRequest;", "", "()V", "addParams", "", "map", "", "", "fields", "", "Ljava/lang/reflect/Field;", "(Ljava/util/Map;[Ljava/lang/reflect/Field;)V", "getBody", "", "getMultipartEntity", "Lokhttp3/MultipartBody;", "extras", "init", "onGetField", "key", "value", "onGetFileFiled", "params", "any", "Ljava/io/File;", "putBody", "name", "obj", "Commonlib_debug"})
public class AbsRequest {
    
    @org.jetbrains.annotations.NotNull()
    public java.util.Map<java.lang.String, java.lang.String> getBody() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public okhttp3.MultipartBody getMultipartEntity() {
        return null;
    }
    
    protected final void init() {
    }
    
    private final void addParams(java.util.Map<java.lang.String, java.lang.String> map, java.lang.reflect.Field[] fields) {
    }
    
    public void onGetFileFiled(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    java.io.File any) {
    }
    
    private final okhttp3.MultipartBody getMultipartEntity(java.util.Map<java.lang.String, java.lang.Object> extras) {
        return null;
    }
    
    protected final void putBody(@org.jetbrains.annotations.NotNull()
    java.lang.Object name, @org.jetbrains.annotations.Nullable()
    java.lang.Object obj, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> extras) {
    }
    
    private final void onGetField(java.lang.String key, java.lang.String value) {
    }
    
    public AbsRequest() {
        super();
    }
}