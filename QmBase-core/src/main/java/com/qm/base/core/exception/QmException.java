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
    private final String code;
    private final String message;
    private final int status;

    public QmException(ICode iCode) {
        super(iCode.getMessage());
        this.code = iCode.getCode();
        this.message = iCode.getMessage();
        this.status = iCode.getStatus().getCode();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
