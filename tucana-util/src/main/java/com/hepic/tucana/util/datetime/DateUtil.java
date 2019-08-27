package com.hepic.tucana.util.datetime;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tucana
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
public class DateUtil {

    /**
     * 日期格式
     **/
    public static final String DATE_PATTERN_HH_MM = "HH:mm";
    public static final String DATE_PATTERN_HH_MM_SS = "HH:mm:ss";
    public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS_1 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN_MM_DD_HH_MM_CN = "MM月dd日 HH:mm";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_CN = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_PATTERN_YYYY_MM_DD_1 = "yyyyMMdd";
    public static final String DATE_PATTERN_YYYY_MM = "yyyyMM";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH_MM_1 = "yyyyMMddHHmm";
    public static final String DATE_PATTERN_YYYY_MM_DD_HH = "yyyyMMddHH";
    public static final String DATE_PATTERN_YYYY_MM_DD_CMD = "yyyy年MM月dd日";

    /**
     * 日期格式通用转换
     **/
    private static String[] timeFormatList = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss",
            "yyyy年MM月dd日 HH:mm:ss", "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm", "yyyy年MM月dd日 HH:mm",
            "yyyyMMddHHmm", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy年MM月dd日", "yyyyMMdd"};

    public static String date2String(Date date) {
        return date2String(date, DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    public static String date2String(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date string2Date(String dateString) {
        return string2Date(dateString, DATE_PATTERN_YYYY_MM_DD_HH_MM_SS);
    }

    public static Date string2Date(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateString, pos);
    }

    public static Date getMonthStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date getDayStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDayEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date addYear(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, amount);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    public static Date addDay(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }

    public static Date addHour(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, amount);
        return cal.getTime();
    }

    public static Date addMinute(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }

    /**
     * 日期比较
     *
     * @param startDate
     * @param compDate
     * @param endDate
     * @return
     */
    public static boolean compareDate(Date startDate, Date compDate, Date endDate) {
        boolean flag = false;
        if (compare_date(compDate, startDate) >= 0 && compare_date(compDate, endDate) <= 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare_date(Date date1, Date date2) {
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static String getExactDateStr(String date) {
        if (date != null && date.trim() != "") {
            if (date.length() == 8) {
                return date;
            } else {
                return date.replaceAll("-", "");
            }
        }
        return "";
    }

    /**
     * 获取日期是周几
     *
     * @param date 0：周日，1-6：周一至周六
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 转换成时间，字符串格式为 yyyy-MM-dd,yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Date convert2Date(String date, String pattern) {
        String dateString = "";
        if (date.length() == 8) {
            dateString = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        } else {
            dateString = date;
        }

        return string2Date(dateString, pattern);
    }

    /**
     * 获取时间的年部分
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取时间的月部分
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取时间的日部分
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 获取时间的小时部分
     *
     * @param date
     * @return
     */
    public static int getHours(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取时间的小时部分
     *
     * @param date
     * @return
     */
    public static int getMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 获取时间的小时部分
     *
     * @param date
     * @return
     */
    public static int getSecond(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }

    /**
     * 转换json时间
     *
     * @param time json时间字符串（/Date(1507824000000+0800)/）
     * @return
     */
    public static Date parseJsonTime(String time) {
        Date date = null;
        if (time != null && time.trim() != "") {
            date = new Date(Long.parseLong(time.substring(time.indexOf('(') + 1, time.indexOf('+'))));
        }
        return date;
    }

    /**
     * utc时间转换（形如Date(456123456142+0800)）
     *
     * @param utcDate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date convertUtcDate(String utcDate) {
        try {
            if (utcDate.toLowerCase().contains("date")) {
                utcDate = utcDate.substring(utcDate.indexOf("(") + 1, utcDate.lastIndexOf("+"));
            }
            return new Date(Long.valueOf(utcDate));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取两个日期之间的分钟数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getSecondsBetweenTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / 1000;
    }

    /**
     * 转换日期格式(支持N种格式)
     *
     * @param before
     * @param after
     * @return
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat format = null;
        for (String str : timeFormatList) {
            format = new SimpleDateFormat(str);
            try {
                Date date = format.parse(dateString);
                return date;
            } catch (ParseException e) {
                continue;
            }
        }
        return null;
    }
}
