package com.booxj.tools.core.data;

import com.booxj.tools.core.lang.Assert;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间工具类
 */
public class DateTimeUtil {


    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //------------------------------------------------------------------------ of

    public static LocalDateTime of(long timestamp) {
        return of(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }

    public static LocalDateTime of(Date date) {
        Assert.notNull(date);
        return of(date.toInstant(), TimeZone.getDefault().toZoneId());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant {@link Instant}
     * @param zoneId  时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, ZoneId zoneId) {
        if (null == instant) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, zoneId != null ? zoneId : ZoneId.systemDefault());
    }

    public static LocalDateTime of(int year, int month, int day) {
        return LocalDateTime.of(year, month, day, 0, 0, 0);
    }

    public static LocalDateTime of(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }


    public static LocalDateTime parse(String timeStr) {
        Assert.notBlank(timeStr);
        return LocalDateTime.parse(timeStr, DateFormatter.YYYY_MM_DD_HH_MM_SS.getFormatter());
    }

    public static LocalDateTime parse(String timeStr, DateFormatter formatter) {
        Assert.notBlank(timeStr);
        Assert.notNull(formatter);
        return LocalDateTime.parse(timeStr, formatter.getFormatter());
    }

    public static String format(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime);
        return localDateTime.format(DateFormatter.YYYY_MM_DD_HH_MM_SS.getFormatter());
    }

    public static String format(LocalDateTime localDateTime, DateFormatter formatter) {
        Assert.notHasNull(localDateTime, formatter);
        return localDateTime.format(formatter.getFormatter());
    }

    public static boolean isSameDay(LocalDateTime dt1, LocalDateTime dt2) {
        return (dt1.getYear() == dt2.getYear() &&
                dt1.getMonth() == dt2.getMonth() &&
                dt1.getDayOfMonth() == dt2.getDayOfMonth());
    }

    public static Duration between(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }

    /**
     * 修改为一天的开始时间，例如：2020-02-02 00:00:00,000
     *
     * @param time 日期时间
     * @return 一天的开始时间
     */
    public static LocalDateTime beginOfDay(LocalDateTime time) {
        return time.with(LocalTime.of(0, 0, 0, 0));
    }

    /**
     * 修改为一天的结束时间，例如：2020-02-02 23:59:59,999
     *
     * @param time 日期时间
     * @return 一天的结束时间
     */
    public static LocalDateTime endOfDay(LocalDateTime time) {
        return time.with(LocalTime.of(23, 59, 59, 999_999_999));
    }

    public static LocalDateTime firstDayOfMonth(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime lastDayOfMonth(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDateTime firstDayOfNextMonth(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfNextMonth());
    }

    public static LocalDateTime firstDayOfYear(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfYear());
    }

    public static LocalDateTime lastDayOfYear(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.lastDayOfYear());
    }

    public static LocalDateTime firstDayOfNextYear(LocalDateTime localDateTime) {
        return localDateTime.with(TemporalAdjusters.firstDayOfNextYear());
    }

}
