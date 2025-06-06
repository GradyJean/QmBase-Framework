package com.qm.base.auth.service.auth;

import com.qm.base.auth.model.request.CredentialRequest;
import com.qm.base.auth.model.request.LoginRequest;
import com.qm.base.auth.model.request.RegisterRequest;
import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.model.AuthToken;

/**
 * 用户认证服务接口。
 * 提供登录、注册、刷新 Token 等能力。
 */
public interface AuthService {

    /**
     * 用户登录。
     *
     * @param request 登录请求参数
     * @return 授权 Token 信息
     */
    AuthToken login(LoginRequest request);

    /**
     * 用户注册。
     *
     * @param request 注册请求参数
     * @return 授权 Token 信息
     */
    Boolean register(RegisterRequest request);

    /**
     * 重置密码。
     * 通常会验证验证码或 token，并更新用户密码。
     *
     * @param request 密码重置请求参数
     */
    Boolean resetCredential(CredentialRequest request);

    /**
     * 刷新 Token。
     *
     * @param refreshToken refresh token 字符串
     * @return 新的授权 Token 信息
     */
    AuthToken tokenRefresh(String refreshToken);

    /**
     *
     * @param identifier 用户标识
     * @return 用户标识是否存在
     */
    boolean identifierExists(String identifier);

    /**
     * 发送验证码
     *
     * @param identifier     用户标识
     * @param identifierType 用户类型
     * @return 是否发送成功
     */
    boolean sendVerifyCode(String identifier, IdentifierType identifierType);

    /**
     * 登出
     *
     * @param accessToken 登录的 accessToken
     * @return 是否登出成功
     */
    Boolean logout(String accessToken);
}
