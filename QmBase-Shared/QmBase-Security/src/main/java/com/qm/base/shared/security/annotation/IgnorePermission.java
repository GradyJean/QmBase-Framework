package com.qm.base.shared.security.annotation;

import java.lang.annotation.*;

/**
 * 注解用于标记某个类或方法在权限检查时被忽略。
 * <p>
 * 当应用于类时，表示该类中的所有方法都将被忽略权限检查。
 * 当应用于方法时，仅该方法将被忽略权限检查。
 * </p>
 * <p>
 * 适用于需要公开访问的 API 或不需要权限控制的操作。
 * </p>
 *
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnorePermission {
}