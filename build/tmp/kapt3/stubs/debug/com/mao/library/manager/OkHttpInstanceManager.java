package com.mao.library.manager;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/mao/library/manager/OkHttpInstanceManager;", "", "()V", "httpClient", "Lokhttp3/OkHttpClient;", "getHttpClient", "()Lokhttp3/OkHttpClient;", "setHttpClient", "(Lokhttp3/OkHttpClient;)V", "Commonlib_debug"})
public final class OkHttpInstanceManager {
    @org.jetbrains.annotations.NotNull()
    private static okhttp3.OkHttpClient httpClient;
    public static final com.mao.library.manager.OkHttpInstanceManager INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient getHttpClient() {
        return null;
    }
    
    public final void setHttpClient(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient p0) {
    }
    
    private OkHttpInstanceManager() {
        super();
    }
}