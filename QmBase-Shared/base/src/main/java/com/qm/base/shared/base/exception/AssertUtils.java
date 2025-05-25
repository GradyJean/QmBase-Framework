package com.qm.base.shared.base.exception;

import com.qm.base.shared.base.result.ResultCode;
import org.springframework.util.StringUtils;

/**
 * 通用断言工具类，用于业务参数校验失败时抛出统一的 BizException。
 * 推荐在 service 层或 controller 入参校验时使用。
 */
public class AssertUtils {

    /**
     * 判断对象不为 null
     *
     * @param obj     被校验的对象
     * @param message 异常信息
     */
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BizException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 判断字符串不为空（包含 null、空串、空白字符）
     *
     * @param str     被校验的字符串
     * @param message 异常信息
     */
    public static void notBlank(String str, String message) {
        if (!StringUtils.hasText(str)) {
            throw new BizException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 判断表达式为 true
     *
     * @param condition 条件表达式
     * @param message   异常信息
     */
    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            throw new BizException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 判断两个对象相等（支持 null 值比较）
     *
     * @param a        第一个对象
     * @param b        第二个对象
     * @param message  异常信息
     */
    public static void equals(Object a, Object b, String message) {
        if (a == null || !a.equals(b)) {
            throw new BizException(ResultCode.PARAM_ERROR, message);
        }
    }
}
