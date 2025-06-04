package com.qm.base.auth.service.impl;

import com.qm.base.auth.exception.AuthException;
import com.qm.base.auth.model.constant.AuthErrorCodeEnum;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.auth.model.token.JwtTokenGenerator;
import com.qm.base.auth.model.token.JwtTokenParser;
import com.qm.base.auth.service.TokenService;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenParser jwtTokenParser;

    public TokenServiceImpl(JwtTokenGenerator jwtTokenGenerator, JwtTokenParser jwtTokenParser) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.jwtTokenParser = jwtTokenParser;
    }

    @Override
    public AuthToken generateToken(JwtPayload payload) {
        String accessToken = jwtTokenGenerator.generateAccessToken(payload);
        String refreshToken = jwtTokenGenerator.generateRefreshToken(payload);
        return new AuthToken(accessToken, refreshToken, 0L);
    }

    @Override
    public JwtPayload parseToken(String token) {
        JwtPayload jwtPayload = null;
        try {
            jwtPayload = jwtTokenParser.parse(token);
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_TOKEN_INVALID);
        }
        return jwtPayload;
    }
}
