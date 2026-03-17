package com.qm.base.shared.security.context;

import lombok.Data;

import java.util.Map;

@Data
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
}
