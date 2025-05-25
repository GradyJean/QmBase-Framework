package com.qm.base.shared.base;

import com.qm.base.shared.base.exception.BizException;
import com.qm.base.shared.base.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BizExceptionTest {

    @Test
    void testConstructWithErrorCode() {
        BizException ex = new BizException(ErrorCode.INVALID_TOKEN);
        assertEquals(ErrorCode.INVALID_TOKEN.name(), ex.getCode());
        assertEquals(ErrorCode.INVALID_TOKEN.getMessage(), ex.getMessage());
    }

    @Test
    void testConstructWithCustomCodeAndMessage() {
        BizException ex = new BizException("9999", "自定义错误信息");
        assertEquals("9999", ex.getCode());
        assertEquals("自定义错误信息", ex.getMessage());
    }
}