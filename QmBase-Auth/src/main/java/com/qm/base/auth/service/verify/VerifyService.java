package com.qm.base.auth.service.verify;

import com.qm.base.core.auth.enums.IdentifierType;

/**
 * 验证服务接口，用于处理用户标识的验证码验证与发送。
 * 主要支持手机号、邮箱等标识类型，适用于注册、登录等场景。
 */
public interface VerifyService {
    /**
     * 验证用户标识是否合法、邮箱验证码、短信验证码等
     *
     * @param identifier     用户凭证标识（如手机号、邮箱）
     * @param verifyCode     用户提交的凭证（如明文密码或验证码）
     * @param identifierType 标识类型（如手机号、邮箱地址等）
     * @return 是否验证通过
     */
    boolean verifyCode(String identifier, String verifyCode, IdentifierType identifierType);

    /**
     * 发送验证码
     *
     * @param identifier     用户标识
     * @param identifierType 用户类型
     * @return 是否发送成功
     * <p>
     * 根据标识类型（如邮箱或手机号）发送对应验证码，用于注册或登录验证。
     */
    boolean generateVerifyCode(String identifier, IdentifierType identifierType);
}
