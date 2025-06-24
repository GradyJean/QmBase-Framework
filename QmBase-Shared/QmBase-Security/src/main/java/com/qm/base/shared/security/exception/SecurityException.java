package com.qm.base.shared.security.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.exception.QmException;

public class SecurityException extends QmException {
    public SecurityException(ICode iCode) {
        super(iCode);
    }
}
