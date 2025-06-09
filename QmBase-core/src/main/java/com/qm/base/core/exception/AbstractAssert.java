package com.qm.base.core.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.utils.StringUtils;

/**
 * 抽象断言类，用于封装通用的参数校验逻辑。
 * 支持传入错误码枚举，根据具体实现抛出对应类型的自定义异常。
 *
 * 子类需实现 {@link #createException(ICode)} 方法以构造模块化异常。
 *
 * @param <EX>   异常类型，需继承 QmException
 * @param <CODE> 错误码类型，需实现 ICode 接口
 */
public abstract class AbstractAssert<EX extends QmException, CODE extends ICode> {
    public abstract EX createException(CODE iCode);

    /**
     * 断言字符串不为空（非 null 且非空白字符），否则抛出自定义异常。
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
     * 断言对象不为 null，否则抛出自定义异常。
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
     * 断言对象不为 null，否则抛出自定义异常。不返回值。
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
     * 断言条件为 true，否则抛出自定义异常。
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
     * 断言条件为 false，否则抛出自定义异常。
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
