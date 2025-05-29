package com.qm.base.core.exception;

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
    public QmException(String message) {
        super(message);
    }

    public QmException(String message, Throwable cause) {
        super(message, cause);
    }

    public QmException(Throwable cause) {
        super(cause);
    }

    public QmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public QmException() {
    }
}
