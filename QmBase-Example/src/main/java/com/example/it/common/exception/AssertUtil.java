package com.example.it.common.exception;

import com.qm.base.core.exception.AbstractAssert;

public class AssertUtil extends AbstractAssert<BusinessException, BusinessError> {
    public static final AssertUtil INSTANCE = new AssertUtil();

    @Override
    public BusinessException createException(BusinessError error) {
        return new BusinessException(error);
    }

    private AssertUtil() {

    }
}
