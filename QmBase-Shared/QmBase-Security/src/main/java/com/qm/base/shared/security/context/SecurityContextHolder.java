package com.qm.base.shared.security.context;

import com.qm.base.context.AbstractContextHolder;
import com.qm.base.shared.security.model.vo.SecurityContext;

public class SecurityContextHolder extends AbstractContextHolder<SecurityContext> {
    private static final SecurityContextHolder INSTANCE = new SecurityContextHolder();

    public static void setContext(SecurityContext context) {
        INSTANCE.set(context);
    }

    public static SecurityContext getContext() {
        return INSTANCE.get();
    }

    public static void clearContext() {
        INSTANCE.clear();
    }
    public static Long getUserId() {

    }
}
