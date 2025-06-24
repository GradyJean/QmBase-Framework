package com.qm.base.shared.security.model.vo;

import java.util.Map;

public class SecurityContextVo {
    private Long userId;
    private String traceId;
    private String deviceId;
    private Map<String, Object> attributes;

    public static SecurityContextVo from(SecurityContext context) {
        SecurityContextVo vo = new SecurityContextVo();
        vo.attributes = context.getPropagatedAttributes();
        vo.userId = context.getUserId();
        vo.traceId = context.getTraceId();
        vo.deviceId = context.getDeviceId();
        return vo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
