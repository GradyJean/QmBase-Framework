package com.qm.base.shared.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    /**
     * 权限名称
     *
     * @return 权限名称
     */
    String name();

    /**
     * 权限动作
     *
     * @return 权限动作
     */
    String action();

    /**
     * 资源类型
     *
     * @return 资源类型
     */
    String resourceType() default "";
}
