package com.qm.base.core.auth.model;

import java.util.Map;

public class PlatformInfo {
    private String platform;
    private Map<String, String> info;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
