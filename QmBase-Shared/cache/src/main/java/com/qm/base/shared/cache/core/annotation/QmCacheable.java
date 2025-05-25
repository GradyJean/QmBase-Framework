package com.qm.base.shared.cache.core.annotation;

import java.lang.annotation.*;

/**
 * QmCacheable - 自定义缓存注解。
 * 支持缓存 key 控制、ttl、是否缓存 null、条件缓存控制等。
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QmCacheable {

    /**
     * 缓存名称（命名空间），对应 QmCacheManager.getCache(name)
     */
    String name();

    /**
     * 缓存 key（支持 SpEL 表达式），如 "#id + '_' + #type"
     */
    String key();

    /**
     * 缓存过期时间（单位秒），0 表示使用默认 ttl
     */
    int ttl() default 0;

    /**
     * 是否缓存 null 值，默认为 false
     */
    boolean cacheNull() default false;

    /**
     * 条件表达式（SpEL），只有为 true 时才执行缓存逻辑
     */
    String condition() default "";

    /**
     * 反向条件表达式（SpEL），为 true 时跳过缓存写入
     */
    String unless() default "";
}