package com.qm.base.shared.lock.annotation;

import com.qm.base.shared.lock.enums.LockType;

import java.lang.annotation.*;

/**
 * 通用锁注解：支持本地锁和分布式锁
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    /**
     * 锁 key，支持 Spring EL 表达式，如 "'order:' + #orderId"
     */
    String key();

    /**
     * 锁类型（默认使用本地锁）
     */
    LockType type() default LockType.LOCAL;

    /**
     * 锁超时时间（毫秒）
     */
    long expire() default 5000;

    /**
     * 等待超时时间（毫秒）
     */
    long waitMillis() default 1000;

}