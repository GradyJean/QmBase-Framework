package com.qm.base.shared.base;

import com.qm.base.shared.base.exception.ErrorCode;
import com.qm.base.shared.base.result.Result;
import com.qm.base.shared.base.result.ResultCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testSuccessWithoutData() {
        Result<String> result = Result.SUCCESS();
        assertTrue(result.isSuccess());
        assertEquals(ResultCode.SUCCESS, result.getCode());
        assertEquals(ResultCode.SUCCESS_MSG, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testSuccessWithData() {
        String data = "hello";
        Result<String> result = Result.SUCCESS(data);
        assertTrue(result.isSuccess());
        assertEquals(ResultCode.SUCCESS, result.getCode());
        assertEquals(ResultCode.SUCCESS_MSG, result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    void testFailWithDefaultMessage() {
        Result<String> result = Result.FAIL();
        assertFalse(result.isSuccess());
        assertEquals(ResultCode.FAIL, result.getCode());
        assertEquals(ResultCode.FAIL_MSG, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testFailWithCustomMessage() {
        Result<String> result = Result.FAIL("400", "Bad request");
        assertFalse(result.isSuccess());
        assertEquals("400", result.getCode());
        assertEquals("Bad request", result.getMessage());
    }

    @Test
    void testFailWithErrorCodeEnum() {
        Result<String> result = Result.FAIL(ErrorCode.ACCESS_DENIED);
        assertFalse(result.isSuccess());
        assertEquals(ErrorCode.ACCESS_DENIED.name(), result.getCode());
        assertEquals(ErrorCode.ACCESS_DENIED.getMessage(), result.getMessage());
    }
}