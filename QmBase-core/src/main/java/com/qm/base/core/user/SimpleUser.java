package com.qm.base.core.user;

import java.util.Objects;

/**
 * User 接口的简单实现。
 * 可用于上下文注入、JWT 解析后载体等。
 */
public class SimpleUser implements User {

    private Long userId;

    public SimpleUser() {
    }

    public SimpleUser(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleUser)) return false;
        SimpleUser that = (SimpleUser) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "SimpleUser{userId=" + userId + '}';
    }
}
