package com.example.it.common.exception;

import com.qm.base.core.exception.QmException;

public class BusinessException extends QmException {
    public BusinessException(BusinessError iCode) {
        super(iCode);
    }
}
