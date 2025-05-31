package com.qm.base.auth.service.impl;

import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.auth.model.dto.RegisterRequest;
import com.qm.base.auth.service.AuthService;
import com.qm.base.auth.service.TokenService;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    private final TokenService tokenService;

    public AuthServiceImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public AuthToken login(LoginRequest request) {
        // 模拟将 openId 映射为 userId（实际应通过数据库或远程服务）
        String openId = request.getCredential();
        if (openId == null || openId.isEmpty()) {
            throw new IllegalArgumentException("OpenId must not be empty");
        }

        // 示例映射：生成一个 mock userId
        Long userId = 10000L + openId.hashCode();

        // 构造 payload
        JwtPayload payload = new JwtPayload();
        payload.setUserId(userId);

        // 调用 tokenService 生成 token
        return tokenService.generateToken(payload);
    }

    @Override
    public AuthToken register(RegisterRequest request) {
        // TODO: Replace with actual registration logic
        return new AuthToken("registeredAccessToken", "registeredRefreshToken", 3600L);
    }

    @Override
    public void resetPassword(String identifier, String newPassword, String credential) {

    }

    @Override
    public AuthToken refresh(String refreshToken) {
        // TODO: Replace with actual refresh logic
        return new AuthToken("newAccessToken", refreshToken, 3600L);
    }
}
