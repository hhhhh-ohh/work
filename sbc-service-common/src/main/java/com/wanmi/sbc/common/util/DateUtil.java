package com.wanmi.sbc.common.util;

import com.google.common.collect.ImmutableMap;
import com.wanmi.sbc.common.enums.RepeatType;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Created by zhangjin on 2017/4/28.
 */
public class DateUtil {

    public static final String FMT_DATE_1 = "yyyy-MM-dd";

    public static final String FMT_TIME_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String FMT_TIME_2 = "yyyy-MM-dd HH:mm";

    public static final String FMT_TIME_3 = "yyyyMMddHHmmss";

    public static final String FMT_TIME_4 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FMT_TIME_5 = "yyyyMMdd";

    public static final String FMT_TIME_6 = "yyyy-MM-dd HH";

    public static final String FMT_TIME_7 = "yyyyMMddHHmmssSSS";

    public static final String FMT_TIME_8 = "yyyyMMdd HH:mm:ss";

    public static final String FMT_TIME_9 = "yyyyMM";

    public static final String QUARTER = "quarter";

    public static final String MONTH = "month";

    public static final String YEAR = "year";

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
    public static LocalDateTime parseDate(String time,String type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
        return LocalDateTime.parse(time, formatter);

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
     * 转换类型 string to LocalDate
     * 2017-06-23 -> 2017-06-23
     *
     * @param time time
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_DATE_1);
        return LocalDate.parse(time, formatter);
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
     * 转换类型  LocalDate to string
     *
     * @param time
     * @param fmt
     * @return String
     */
    public static String format(LocalDate time, String fmt) {
        return time.format(DateTimeFormatter.ofPattern(fmt));
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

    /**
     * 相差天数
     * @param oldTime
     * @param time
     * @return
     */
    public static Long differentDays(LocalDateTime oldTime,LocalDateTime time) {
        return time.toLocalDate().toEpochDay()-oldTime.toLocalDate().toEpochDay();
    }

    /**
     * 获取某季度的开始日期
     * 季度一年四季， 第一季度：1月-3月， 第二季度：4月-6月， 第三季度：7月-9月， 第四季度：10月-12月
     *
     * @param offset 0本季度，1下个季度，-1上个季度，依次类推
     * @return
     */
    public static LocalDateTime quarterStart(long offset) {
        final LocalDateTime date = LocalDateTime.now().plusMonths(offset * 3L);
        int month = date.getMonth().getValue();
        long start = 0L;
        if (month >= 1 && month <= 3) {
            start = 1L;
        } else if (month >= 4 && month <= 6) {
            start = 4L;
        } else if (month >= 7 && month <= 9) {
            start = 7L;
        } else if ((month >= 10 && month <= 12)) {
            start = 10L;
        }
        return date.plusMonths(start - ((long)month)).with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);
    }

    /**
     * 明天
     * @return
     */
    public static LocalDate tomorrowDay(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FMT_DATE_1);
        return LocalDate.parse(tomorrowDate(), formatter);
    }

    /**
     * 当前周的第一天
     * @return
     */
    public static LocalDate firstDayOfWeek(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FMT_DATE_1);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date date = cal.getTime();
        return LocalDate.parse(simpleDateFormat.format(date));
    }

    /**
     * 下周的第一天
     * @return
     */
    public static LocalDate firstDayOfNextWeek() {
        return LocalDate.now().with(TemporalAdjusters.next( DayOfWeek.MONDAY));
    }

    /**
     * 当月的第一天
     * @return
     */
    public static LocalDate firstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 下个月的第一天
     * @return
     */
    public static LocalDate firstDayOfNextMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
    }

    /**
     * 当年的第一天
     * @return
     */
    public static LocalDate firstDayOfYear() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 明年的第一天
     * @return
     */
    public static LocalDate firstDayOfNextYear() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear());
    }

    /**
     * 根据当前时间转换出Map{month=当前月, quarter=当前季度, year=当前年份}
     * @return
     */
    public static Map<String, Integer> covertToMonthAndQuarterAndYear(LocalDateTime date) {
        int month = date.getMonth().getValue();
        int quarter;
        if (month >= Month.JANUARY.getValue() && month <= Month.MARCH.getValue()) {
            quarter = 1;
        } else if (month >= Month.APRIL.getValue() && month <= Month.JUNE.getValue()) {
            quarter = 2;
        } else if (month >= Month.JULY.getValue() && month <= Month.SEPTEMBER.getValue()) {
            quarter = 3;
        } else {
            quarter = 4;
        }
        return ImmutableMap.of(MONTH,month,QUARTER,quarter,YEAR,date.getYear());
    }

    /**
     * 最近7天
     * @return
     */
    public static String beforeSevenDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, - 7);
        return format(c.getTime(),FMT_DATE_1);
    }
    /**
     * 最近一个月
     * @return
     */
    public static String beforeOneMonthDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        return format(c.getTime(),FMT_DATE_1);
    }
    /**
     * 最近三个月
     * @return
     */
    public static String beforeThreeMonth(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -3);
        return format(c.getTime(),FMT_DATE_1);
    }

    /**
     * 最近一年
     * @return
     */
    public static String afterOneYear(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        return format(c.getTime(),FMT_TIME_4);
    }

    /**
     * 日期加减指定天数
     * @return
     */
    public static String plusDayForStr(String date, int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(FMT_DATE_1);
        Date classDate = format.parse(date);
        Calendar c = Calendar.getInstance();
        c.setTime(classDate);
        c.add(Calendar.DATE, day);
        return format(c.getTime(),FMT_DATE_1);
    }

    /**
     * 比较两段时间是否存在重叠
     * @return
     */
    public static boolean compareTimeIntersection(LocalDateTime startTime,LocalDateTime endTime,
                                                  LocalDateTime compareStartTime,LocalDateTime compareEndTime) {

        Date leftStartDate = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date leftEndDate = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        Date rightStartDate = Date.from(compareStartTime.atZone(ZoneId.systemDefault()).toInstant());
        Date rightEndDate = Date.from(compareEndTime.atZone(ZoneId.systemDefault()).toInstant());

        /*判断*/
        if (((leftStartDate.getTime() >= rightStartDate.getTime())
                && leftStartDate.getTime() < rightEndDate.getTime())
                || ((leftStartDate.getTime() > rightStartDate.getTime())
                && leftStartDate.getTime() <= rightEndDate.getTime())
                || ((rightStartDate.getTime() >= leftStartDate.getTime())
                && rightStartDate.getTime() < leftEndDate.getTime())
                || ((rightStartDate.getTime() > leftStartDate.getTime())
                && rightStartDate.getTime() <= leftEndDate.getTime())){
            return true;
        }
        return false;
    }

    /**
     * 计算本周/本月的开始时间
     * @param time
     * @param type
     * @return
     */
    public static LocalDateTime getCheckTime(LocalDateTime time, RepeatType type) {
        if (RepeatType.WEEK == type) {
            int value = time.getDayOfWeek().getValue() - 1;
            time = time.minusDays(value);
        } else {
            int value = time.getDayOfMonth() - 1;
            time = time.minusDays(value);
        }
        return LocalDateTime.of(time.toLocalDate(), LocalTime.MIN);
    }

    /**
     * Date转LocalDate
     * @return
     */
    public static LocalDate dateToLocalDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FMT_DATE_1);
        return LocalDate.parse(simpleDateFormat.format(date));
    }

    /**
     * Date转LocalDateTime
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取当前日期是本月的第几周（通过本月有几个周三来判断）
     * @param localDateTime
     * @return  第n周
     * @throws Exception
     */
    public static int getWeekByWed(LocalDateTime localDateTime) {
        String dateStr = format(localDateTime, FMT_DATE_1);
        // 设置时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值，变为周一
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        // 加两天变为周三
        calendar.add(Calendar.DATE, 2);

        //得到传入日期所在周的周三
        String wed = sdf.format(calendar.getTime());

        // 本月份的天数
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //判断是周三
        int wek = 3;
        //本月的第几个周三
        int index = 0;
        List<String> list = new ArrayList<String>();
        for (int i = 1; i <= days; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            // 注意,Calendar对象默认星期天为1
            int weekd = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (weekd == wek) {
                String aaa = sdf.format(calendar.getTime());
                list.add(aaa);
                if (wed.equals(aaa)) {
                    index = list.size();
                }
            }
        }
        return index;
    }

    @SneakyThrows
    public static Date parseDate1(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FMT_DATE_1);
        Date date = simpleDateFormat.parse(dateStr);
        return date;
    }


    public static Date convertToLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
