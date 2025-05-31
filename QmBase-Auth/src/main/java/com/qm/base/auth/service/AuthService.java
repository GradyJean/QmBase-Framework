package com.qm.base.auth.service;

import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.auth.model.dto.RegisterRequest;
import com.qm.base.core.model.auth.dto.AuthToken;

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
    AuthToken register(RegisterRequest request);

    /**
     * 重置密码。
     * 通常会验证验证码或 token，并更新用户密码。
     *
     * @param identifier  用户标识（如邮箱、手机号）
     * @param newPassword 新密码
     * @param credential  验证码或 token 等凭证
     */
    void resetPassword(String identifier, String newPassword, String credential);

    /**
     * 刷新 Token。
     *
     * @param refreshToken refresh token 字符串
     * @return 新的授权 Token 信息
     */
    AuthToken refresh(String refreshToken);
}
