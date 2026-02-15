package com.qm.base.shared.security.context;

import java.util.Map;

public class SecurityContextPayload {
    private String userId;
    private String deviceId;
    private Map<String, Object> attributes;

    public static SecurityContextPayload from(SecurityContext context) {
        SecurityContextPayload vo = new SecurityContextPayload();
        vo.attributes = context.getPropagatedAttributes();
        vo.userId = context.getUserId();

        vo.deviceId = context.getDeviceId();
        return vo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
