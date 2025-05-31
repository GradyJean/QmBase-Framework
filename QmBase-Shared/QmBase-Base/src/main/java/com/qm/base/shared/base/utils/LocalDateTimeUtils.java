package com.qm.base.shared.base.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * LocalDateTime 工具类，提供常用的日期时间操作封装。
 */
public class LocalDateTimeUtils {

    /**
     * 获取今天开始时间（00:00:00）
     *
     * @return 当天起始时间
     */
    public static LocalDateTime getStartOfToday() {
        return LocalDate.now().atStartOfDay();
    }

    /**
     * 获取今天结束时间（23:59:59.999999999）
     *
     * @return 当天结束时间
     */
    public static LocalDateTime getEndOfToday() {
        return LocalDate.now().atTime(LocalTime.MAX);
    }

    /**
     * 获取指定时间向前/向后偏移天数后的时间
     *
     * @param dateTime 原始时间
     * @param days     正数表示向后，负数表示向前
     * @return 偏移后的时间
     */
    public static LocalDateTime offsetDays(LocalDateTime dateTime, int days) {
        return dateTime.plusDays(days);
    }
}
