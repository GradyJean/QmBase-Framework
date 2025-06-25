package com.qm.base.shared.security.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.exception.QmException;

/**
 * 安全模块异常类。
 * <p>
 * 用于在安全相关操作中抛出自定义异常，继承自 {@link QmException}。
 * 支持通过 {@link ICode} 传递错误码和错误信息，便于统一异常处理。
 * </p>
 *
 * @author GradyJean
 * @since 1.0
 */
public class SecurityException extends QmException {
    /**
     * 构造方法，根据错误码对象创建安全异常。
     *
     * @param iCode 错误码接口，包含错误码和描述信息
     */
    public SecurityException(ICode iCode) {
        super(iCode);
    }
}