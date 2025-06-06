package com.qm.base.core.exception;

import com.qm.base.core.auth.enums.AuthError;
import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.core.utils.StringUtils;

public class AssertReturn<E extends QmException> {
    E exception;

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
