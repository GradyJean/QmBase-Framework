package com.qm.base.core.security.constants;

public enum Action {
    READ(1),
    CREATE(2),
    UPDATE(4),
    DELETE(8),
    QUERY(1);
    private final int mask;

    Action(int mask) {
        this.mask = mask;
    }

    public int getMask() {
        return mask;
    }
}
