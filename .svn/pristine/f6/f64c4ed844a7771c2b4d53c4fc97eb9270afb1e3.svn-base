package com.wanmi.sbc.dbreplay.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangjin on 2017/4/28.
 */
public class DateUtil {

    public static final String FMT_DATE_1 = "yyyy-MM-dd";

    public static final String FMT_TIME_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String FMT_TIME_2 = "yyyy-MM-dd HH:mm";

    public static final String FMT_TIME_3 = "yyyyMMddHHmmss";

    public static final String FMT_DATE_3 = "MMddHH";

    public static final String FMT_TIME_4 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FMT_TIME_5 = "yyyyMMdd";

    public static final String FMT_TIME_6 = "yyyy-MM-dd HH";

    /**
     * 转换类型 string to LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime parseDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_TIME_2);
        return LocalDateTime.of(LocalDate.parse(time, formatter), LocalTime.MIN);
    }

    /**
     * 转换类型 string to LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static String getDate(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_DATE_1);
        return formatter.format(time);
    }

    /**
     * 转换类型 string to LocalDateTime
     * 2017-06-23 -> 2017-06-23 00:00
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime parseDay(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_DATE_1);
        return LocalDate.parse(time, formatter).atStartOfDay();
    }


    /**
     * 获取全部当前时间
     * @return
     */
    public static String nowTime(){
        return format(LocalDateTime.now(), FMT_TIME_1);
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String nowDate(){
        return format(LocalDateTime.now(),FMT_DATE_1);
    }

    /**
     * 获取当前时间 到小时
     * @return
     */
    public static String nowHourTime(){
        return format(LocalDateTime.now(),FMT_TIME_6);
    }

    /**
     * 获取昨天时间
     * @return
     */
    public static String yesterdayDate(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);

        return format(cal.getTime(),FMT_DATE_1);
    }

    /**
     * 获取明天时间
     * @return
     */
    public static String tomorrowDate(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,1);

        return format(cal.getTime(),FMT_DATE_1);
    }


    /**
     * 转换类型  LocalDateTime to string
     *
     * @param time time
     * @return LocalDateTime
     */
    public static String format(LocalDateTime time, String fmt) {
        return time.format(DateTimeFormatter.ofPattern(fmt));
    }

    /**
     * 转换类型  Date to string
     *
     * @param time time
     * @return LocalDateTime
     */
    public static String format(Date time, String fmt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
        return simpleDateFormat.format(time);
    }

    /**
     * 转换类型  string to LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime parse(String time, String fmt) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(fmt));
    }


    /**
     * 转换类型  string to LocalDateTime
     *
     * @param time time
     * @return LocalDateTime
     */
    public static LocalDateTime parseZone(String time, String fmt) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(fmt));
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * 时间类型转换为LocalDateTime, 针对*request中带有canEmpty注解的时间属性
     * 如果没有canEmpty注解, 需要在外层判断为空则不调用该方法
     * 使用时需要位于copyProperties上方
     * 场景: 修改信息,
     *   有值:
     *     1.前端如果修改值, 传递到后端则为yyyy-MM-dd类型, 长度为10
     *     2.如果不做修改则为yyyy-MM-dd HH:mm:ss.SSS类型
     *   无值:
     *     保存为null
     * @param time 时间
     * @return
     */
    public static LocalDateTime parseDayCanEmpty(String time) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(FMT_DATE_1);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(FMT_TIME_4);
        if(StringUtils.isNotBlank(time)) {
            if(time.length() == 10) {
                return LocalDateTime.of(LocalDate.parse(time, formatter1), LocalTime.MIN);
            } else {
                return LocalDateTime.parse(time, formatter2);
            }
        }
        return null;
    }

    public static boolean isValidFormat(String value,String format) {
        boolean ok = true;
        DateFormat df = new SimpleDateFormat(format);
        try {
            df.parse(value);
        } catch (ParseException e) {
            ok = false;
        }
        return ok;
    }
}
