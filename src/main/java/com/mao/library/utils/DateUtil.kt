package com.mao.library.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by maozonghong
 * on 2019/12/17
 */

object DateUtil {
    /**
     * M月d日 HH:mm
     */
    val sdf1: SimpleDateFormat = SimpleDateFormat("M月d日 HH:mm")
    /**
     * yyyy年MM月dd日
     */
    val sdf2: SimpleDateFormat = SimpleDateFormat("yyyy年MM月dd日")
    /**
     * yyyy-MM-dd
     */
    val sdf3: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    /**
     * HH:mm
     */
    val sdf4: SimpleDateFormat = SimpleDateFormat("HH:mm")
    /**
     * yyyy年MM月dd日 HH:mm:ss
     */
    val sdf5: SimpleDateFormat = SimpleDateFormat("yyyy年MM月dd日HH:mm:ss")
    /**
     * M月d日
     */
    val sdf7: SimpleDateFormat = SimpleDateFormat("M月d日")
    /**
     * MM-dd HH:mm
     */
    val sdf8: SimpleDateFormat = SimpleDateFormat("MM-dd HH:mm")
    /**
     * MM-dd
     */
    val sdf9: SimpleDateFormat = SimpleDateFormat("MM-dd")

    /**
     * yyyy年MM月dd日 E HH:mm
     */
    val sdf10: SimpleDateFormat = SimpleDateFormat("yyyy年MM月dd日 E HH:mm")

    val sdf11:SimpleDateFormat=SimpleDateFormat("MM月dd日 E")

    val SECOND_MILLIS = 1000
    val MINUTE_MILLIS = SECOND_MILLIS * 60
    val HOUR_MILLIS = MINUTE_MILLIS * 60
    val DAY_MILLIS = HOUR_MILLIS * 24

    var constellationArr = arrayOf(
        "魔羯座",
        "水瓶座",
        "双鱼座",
        "白羊座",
        "金牛座",
        "双子座",
        "巨蟹座",
        "狮子座",
        "处女座",
        "天秤座",
        "天蝎座",
        "射手座")
    private val constellationEdgeDay =
        intArrayOf(20, 18, 20, 20, 20, 21, 22, 22, 22, 22, 21, 21)

    // public static final int WEEK_MILLIS = DAY_MILLIS * 7;

    // public static final int WEEK_MILLIS = DAY_MILLIS * 7;
    fun getConstellation(month: Int, day: Int): Int {
        var month = month
        if (day <= constellationEdgeDay[month - 1]) {
            month -= 1
        }
        if (month < 0 || month >= constellationArr.size) {
            month = 0
        }
        return month
    }

    fun getChatTimeDetail(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        val now: Calendar = Calendar.getInstance()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        if (now.get(Calendar.YEAR) !== calendar.get(Calendar.YEAR)) {
            return sdf5.format(time)
        }
        val days = getDaysBetween(now, calendar)
        return when (days) {
            0 -> sdf4.format(time)
            1 -> "昨天 " + sdf4.format(time)
            2 -> "前天 " + sdf4.format(time)
            else -> sdf1.format(time)
        }
    }

    fun getDaysBetween(now: Calendar, old: Calendar): Int {
        val temp_now: Calendar = Calendar.getInstance()
        temp_now.clear()
        temp_now.set(Calendar.YEAR, now.get(Calendar.YEAR))
        temp_now.set(Calendar.MONTH, now.get(Calendar.MONTH))
        temp_now.set(Calendar.DATE, now.get(Calendar.DATE))
        val temp_old: Calendar = Calendar.getInstance()
        temp_old.clear()
        temp_old.set(Calendar.YEAR, old.get(Calendar.YEAR))
        temp_old.set(Calendar.MONTH, old.get(Calendar.MONTH))
        temp_old.set(Calendar.DATE, old.get(Calendar.DATE))
        return (((temp_now.time.time - temp_old.time.time) / DAY_MILLIS).toInt())
    }


    fun getCreateTime(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        var gap = System.currentTimeMillis() - time
        gap /= 1000
        if (gap <= 60) {
            return "刚刚"
        } else {
            gap /= 60
            if (gap <= 60) {
                return gap.toString() + "分钟前"
            } else {
                gap /= 60
                if (gap < 24) {
                    return gap.toString() + "小时前"
                }
            }
        }
        val now: Calendar = Calendar.getInstance()
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(time)
        // if (now.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
// return "今天 " + sdf4.format(time);
// } else {
        now.add(Calendar.DATE, -1)
        return if (now.get(Calendar.DATE) === calendar.get(Calendar.DATE)) {
            "昨天 " + sdf4.format(time)
        } else {
            sdf7.format(time)
        }
        // }
    }

    fun getOnLine(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        var gap = System.currentTimeMillis() - time
        gap /= 1000
        return if (gap <= 60) {
            "刚刚"
        } else {
            gap /= 60
            if (gap <= 60) {
                gap.toString() + "分钟前"
            } else {
                gap /= 60
                if (gap < 24) {
                    gap.toString() + "小时前"
                } else {
                    gap = gap / 24
                    if (gap > 30) {
                        gap = 30
                    }
                    gap.toString() + "天前"
                }
            }
        }
    }

    fun getAge(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        return try {
            val d: String = sdf3.format(time)
            val date = Date()
            val mydate: Date = sdf3.parse(d)
            val day: Long = (date.time - mydate.time) / (24 * 60 * 60 * 1000) + 1
            DecimalFormat("#").format(day / 365f.toDouble())
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun getBirTime(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        return sdf2.format(time)
    }

    fun getDate(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        return sdf3.format(time)
    }

    fun getPublishDate(time: Long): String? {
        val calender: Calendar = Calendar.getInstance()
        calender.timeInMillis = time
        val year: Int = calender.get(Calendar.YEAR)
        return if (year == Calendar.getInstance().get(Calendar.YEAR)) {
            sdf1.format(time)
        } else {
            sdf5.format(time)
        }
    }

    fun getTimeAndWeek(time:Long):String{
        return sdf11.format(time)
    }

    fun getDateAndTime(time: Long): String? {
        return sdf5.format(time)
    }

    fun getYearMonthAndWeek(time: Long): String? {
        return sdf10.format(time)
    }

    fun getDate2(time: Long): String? {
        var time = time
        if (time < 10000000000L) {
            time *= 1000
        }
        return sdf9.format(time)
    }

    fun getWaitingTime(time: Long): String? {
        var time = time
        if (time < MINUTE_MILLIS) {
            return (time / SECOND_MILLIS).toString() + "秒"
        }
        val builder = StringBuilder()
        if (time >= DAY_MILLIS) {
            builder.append(time / DAY_MILLIS)
            builder.append("天")
            time %= DAY_MILLIS
        }
        if (time >= HOUR_MILLIS) {
            builder.append(time / HOUR_MILLIS)
            builder.append("时")
            time %= HOUR_MILLIS
        }
        if (time >= MINUTE_MILLIS) {
            builder.append(time / MINUTE_MILLIS)
            builder.append("分")
            time %= MINUTE_MILLIS
        }
        return builder.toString()
    }

    fun parseDotNetDateJson(time: String): Long {
        var time = time
        time = time.replace("[^0-9]".toRegex(), "")
        return time.toLong()
    }
}