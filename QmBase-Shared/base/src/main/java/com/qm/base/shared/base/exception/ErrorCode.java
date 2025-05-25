package com.qm.base.shared.base.exception;

import lombok.Getter;

/**
 * 系统通用错误码枚举类。
 * <p>
 * 每个错误码对应一个明确的错误语义，便于业务异常统一处理。
 * 全局通用错误码集合，供各模块统一使用。
 */
@Getter
public enum ErrorCode {
    /**
     * 用户不存在
     * <p>
     * 当请求的用户在系统中不存在时返回该错误码。
     */
    USER_NOT_FOUND("User not found"),

    /**
     * 认证 token 无效或过期
     * <p>
     * 当用户提供的身份认证 token 无效或已过期时返回该错误码。
     */
    INVALID_TOKEN("Invalid authentication token"),

    /**
     * 无访问权限
     * <p>
     * 当用户尝试访问无权限的资源时返回该错误码。
     */
    ACCESS_DENIED("Access denied"),

    /**
     * 系统内部错误
     * <p>
     * 当系统发生未捕获异常或未知错误时返回该错误码。
     */
    SYSTEM_ERROR("Internal server error");

    /** 错误提示信息
     * -- GETTER --
     *  获取错误提示文本。
     *
     */
    private final String message;

    /**
     * 构造方法。
     *
     * @param message 错误提示文本
     */
    ErrorCode(String message) {
        this.message = message;
    }

}