package com.qm.base.core.security.constants;

public enum Action {
    READ("1"),
    CREATE("2"),
    UPDATE("4"),
    DELETE("8"),
    QUERY(null);
    private final String mask;

    Action(String mask) {
        this.mask = mask;
    }

    public String getMask() {
        return mask;
    }
}
