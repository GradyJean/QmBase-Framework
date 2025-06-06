package com.qm.base.core.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.utils.StringUtils;

public abstract class AbstractAssert<EX extends QmException, CODE extends ICode> {
    public abstract EX createException(CODE iCode);

    /**
     * 断言字符串不为空（非 null 且非空白字符），否则抛出认证异常。
     *
     * @param str  被检查的字符串
     * @param code 异常错误枚举
     * @return 原始字符串
     */
    public String notBlank(String str, CODE code) {
        if (StringUtils.isBlank(str)) {
            throw createException(code);
        }
        return str;
    }

    /**
     * 断言对象不为 null，否则抛出认证异常。
     *
     * @param obj  被检查的对象
     * @param code 异常错误枚举
     * @param <T>  对象类型
     * @return 原始对象
     */
    public <T> T notNull(T obj, CODE code) {
        if (obj == null) {
            throw createException(code);
        }
        return obj;
    }

    /**
     * 断言对象不为 null，否则抛出认证异常。不返回值。
     *
     * @param obj  被检查的对象
     * @param code 异常错误枚举
     */
    public void notNullObject(Object obj, CODE code) {
        if (obj == null) {
            throw createException(code);
        }
    }

    /**
     * 断言条件为 true，否则抛出认证异常。
     *
     * @param condition 条件判断
     * @param code      异常错误枚举
     */
    public void isTrue(boolean condition, CODE code) {
        if (!condition) {
            throw createException(code);
        }
    }

    /**
     * 断言条件为 false，否则抛出认证异常。
     *
     * @param condition 条件判断
     * @param code      异常错误枚举
     */
    public void notTrue(boolean condition, CODE code) {
        if (condition) {
            throw createException(code);
        }
    }
}
