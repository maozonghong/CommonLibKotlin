package com.mao.library.utils;

import java.lang.System;

/**
 * Created by maozonghong
 * on 2019/12/17
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010-\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u00100\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u00101\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0016\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00020\u00042\u0006\u00104\u001a\u00020\u0004J\u0010\u00105\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u00106\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u00107\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u00108\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0016\u00109\u001a\u00020\u00042\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;J\u0010\u0010=\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u0010>\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u000e\u0010?\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u0010@\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u0010\u0010A\u001a\u0004\u0018\u00010\u000f2\u0006\u0010.\u001a\u00020/J\u000e\u0010B\u001a\u00020/2\u0006\u0010.\u001a\u00020\u000fR\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\"\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0014\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0011\u0010\u001d\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0011\u0010\u001f\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001aR\u0011\u0010!\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001aR\u0011\u0010#\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0011\u0010%\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001aR\u0011\u0010\'\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001aR\u0011\u0010)\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001aR\u0011\u0010+\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001a\u00a8\u0006C"}, d2 = {"Lcom/mao/library/utils/DateUtil;", "", "()V", "DAY_MILLIS", "", "getDAY_MILLIS", "()I", "HOUR_MILLIS", "getHOUR_MILLIS", "MINUTE_MILLIS", "getMINUTE_MILLIS", "SECOND_MILLIS", "getSECOND_MILLIS", "constellationArr", "", "", "getConstellationArr", "()[Ljava/lang/String;", "setConstellationArr", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "constellationEdgeDay", "", "sdf1", "Ljava/text/SimpleDateFormat;", "getSdf1", "()Ljava/text/SimpleDateFormat;", "sdf10", "getSdf10", "sdf11", "getSdf11", "sdf2", "getSdf2", "sdf3", "getSdf3", "sdf4", "getSdf4", "sdf5", "getSdf5", "sdf7", "getSdf7", "sdf8", "getSdf8", "sdf9", "getSdf9", "getAge", "time", "", "getBirTime", "getChatTimeDetail", "getConstellation", "month", "day", "getCreateTime", "getDate", "getDate2", "getDateAndTime", "getDaysBetween", "now", "Ljava/util/Calendar;", "old", "getOnLine", "getPublishDate", "getTimeAndWeek", "getWaitingTime", "getYearMonthAndWeek", "parseDotNetDateJson", "Commonlib_debug"})
public final class DateUtil {
    
    /**
     * M月d日 HH:mm
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf1 = null;
    
    /**
     * yyyy年MM月dd日
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf2 = null;
    
    /**
     * yyyy-MM-dd
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf3 = null;
    
    /**
     * HH:mm
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf4 = null;
    
    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf5 = null;
    
    /**
     * M月d日
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf7 = null;
    
    /**
     * MM-dd HH:mm
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf8 = null;
    
    /**
     * MM-dd
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf9 = null;
    
    /**
     * yyyy年MM月dd日 E HH:mm
     */
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf10 = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat sdf11 = null;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60000;
    private static final int HOUR_MILLIS = 3600000;
    private static final int DAY_MILLIS = 86400000;
    @org.jetbrains.annotations.NotNull()
    private static java.lang.String[] constellationArr;
    private static final int[] constellationEdgeDay = null;
    public static final com.mao.library.utils.DateUtil INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.text.SimpleDateFormat getSdf11() {
        return null;
    }
    
    public final int getSECOND_MILLIS() {
        return 0;
    }
    
    public final int getMINUTE_MILLIS() {
        return 0;
    }
    
    public final int getHOUR_MILLIS() {
        return 0;
    }
    
    public final int getDAY_MILLIS() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getConstellationArr() {
        return null;
    }
    
    public final void setConstellationArr(@org.jetbrains.annotations.NotNull()
    java.lang.String[] p0) {
    }
    
    public final int getConstellation(int month, int day) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getChatTimeDetail(long time) {
        return null;
    }
    
    public final int getDaysBetween(@org.jetbrains.annotations.NotNull()
    java.util.Calendar now, @org.jetbrains.annotations.NotNull()
    java.util.Calendar old) {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCreateTime(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOnLine(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAge(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBirTime(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDate(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPublishDate(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeAndWeek(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDateAndTime(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getYearMonthAndWeek(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDate2(long time) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getWaitingTime(long time) {
        return null;
    }
    
    public final long parseDotNetDateJson(@org.jetbrains.annotations.NotNull()
    java.lang.String time) {
        return 0L;
    }
    
    private DateUtil() {
        super();
    }
}