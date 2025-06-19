package com.qm.base.auth.context;

import com.qm.base.auth.model.vo.AuthContext;
import com.qm.base.context.AbstractContextHolder;

public class AuthContextHolder extends AbstractContextHolder<AuthContext> {
    private static final AuthContextHolder INSTANCE = new AuthContextHolder();

    public static void setContext(AuthContext context) {
        INSTANCE.set(context);
    }

    public static AuthContext getContext() {
        return INSTANCE.get();
    }

    public static void clearContext() {
        INSTANCE.clear();
    }
}
