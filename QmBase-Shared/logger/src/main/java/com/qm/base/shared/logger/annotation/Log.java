package com.qm.base.shared.logger.annotation;

import com.qm.base.shared.logger.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 日志注解：用于打印方法调用的入参、出参、耗时
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 模块名（例如：订单、用户）
     */
    String module() default "";

    /**
     * 操作说明（例如：新增、删除）
     */
    String action() default "";

    /**
     * 日志级别
     */
    LogLevel level() default LogLevel.INFO;

    /**
     * 是否打印请求参数
     */
    boolean logParams() default true;

    /**
     * 是否打印返回结果
     */
    boolean logResult() default true;
}