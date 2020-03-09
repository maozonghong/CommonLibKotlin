package com.mao.library.manager;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u0000 \u00032\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0006"}, d2 = {"Lcom/mao/library/manager/OkHttpManager;", "", "()V", "Companion", "GzipRequestInterceptor", "MethodType", "Commonlib_debug"})
public final class OkHttpManager {
    private static boolean gzip_enable;
    private static final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, okhttp3.Call> requests = null;
    private static com.mao.library.manager.OkHttpManager.GzipRequestInterceptor gzipRequestInterceptor;
    public static final com.mao.library.manager.OkHttpManager.Companion Companion = null;
    
    private OkHttpManager() {
        super();
    }
    
    public static final boolean isNetworkAvailable() {
        return false;
    }
    
    public static final boolean isNetworkOnWifi() {
        return false;
    }
    
    public static final boolean isNetworkOnMobile() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
    com.mao.library.manager.OkHttpManager.MethodType type) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
    com.mao.library.manager.OkHttpManager.MethodType type) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> params) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
    okhttp3.MultipartBody mpEntity, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
    com.mao.library.manager.OkHttpManager.MethodType type) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
    okhttp3.MultipartBody mpEntity, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, java.lang.String> params) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
    okhttp3.MultipartBody mpEntity) {
        return null;
    }
    
    /**
     * 取消当前网络请求
     *
     * @param id
     */
    public static final void cancel(int id) {
    }
    
    public static final void setGzip_enable(boolean gzip_enable) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/mao/library/manager/OkHttpManager$MethodType;", "", "(Ljava/lang/String;I)V", "Post", "Get", "Restful", "Commonlib_debug"})
    public static enum MethodType {
        /*public static final*/ Post /* = new Post() */,
        /*public static final*/ Get /* = new Get() */,
        /*public static final*/ Restful /* = new Restful() */;
        
        MethodType() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/mao/library/manager/OkHttpManager$GzipRequestInterceptor;", "Lokhttp3/Interceptor;", "()V", "gzip", "Lokhttp3/RequestBody;", "body", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "Commonlib_debug"})
    public static final class GzipRequestInterceptor implements okhttp3.Interceptor {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
        okhttp3.Interceptor.Chain chain) throws java.io.IOException {
            return null;
        }
        
        private final okhttp3.RequestBody gzip(okhttp3.RequestBody body) {
            return null;
        }
        
        public GzipRequestInterceptor() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\tH\u0007JN\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007J\\\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007J.\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0018H\u0007J6\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J@\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u000f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u000e2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u000f0\u0018J\b\u0010\u001f\u001a\u00020\u0006H\u0007J\b\u0010 \u001a\u00020\u0006H\u0007J\b\u0010!\u001a\u00020\u0006H\u0007J\u0010\u0010\"\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/mao/library/manager/OkHttpManager$Companion;", "", "()V", "gzipRequestInterceptor", "Lcom/mao/library/manager/OkHttpManager$GzipRequestInterceptor;", "gzip_enable", "", "requests", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lokhttp3/Call;", "addHeaders", "", "headers", "Ljava/util/HashMap;", "", "request", "Lokhttp3/Request$Builder;", "cancel", "id", "getResponse", "Lokhttp3/Response;", "url", "params", "", "type", "Lcom/mao/library/manager/OkHttpManager$MethodType;", "mpEntity", "Lokhttp3/MultipartBody;", "getResponseByRestful", "list", "isNetworkAvailable", "isNetworkOnMobile", "isNetworkOnWifi", "setGzip_enable", "Commonlib_debug"})
    public static final class Companion {
        
        public final boolean isNetworkAvailable() {
            return false;
        }
        
        public final boolean isNetworkOnWifi() {
            return false;
        }
        
        public final boolean isNetworkOnMobile() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
        com.mao.library.manager.OkHttpManager.MethodType type) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> params) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
        com.mao.library.manager.OkHttpManager.MethodType type) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> params) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
        okhttp3.MultipartBody mpEntity, @org.jetbrains.annotations.Nullable()
        java.util.Map<java.lang.String, java.lang.String> params, @org.jetbrains.annotations.NotNull()
        com.mao.library.manager.OkHttpManager.MethodType type) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
        okhttp3.MultipartBody mpEntity, @org.jetbrains.annotations.Nullable()
        java.util.Map<java.lang.String, java.lang.String> params) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponse(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.Nullable()
        okhttp3.MultipartBody mpEntity) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final okhttp3.Response getResponseByRestful(int id, @org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.util.HashMap<java.lang.String, java.lang.String> headers, @org.jetbrains.annotations.NotNull()
        java.util.Map<java.lang.String, java.lang.String> list) {
            return null;
        }
        
        private final void addHeaders(java.util.HashMap<java.lang.String, java.lang.String> headers, okhttp3.Request.Builder request) {
        }
        
        /**
         * 取消当前网络请求
         *
         * @param id
         */
        public final void cancel(int id) {
        }
        
        public final void setGzip_enable(boolean gzip_enable) {
        }
        
        private Companion() {
            super();
        }
    }
}