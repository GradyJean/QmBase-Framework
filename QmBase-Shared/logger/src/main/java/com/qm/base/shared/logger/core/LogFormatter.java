package com.qm.base.shared.logger.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 日志格式化工具类，用于将方法参数、返回值等转换为结构化字符串表示。
 */
public class LogFormatter {

    /**
     * 格式化方法参数列表。
     *
     * @param args 方法参数数组
     * @return 字符串形式的参数信息
     */
    public static String formatArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        return Arrays.stream(args)
                .map(LogFormatter::safeToString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * 格式化返回值。
     *
     * @param result 方法返回值
     * @return 字符串形式的返回信息
     */
    public static String formatResult(Object result) {
        return safeToString(result);
    }

    /**
     * 格式化异常信息。
     *
     * @param throwable 异常对象
     * @return 简化后的异常类名与信息
     */
    public static String formatThrowable(Throwable throwable) {
        if (throwable == null) return "";
        return throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
    }

    /**
     * 安全转换对象为字符串，避免空指针。
     */
    private static String safeToString(Object obj) {
        if (obj == null) return "null";
        if (obj.getClass().isArray()) {
            return Arrays.deepToString(new Object[]{obj});
        }
        return Objects.toString(obj);
    }
}
