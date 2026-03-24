package com.qm.base.shared.security.annotation;


import com.qm.base.core.security.constants.Action;

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
    Action action();

    /**
     * 需要校验的资源类型
     *
     * @return 资源类型
     */
    String vResourceType() default "";

    /**
     * 是否需有资源注册
     *
     * @return boolean
     */
    boolean hasResource() default false;
}
