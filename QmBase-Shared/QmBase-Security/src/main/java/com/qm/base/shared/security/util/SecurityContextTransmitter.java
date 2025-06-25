package com.qm.base.shared.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.logger.core.QmLog;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextPayload;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 安全上下文传输工具类。
 * 用于将当前线程中的 SecurityContext 转换为 Base64 编码的 JSON 字符串以便跨服务传输，
 * 或在接收到传入请求头时进行解码并设置回线程上下文中。
 */
public class SecurityContextTransmitter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将当前线程的 SecurityContext 编码为 Base64 字符串。
     * 主要用于 Feign 或 RestTemplate 请求中设置到请求头。
     *
     * @return Base64 编码的上下文字符串（X-Security-Context），为空时返回 null
     */
    public static String encodeContext() {
        var context = SecurityContextHolder.getContext();
        if (context == null) return null;
        try {
            SecurityContextPayload payload = SecurityContextPayload.from(context);
            String json = objectMapper.writeValueAsString(payload);
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            QmLog.error(e.getMessage(), e);
            throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_ERROR);
        }
    }

    /**
     * 从 Base64 字符串解码出 SecurityContextVo，并还原为 SecurityContext 设置到当前线程上下文。
     * 用于网关、服务入口拦截器等场景。
     *
     * @param encoded 请求头中的 Base64 编码安全上下文
     */
    public static void decodeAndSetContext(String encoded) {
        if (StringUtils.isBlank(encoded)) {
            return;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encoded);
            String json = new String(decodedBytes, StandardCharsets.UTF_8);
            SecurityContextPayload payload = objectMapper.readValue(json, SecurityContextPayload.class);
            SecurityContext context = new SecurityContext(payload.getUserId(),
                    payload.getTraceId(),
                    payload.getDeviceId(),
                    payload.getAttributes());
            SecurityContextHolder.setContext(context);
        } catch (JsonProcessingException e) {
            QmLog.error(e.getMessage(), e);
            throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_ERROR);
        }
    }
}