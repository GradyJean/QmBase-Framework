package com.qm.base.core.auth.exception;


import com.qm.base.core.auth.enums.AuthError;
import com.qm.base.core.utils.StringUtils;

/**
 * 提供认证相关的断言方法。
 * 断言失败时会抛出自定义 AuthException 异常，统一错误码结构。
 * 成功时返回原值，便于链式调用和简化业务逻辑。
 */
public class AuthAssertReturn {
    private AuthAssertReturn() {
    }

    /**
     * 断言字符串不为空（非 null 且非空白字符），否则抛出认证异常。
     *
     * @param str       被检查的字符串
     * @param authError 异常错误枚举
     * @return 原始字符串
     */
    public static String notBlank(String str, AuthError authError) {
        if (StringUtils.isBlank(str)) {
            throw new AuthException(authError);
        }
        return str;
    }

    /**
     * 断言对象不为 null，否则抛出认证异常。
     *
     * @param obj       被检查的对象
     * @param authError 异常错误枚举
     * @param <T>       对象类型
     * @return 原始对象
     */
    public static <T> T notNull(T obj, AuthError authError) {
        if (obj == null) {
            throw new AuthException(authError);
        }
        return obj;
    }

    /**
     * 断言对象不为 null，否则抛出认证异常。不返回值。
     *
     * @param obj       被检查的对象
     * @param authError 异常错误枚举
     */
    public static void notNullObject(Object obj, AuthError authError) {
        if (obj == null) {
            throw new AuthException(authError);
        }
    }

    /**
     * 断言条件为 true，否则抛出认证异常。
     *
     * @param condition 条件判断
     * @param authError 异常错误枚举
     */
    public static void isTrue(boolean condition, AuthError authError) {
        if (!condition) {
            throw new AuthException(authError);
        }
    }

    /**
     * 断言条件为 false，否则抛出认证异常。
     *
     * @param condition 条件判断
     * @param authError 异常错误枚举
     */
    public static void notTrue(boolean condition, AuthError authError) {
        if (condition) {
            throw new AuthException(authError);
        }
    }
}
