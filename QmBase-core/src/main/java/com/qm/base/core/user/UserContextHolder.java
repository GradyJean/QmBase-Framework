package com.qm.base.core.user;

/**
 * 用户上下文持有器。
 * 通过 ThreadLocal 保存当前线程用户信息，便于跨组件访问。
 */
public class UserContextHolder {

    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前用户。
     *
     * @param user 当前用户对象
     */
    public static void set(User user) {
        USER_HOLDER.set(user);
    }

    /**
     * 获取当前用户。
     *
     * @return 当前线程用户对象，可能为 null
     */
    public static User get() {
        return USER_HOLDER.get();
    }

    /**
     * 清理当前线程中的用户信息。
     * 建议在请求结束后调用，防止线程复用导致数据泄露。
     */
    public static void clear() {
        USER_HOLDER.remove();
    }
}
