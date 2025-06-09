package com.qm.base.core.exception;

import com.qm.base.core.code.ICode;

import java.io.Serial;

/**
 * Qm 系统通用基础异常类
 *
 * <p>作为所有自定义异常的统一父类，继承自 {@link RuntimeException}，
 * 用于在不同模块中传递业务异常、封装异常信息。</p>
 *
 * <p>建议业务侧统一继承 {@code QmException} 或其子类（如 BizException），
 * 以确保异常体系的一致性和可控性。</p>
 */
public class QmException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 业务错误码（如 "AUTH_TOKEN_EXPIRED"）
     */
    private final String code;

    /**
     * 错误消息，用于展示给用户或日志记录（如 "Token has expired"）
     */
    private final String message;

    /**
     * HTTP 状态码数值（如 401、500 等），供 Web 层响应使用
     */
    private final int status;

    public QmException(ICode iCode) {
        super(iCode.getMessage());
        this.code = iCode.getCode();
        this.message = iCode.getMessage();
        this.status = iCode.getStatus().getCode();
    }

    /**
     * 返回错误码字符串，通常用于标识业务错误类型。
     *
     * @return 业务错误码（如 "AUTH_TOKEN_EXPIRED"）
     */
    public String getCode() {
        return code;
    }

    /**
     * 返回异常的描述信息，通常用于展示给用户或日志记录。
     *
     * @return 错误消息（如 "Token has expired"）
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 返回建议的 HTTP 状态码数值，供 Web 层响应时使用。
     *
     * @return HTTP 状态码（如 401、500 等）
     */
    public int getStatus() {
        return status;
    }
}
