package com.qm.base.auth.service.impl;

import com.qm.base.auth.exception.AuthException;
import com.qm.base.auth.model.constant.AuthErrorCodeEnum;
import com.qm.base.auth.model.token.JwtTokenManager;
import com.qm.base.auth.service.TokenService;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.shared.base.utils.StringUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final JwtTokenManager jwtTokenManager;

    public TokenServiceImpl(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }


    @Override
    public AuthToken generateToken(JwtPayload payload) {
        if (payload == null || payload.getUserId() == null) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ERROR);
        }
        return jwtTokenManager.generateAuthToken(payload);
    }

    @Override
    public JwtPayload parseToken(String token) {
        JwtPayload jwtPayload = null;
        try {
            jwtPayload = jwtTokenManager.parse(token);
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_TOKEN_INVALID);
        }
        return jwtPayload;
    }
}
