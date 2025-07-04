package com.example.it.common.constants;

public enum ScopeEnum {
    /**
     * 系统权限域
     */
    SYSTEM("SYSTEM", "系统权限域"),

    /**
     * 业务权限域
     */
    APPLICATION("APPLICATION", "应用权限域"),
    ;

    private final String code;
    private final String description;

    ScopeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }
}
