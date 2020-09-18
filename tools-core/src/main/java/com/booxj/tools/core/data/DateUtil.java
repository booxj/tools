package com.booxj.tools.core.data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author xiaoleilu
 */
public class DateUtil extends CalendarUtil {

    public static Date now() {
        return new Date();
    }

    public static Date date(long date) {
        return new Date(date);
    }

    public static Date date(Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format {@link SimpleDateFormat}
     * @return 格式化后的字符串
     */
    public static String format(Date date, SimpleDateFormat format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date);
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateStr 特定格式的日期
     * @param format  格式，例如 yyyy-MM-dd
     * @return 日期对象
     * @throws ParseException
     */
    public static Date parse(String dateStr, SimpleDateFormat format) throws ParseException {
        return format.parse(dateStr);
    }

    /**
     * 修改日期为某个时间字段起始时间
     *
     * @param date      {@link Date}
     * @param dateField 时间字段
     * @return {@link Date}
     */
    public static Date truncate(Date date, DateField dateField) {
        return truncate(calendar(date), dateField).getTime();
    }

    /**
     * 修改日期为某个时间字段结束时间
     *
     * @param date      {@link Date}
     * @param dateField 时间字段
     * @return {@link Date}
     */
    public static Date ceiling(Date date, DateField dateField) {
        return ceiling(calendar(date), dateField).getTime();
    }

    /**
     * 获取某天的开始时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date beginOfDay(Date date) {
        return beginOfDay(calendar(date)).getTime();
    }

    /**
     * 获取某天的结束时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date endOfDay(Date date) {
        return endOfDay(calendar(date)).getTime();
    }

    /**
     * 获取某周的开始时间，周一定为一周的开始时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date beginOfWeek(Date date) {
        return beginOfWeek(calendar(date)).getTime();
    }

    /**
     * 获取某周的开始时间
     *
     * @param date               日期
     * @param isMondayAsFirstDay 是否周一做为一周的第一天（false表示周日做为第一天）
     * @return {@link Date}
     */
    public static Date beginOfWeek(Date date, boolean isMondayAsFirstDay) {
        return beginOfWeek(calendar(date), isMondayAsFirstDay).getTime();
    }

    /**
     * 获取某周的结束时间，周日定为一周的结束
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date endOfWeek(Date date) {
        return endOfWeek(calendar(date)).getTime();
    }

    /**
     * 获取某周的结束时间
     *
     * @param date              日期
     * @param isSundayAsLastDay 是否周日做为一周的最后一天（false表示周六做为最后一天）
     * @return {@link Date}
     */
    public static Date endOfWeek(Date date, boolean isSundayAsLastDay) {
        return endOfWeek(calendar(date), isSundayAsLastDay).getTime();
    }

    /**
     * 获取某月的开始时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date beginOfMonth(Date date) {
        return beginOfMonth(calendar(date)).getTime();
    }

    /**
     * 获取某月的结束时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date endOfMonth(Date date) {
        return endOfMonth(calendar(date)).getTime();
    }

    /**
     * 获取某年的开始时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date beginOfYear(Date date) {
        return beginOfYear(calendar(date)).getTime();
    }

    /**
     * 获取某年的结束时间
     *
     * @param date 日期
     * @return {@link Date}
     */
    public static Date endOfYear(Date date) {
        return endOfYear(calendar(date)).getTime();
    }


    /**
     * 比较两个日期是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一天
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return CalendarUtil.isSameDay(calendar(date1), calendar(date2));
    }

    /**
     * 比较两个日期是否为同一月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一月
     */
    public static boolean isSameMonth(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return CalendarUtil.isSameMonth(calendar(date1), calendar(date2));
    }


}
