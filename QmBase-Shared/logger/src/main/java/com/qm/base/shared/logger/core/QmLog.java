package com.qm.base.shared.logger.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.lang.StackWalker;

/**
 * 统一日志门面类，用于标准化输出结构化日志。
 */
public class QmLog {

    private static final StackWalker CALLER_WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    /**
     * 打印 info 日志
     *
     * @param module 模块名，如 "订单"
     * @param action 操作名，如 "创建订单"
     * @param args   可变参数，将按顺序打印
     */
    public static void info(String module, String action, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isInfoEnabled()) {
            logger.info("[{}-{}] {}", module, action, formatArgs(args));
        }
    }

    /**
     * 打印 error 日志（带异常）
     *
     * @param module 模块名
     * @param action 操作名
     * @param ex     异常对象
     */
    public static void error(String module, String action, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        logger.error("[{}-{}] 异常: {}", module, action, ex.toString(), ex);
    }

    /**
     * 打印 debug 日志
     *
     * @param module 模块名
     * @param action 操作名
     * @param args   结构化参数
     */
    public static void debug(String module, String action, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isDebugEnabled()) {
            logger.debug("[{}-{}] {}", module, action, formatArgs(args));
        }
    }

    /**
     * 打印 warn 日志
     *
     * @param module 模块名
     * @param action 操作名
     * @param args   结构化参数
     */
    public static void warn(String module, String action, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isWarnEnabled()) {
            logger.warn("[{}-{}] {}", module, action, formatArgs(args));
        }
    }

    /**
     * 打印 info 日志（无模块和操作名）
     *
     * @param message 日志消息模板
     * @param args    参数
     */
    public static void info(String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isInfoEnabled()) {
            logger.info(message, args);
        }
    }

    /**
     * 打印 debug 日志（无模块和操作名）
     *
     * @param message 日志消息模板
     * @param args    参数
     */
    public static void debug(String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isDebugEnabled()) {
            logger.debug(message, args);
        }
    }

    /**
     * 打印 warn 日志（无模块和操作名）
     *
     * @param message 日志消息模板
     * @param args    参数
     */
    public static void warn(String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        if (logger.isWarnEnabled()) {
            logger.warn(message, args);
        }
    }

    /**
     * 打印 error 日志（无模块和操作名）
     *
     * @param message 日志消息模板
     * @param args    参数
     */
    public static void error(String message, Object... args) {
        Logger logger = LoggerFactory.getLogger(getCallerClass());
        logger.error(message, args);
    }

    /**
     * 参数格式化（用 ｜ 分隔）
     */
    private static String formatArgs(Object... args) {
        return Arrays.stream(args)
                .map(arg -> arg == null ? "null" : arg.toString())
                .collect(Collectors.joining(" | "));
    }

    /**
     * 获取调用类（用于 logger 命名）
     */
    private static Class<?> getCallerClass() {
        return CALLER_WALKER.walk(frames -> frames
                .skip(2)
                .findFirst()
                .map(StackWalker.StackFrame::getDeclaringClass)
                .orElse(null));
    }
}
