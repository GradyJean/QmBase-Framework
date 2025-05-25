package com.qm.base.shared.base.exception;

/**
 * 自定义业务异常，用于在领域层或应用层显式抛出受控错误。
 * 支持统一错误码和提示信息处理，便于前后端协议规范一致。
 */
public class BizException extends RuntimeException {

    /**
     * 自定义业务错误码，建议对应 ResultCode 或 ErrorCode 中定义的常量
     */
    private final String code;

    /**
     * 使用系统定义的错误码枚举抛出业务异常
     *
     * @param errorCode 统一错误码枚举
     */
    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.name();
    }

    /**
     * 使用自定义错误码和提示信息抛出业务异常
     *
     * @param code    自定义错误码
     * @param message 错误提示信息
     */
    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}