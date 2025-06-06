package com.qm.base.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间工具类，封装常用时间格式化方法。
 */
public class DateUtils {

    /**
     * 默认时间格式：yyyy-MM-dd HH:mm:ss
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将 LocalDateTime 格式化为默认格式字符串
     *
     * @param dateTime 时间对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    /**
     * 使用自定义格式格式化 LocalDateTime
     *
     * @param dateTime 时间对象
     * @param pattern  格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
