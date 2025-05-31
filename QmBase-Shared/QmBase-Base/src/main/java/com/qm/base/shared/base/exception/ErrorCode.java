package com.qm.base.shared.base.exception;

/**
 * 系统通用错误码枚举类。
 * <p>
 * 每个错误码代表一个明确的业务错误语义，适用于 {@code Result<T>} 和异常体系。
 * 建议在 {@code BizException} 或控制器中统一使用，用于前后端错误对齐。
 * 可与 {@code ResultCode} 并存，前者语义更强，后者适合与状态码组合。
 * </p>
 */
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

    /**
     * 错误提示信息
     * -- GETTER --
     * 获取错误提示文本。
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

    /**
     * 获取错误码对应的提示信息。
     *
     * @return 错误提示文本
     */
    public String getMessage() {
        return message;
    }
}