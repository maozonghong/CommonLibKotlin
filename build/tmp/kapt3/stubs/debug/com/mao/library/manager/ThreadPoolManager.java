package com.mao.library.manager;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/mao/library/manager/ThreadPoolManager;", "", "()V", "Companion", "Commonlib_debug"})
public final class ThreadPoolManager {
    private static final java.util.concurrent.ThreadPoolExecutor imageExecutorService = null;
    private static final java.util.concurrent.ThreadPoolExecutor downloadExecutorService = null;
    private static final java.util.concurrent.ThreadPoolExecutor httpExecutorService = null;
    private static final java.util.concurrent.ScheduledExecutorService scheduledExecutorService = null;
    private static final java.util.concurrent.ThreadPoolExecutor cacheExecutorService = null;
    public static final com.mao.library.manager.ThreadPoolManager.Companion Companion = null;
    
    public ThreadPoolManager() {
        super();
    }
    
    public static final void downloadExecute(@org.jetbrains.annotations.NotNull()
    java.lang.Runnable runnable) {
    }
    
    public static final void cacheExecute(@org.jetbrains.annotations.NotNull()
    java.lang.Runnable runnable) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J \u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000e\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J \u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000e\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011J\u000e\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ \u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000e\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011J\u000e\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ \u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u000e\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/mao/library/manager/ThreadPoolManager$Companion;", "", "()V", "cacheExecutorService", "Ljava/util/concurrent/ThreadPoolExecutor;", "downloadExecutorService", "httpExecutorService", "imageExecutorService", "scheduledExecutorService", "Ljava/util/concurrent/ScheduledExecutorService;", "cacheExecute", "", "runnable", "Ljava/lang/Runnable;", "Ljava/util/concurrent/Future;", "T", "callable", "Ljava/util/concurrent/Callable;", "downloadExecute", "downloadSubmit", "httpExecute", "httpSubmit", "imageExecute", "imageSubmit", "Commonlib_debug"})
    public static final class Companion {
        
        public final void imageExecute(@org.jetbrains.annotations.NotNull()
        java.lang.Runnable runnable) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>java.util.concurrent.Future<T> imageSubmit(@org.jetbrains.annotations.NotNull()
        java.util.concurrent.Callable<T> callable) {
            return null;
        }
        
        public final void downloadExecute(@org.jetbrains.annotations.NotNull()
        java.lang.Runnable runnable) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>java.util.concurrent.Future<T> downloadSubmit(@org.jetbrains.annotations.NotNull()
        java.util.concurrent.Callable<T> callable) {
            return null;
        }
        
        public final void httpExecute(@org.jetbrains.annotations.NotNull()
        java.lang.Runnable runnable) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>java.util.concurrent.Future<T> httpSubmit(@org.jetbrains.annotations.NotNull()
        java.util.concurrent.Callable<T> callable) {
            return null;
        }
        
        public final void cacheExecute(@org.jetbrains.annotations.NotNull()
        java.lang.Runnable runnable) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final <T extends java.lang.Object>java.util.concurrent.Future<T> cacheExecute(@org.jetbrains.annotations.NotNull()
        java.util.concurrent.Callable<T> callable) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}