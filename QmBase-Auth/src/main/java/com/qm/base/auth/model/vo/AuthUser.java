package com.qm.base.auth.model.vo;

import com.qm.base.core.crypto.PasswordUtils;
import com.qm.base.core.model.auth.dto.AbstractAuth;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.core.user.User;
import com.qm.base.shared.base.utils.StringUtils;

/**
 * AuthUser 是认证完成后的用户视图对象，用于封装登录信息和用户状态。
 */
public class AuthUser extends AbstractAuth implements User {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否匿名访问（true 表示未登录用户）
     */
    private boolean anonymous;

    /**
     * 用户是否启用（false 表示被禁用或封禁）
     */
    private boolean enabled;

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public boolean isAnonymous() {
        return this.anonymous;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 构造一个 AuthUser 对象，并自动加密密码字段。
     *
     * @param identifier     用户名、邮箱或手机号等唯一标识
     * @param credential     明文密码，将会在此处被加密
     * @param identifierType 标识类型（如 USERNAME、EMAIL、MOBILE 等）
     * @return 构造完成的 AuthUser 对象
     */
    public static AuthUser of(String identifier, String credential, IdentifierType identifierType) {
        AuthUser authUser = new AuthUser();
        authUser.setIdentifier(identifier);
        authUser.setCredential(StringUtils.isBlank(credential) ? null : PasswordUtils.encode(credential)); // 明文密码加密
        authUser.setIdentifierType(identifierType);
        authUser.setEnabled(true);
        return authUser;
    }
}
