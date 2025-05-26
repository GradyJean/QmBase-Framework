package com.qm.base.shared.security.datascope.annotation;

import java.lang.annotation.*;

/**
 * 数据权限控制注解
 * 用于声明当前接口需要基于部门或用户过滤数据
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {

    /**
     * 部门表别名（用于 SQL 拼接）
     */
    String deptAlias() default "";

    /**
     * 用户表别名（用于 SQL 拼接）
     */
    String userAlias() default "";

    /**
     * 是否包含子部门（默认包含）
     */
    boolean includeSubDept() default true;
}