package com.qm.base.shared.security.context;

/**
 * Holds the authenticated user's context for the current thread.
 * This is useful for retrieving user information without passing parameters explicitly.
 */
public class SecurityUserContextHolder {
    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    public static void clear() {
        USER_ID_HOLDER.remove();
    }
}
