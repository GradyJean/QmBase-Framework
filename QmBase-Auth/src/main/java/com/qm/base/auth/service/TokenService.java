package com.qm.base.auth.service;

import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;

public interface TokenService {

    /**
     * 生成 JWT Token
     *
     * @param payload JWT 载荷
     * @return 认证 Token
     */
    AuthToken generateToken(JwtPayload payload);

    /**
     * 解析 JWT Token
     *
     * @param token 令牌字符串
     * @return 解析后的 JWT 载荷
     */
    JwtPayload parseToken(String token);

    /**
     * 刷新 Token
     *
     * @param refreshToken 刷新令牌
     * @return 新的认证 Token
     */
    AuthToken refreshToken(String refreshToken);
}
