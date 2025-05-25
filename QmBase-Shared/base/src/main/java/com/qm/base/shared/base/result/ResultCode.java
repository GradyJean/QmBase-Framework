package com.qm.base.shared.base.result;

/**
 * 通用结果码常量类，适用于统一的 Result<T> 响应结构。
 * 分为成功、客户端错误、服务端错误等类别。
 */
public final class ResultCode {

    private ResultCode() {
        // 工具类禁止实例化
    }

    // ========== 成功 ==========
    public static final String SUCCESS = "0";
    public static final String SUCCESS_MSG = "Success";

    // ========== 通用失败 ==========
    public static final String FAIL = "1";
    public static final String FAIL_MSG = "Failure";

    // ========== 参数错误 ==========
    public static final String PARAM_ERROR = "1000";
    public static final String PARAM_ERROR_MSG = "Parameter error";

    public static final String PARAM_MISSING = "1001";
    public static final String PARAM_MISSING_MSG = "Missing required parameter";

    public static final String PARAM_INVALID = "1002";
    public static final String PARAM_INVALID_MSG = "Invalid parameter format";

    public static final String PARAM_TYPE_MISMATCH = "1003";
    public static final String PARAM_TYPE_MISMATCH_MSG = "Parameter type mismatch";

    // ========== 认证与鉴权 ==========
    public static final String UNAUTHORIZED = "2001";
    public static final String UNAUTHORIZED_MSG = "User not authenticated";

    public static final String TOKEN_EXPIRED = "2002";
    public static final String TOKEN_EXPIRED_MSG = "Login token expired";

    public static final String FORBIDDEN = "2003";
    public static final String FORBIDDEN_MSG = "Access denied";

    // ========== 资源错误 ==========
    public static final String NOT_FOUND = "3001";
    public static final String NOT_FOUND_MSG = "Resource not found";

    public static final String DUPLICATE_REQUEST = "3002";
    public static final String DUPLICATE_REQUEST_MSG = "Duplicate request or data exists";

    // ========== 业务错误 ==========
    public static final String BUSINESS_RULE_VIOLATION = "4001";
    public static final String BUSINESS_RULE_VIOLATION_MSG = "Business rule violated";

    public static final String OPERATION_NOT_ALLOWED = "4002";
    public static final String OPERATION_NOT_ALLOWED_MSG = "Operation not allowed";

    public static final String ORDER_ALREADY_PAID = "4003";
    public static final String ORDER_ALREADY_PAID_MSG = "Order already paid";

    // ========== 系统错误 ==========
    public static final String SYSTEM_ERROR = "5000";
    public static final String SYSTEM_ERROR_MSG = "Internal server error";

    public static final String SERVICE_UNAVAILABLE = "5001";
    public static final String SERVICE_UNAVAILABLE_MSG = "Service temporarily unavailable";

    public static final String DEPENDENCY_FAILURE = "5002";
    public static final String DEPENDENCY_FAILURE_MSG = "Dependent service failure";

    public static final String UNKNOWN_ERROR = "5999";
    public static final String UNKNOWN_ERROR_MSG = "Unknown system error";

}