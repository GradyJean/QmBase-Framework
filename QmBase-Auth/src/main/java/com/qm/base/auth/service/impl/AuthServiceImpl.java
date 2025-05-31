package com.qm.base.auth.service.impl;

import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.auth.model.dto.RegisterRequest;
import com.qm.base.auth.service.AuthService;
import com.qm.base.auth.service.TokenService;
import com.qm.base.auth.service.UserService;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.core.user.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {


    private final TokenService tokenService;
    private final UserService userService;

    public AuthServiceImpl(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public AuthToken login(LoginRequest request) {
        String credential = request.getCredential();
        if (credential == null || credential.isEmpty()) {
            throw new IllegalArgumentException("Credential must not be empty");
        }
        User user = userService.findByIdentifier(credential);
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("User not found for credential: " + credential);
        }

        return tokenService.generateToken(JwtPayload.ofUser(user.getUserId()));
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
