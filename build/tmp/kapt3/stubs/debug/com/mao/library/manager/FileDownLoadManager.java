package com.mao.library.manager;

import java.lang.System;

/**
 * 文件下载管理器
 * Created by maozonghong
 * on 2019/11/21
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003./0B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fJ(\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J&\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J.\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002J@\u0010\b\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0002J\u0010\u0010\u001f\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0005H\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u0005H\u0002J\u000e\u0010!\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u0005J\u0018\u0010\"\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010$\u001a\u00020%H\u0002J \u0010&\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\u000eH\u0002J\u0018\u0010)\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010*\u001a\u00020%H\u0002J\u0018\u0010+\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010,\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u0005J$\u0010-\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/mao/library/manager/FileDownLoadManager;", "", "()V", "downloadingUrls", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/mao/library/manager/FileDownLoadManager$DownloadObserverSet;", "tempSet", "download", "Ljava/io/File;", "url", "observer", "Lcom/mao/library/manager/FileDownLoadManager$DownloadObserver;", "isCache", "", "filePath", "fileDir", "response", "Lokhttp3/Response;", "inputStream", "Ljava/io/InputStream;", "fs", "Ljava/io/RandomAccessFile;", "buffer", "", "count", "", "info", "Lcom/mao/library/bean/DownloadInfo;", "downloadFromNet", "Lcom/mao/library/manager/FileDownLoadManager$DownloadResult;", "getObservers", "isCanceled", "isDownloading", "onDownloadFail", "", "error_code", "", "onDownloadFinish", "file", "isExists", "onDownloadInProgress", "percent", "onDownloadStart", "stopDownload", "tryDownload", "DownloadObserver", "DownloadObserverSet", "DownloadResult", "Commonlib_debug"})
public final class FileDownLoadManager {
    private static final java.util.concurrent.ConcurrentHashMap<java.lang.String, com.mao.library.manager.FileDownLoadManager.DownloadObserverSet> downloadingUrls = null;
    private static final com.mao.library.manager.FileDownLoadManager.DownloadObserverSet tempSet = null;
    public static final com.mao.library.manager.FileDownLoadManager INSTANCE = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.Nullable()
    com.mao.library.manager.FileDownLoadManager.DownloadObserver observer) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url, boolean isCache, @org.jetbrains.annotations.Nullable()
    com.mao.library.manager.FileDownLoadManager.DownloadObserver observer) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url, boolean isCache) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.lang.String filePath, @org.jetbrains.annotations.Nullable()
    com.mao.library.manager.FileDownLoadManager.DownloadObserver observer) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.io.File download(@org.jetbrains.annotations.NotNull()
    java.lang.String url, @org.jetbrains.annotations.NotNull()
    java.lang.String filePath) {
        return null;
    }
    
    /**
     * 主线程中调用必然返回null
     *
     * @param url
     * @param observer
     * @return
     */
    private final java.io.File download(java.lang.String url, java.lang.String filePath, java.lang.String fileDir, com.mao.library.manager.FileDownLoadManager.DownloadObserver observer) {
        return null;
    }
    
    private final synchronized com.mao.library.manager.FileDownLoadManager.DownloadObserverSet getObservers(java.lang.String url) {
        return null;
    }
    
    public final boolean isDownloading(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
        return false;
    }
    
    private final java.io.File tryDownload(java.lang.String url, java.lang.String filePath, java.lang.String fileDir) {
        return null;
    }
    
    private final com.mao.library.manager.FileDownLoadManager.DownloadResult downloadFromNet(java.lang.String url, java.lang.String filePath) {
        return null;
    }
    
    private final boolean download(java.lang.String url, okhttp3.Response response, java.io.InputStream inputStream, java.io.RandomAccessFile fs, byte[] buffer, long count, com.mao.library.bean.DownloadInfo info) throws java.io.IOException {
        return false;
    }
    
    private final void onDownloadFinish(java.lang.String url, java.io.File file, boolean isExists) {
    }
    
    private final void onDownloadStart(java.lang.String url, long count) {
    }
    
    private final void onDownloadInProgress(java.lang.String url, int percent) {
    }
    
    private final void onDownloadFail(java.lang.String url, int error_code) {
    }
    
    private final boolean isCanceled(java.lang.String url) {
        return false;
    }
    
    public final void stopDownload(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    private FileDownLoadManager() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/mao/library/manager/FileDownLoadManager$DownloadObserverSet;", "Ljava/util/HashSet;", "Lcom/mao/library/manager/FileDownLoadManager$DownloadObserver;", "()V", "file", "Ljava/io/File;", "getFile$Commonlib_debug", "()Ljava/io/File;", "setFile$Commonlib_debug", "(Ljava/io/File;)V", "isCanceled", "", "isCanceled$Commonlib_debug", "()Z", "setCanceled$Commonlib_debug", "(Z)V", "Commonlib_debug"})
    static final class DownloadObserverSet extends java.util.HashSet<com.mao.library.manager.FileDownLoadManager.DownloadObserver> {
        @org.jetbrains.annotations.Nullable()
        private java.io.File file;
        private boolean isCanceled;
        
        @org.jetbrains.annotations.Nullable()
        public final java.io.File getFile$Commonlib_debug() {
            return null;
        }
        
        public final void setFile$Commonlib_debug(@org.jetbrains.annotations.Nullable()
        java.io.File p0) {
        }
        
        public final boolean isCanceled$Commonlib_debug() {
            return false;
        }
        
        public final void setCanceled$Commonlib_debug(boolean p0) {
        }
        
        public DownloadObserverSet() {
            super();
        }
        
        @java.lang.Override()
        public boolean contains(com.mao.library.manager.FileDownLoadManager.DownloadObserver p0) {
            return false;
        }
        
        @java.lang.Override()
        public final boolean contains(java.lang.Object p0) {
            return false;
        }
        
        @java.lang.Override()
        public boolean remove(com.mao.library.manager.FileDownLoadManager.DownloadObserver p0) {
            return false;
        }
        
        @java.lang.Override()
        public final boolean remove(java.lang.Object p0) {
            return false;
        }
        
        @java.lang.Override()
        public int getSize() {
            return 0;
        }
        
        @java.lang.Override()
        public final int size() {
            return 0;
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/mao/library/manager/FileDownLoadManager$DownloadResult;", "", "()V", "error_code", "", "getError_code$Commonlib_debug", "()I", "setError_code$Commonlib_debug", "(I)V", "file", "Ljava/io/File;", "getFile$Commonlib_debug", "()Ljava/io/File;", "setFile$Commonlib_debug", "(Ljava/io/File;)V", "isCancel", "", "isCancel$Commonlib_debug", "()Z", "setCancel$Commonlib_debug", "(Z)V", "Commonlib_debug"})
    static final class DownloadResult {
        @org.jetbrains.annotations.Nullable()
        private java.io.File file;
        private boolean isCancel;
        private int error_code;
        
        @org.jetbrains.annotations.Nullable()
        public final java.io.File getFile$Commonlib_debug() {
            return null;
        }
        
        public final void setFile$Commonlib_debug(@org.jetbrains.annotations.Nullable()
        java.io.File p0) {
        }
        
        public final boolean isCancel$Commonlib_debug() {
            return false;
        }
        
        public final void setCancel$Commonlib_debug(boolean p0) {
        }
        
        public final int getError_code$Commonlib_debug() {
            return 0;
        }
        
        public final void setError_code$Commonlib_debug(int p0) {
        }
        
        public DownloadResult() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\nH\u0016J\u0018\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0016\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/mao/library/manager/FileDownLoadManager$DownloadObserver;", "", "()V", "any", "(Ljava/lang/Object;)V", "onDownloadFail", "", "url", "", "error_code", "", "error", "(Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)V", "onDownloadFinish", "file", "Ljava/io/File;", "onDownloadInProgress", "percent", "onDownloadStart", "count", "", "onFileExists", "Commonlib_debug"})
    public static abstract class DownloadObserver {
        private java.lang.Object any;
        
        public final void onFileExists(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.io.File file) {
        }
        
        public abstract void onDownloadFinish(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.NotNull()
        java.io.File file);
        
        public void onDownloadStart(@org.jetbrains.annotations.NotNull()
        java.lang.String url, long count) {
        }
        
        public void onDownloadInProgress(@org.jetbrains.annotations.NotNull()
        java.lang.String url, int percent) {
        }
        
        public void onDownloadFail(@org.jetbrains.annotations.NotNull()
        java.lang.String url, @org.jetbrains.annotations.Nullable()
        java.lang.Integer error_code, @org.jetbrains.annotations.Nullable()
        java.lang.String error) {
        }
        
        public DownloadObserver() {
            super();
        }
        
        public DownloadObserver(@org.jetbrains.annotations.NotNull()
        java.lang.Object any) {
            super();
        }
    }
}