package com.qm.base.auth.manager;

import com.qm.base.auth.config.AuthProperties;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.user.AuthUserService;
import com.qm.base.auth.service.verify.VerifyService;
import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.enums.TokenType;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.auth.model.Payload;
import com.qm.base.core.auth.model.Token;
import com.qm.base.core.auth.token.TokenManager;
import com.qm.base.core.auth.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 凭据管理器接口（CredentialManager）
 *
 * <p>该接口用于统一管理与用户认证凭据相关的操作，主要包括用户登录令牌的保存、查询与撤销。
 * 继承自 {@link AuthUserService} 和 {@link VerifyService}，因此也包含了用户身份认证与验证码验证的相关能力。
 * 通常由认证模块的核心服务实现，用于支持如登录、登出、Token刷新、验证码登录等完整的认证流程。
 * </p>
 */
public class CredentialManager {
    Logger logger = LoggerFactory.getLogger(CredentialManager.class);
    private final AuthUserService authUserService;
    private final VerifyService verifyService;
    private final TokenService tokenService;
    private final TokenManager tokenManager;
    private final AuthProperties authProperties;

    public CredentialManager(AuthUserService authUserService,
                             VerifyService verifyService,
                             TokenService tokenService,
                             TokenManager tokenManager,
                             AuthProperties authProperties) {
        this.authUserService = authUserService;
        this.verifyService = verifyService;
        this.tokenService = tokenService;
        this.tokenManager = tokenManager;
        this.authProperties = authProperties;
    }

    /**
     * 生成新的 AuthToken
     *
     * @param userId   用户 ID
     * @param deviceId 设备 ID
     * @return AuthToken
     */
    public AuthToken generateAuthToken(Long userId, String deviceId) {
        Date now = new Date();
        Date accessExpireAt = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(authProperties.getExpirationSeconds()));
        Date refreshExpireAt = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(authProperties.getRefreshIntervalSeconds()));
        String accessTokenStr = generateToken(userId, TokenType.ACCESS, now, accessExpireAt, deviceId);
        String refreshTokenStr = generateToken(userId, TokenType.REFRESH, now, refreshExpireAt, deviceId);
        Token accessToken = new Token(accessTokenStr, accessExpireAt.getTime());
        Token refreshToken = new Token(refreshTokenStr, refreshExpireAt.getTime());
        AuthToken authToken = new AuthToken(accessToken, refreshToken);
        saveAuthToken(userId, deviceId, authToken);
        return authToken;
    }

    /**
     * 获取新的 AuthToken
     *
     * @param refreshPayload refreshToken 的 payload
     * @return AuthToken
     */
    public AuthToken tokenRefresh(Payload refreshPayload) {
        Long userId = refreshPayload.getUserId();
        String deviceId = refreshPayload.getDeviceId();
        // token 过期时间不变
        Date refreshExpireAt = refreshPayload.getExpiresAt();
        Date now = new Date();
        Date accessExpireAt = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(authProperties.getExpirationSeconds()));
        Token accessToken = new Token(
                generateToken(userId, TokenType.ACCESS, now, accessExpireAt, deviceId), accessExpireAt.getTime());
        Token refreshToken = new Token(generateToken(userId, TokenType.REFRESH, now, refreshExpireAt, deviceId)
                , refreshExpireAt.getTime());
        AuthToken authToken = new AuthToken(accessToken, refreshToken);
        saveAuthToken(userId, deviceId, authToken);
        return authToken;
    }

    private String generateToken(Long userId, TokenType tokenType, Date now, Date expiration, String deviceId) {
        return tokenManager.generateToken(userId, tokenType, now, expiration, deviceId);
    }

    public Payload parseToken(String tokenString) {
        return tokenManager.parse(tokenString);
    }

    public AuthUser findByIdentifier(String identifier) {
        try {
            return authUserService.findByIdentifier(identifier);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public AuthUser createUser(AuthUser authUser) {
        try {
            return authUserService.createUser(authUser);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public Boolean updateCredential(Long userId, String newCredential) {
        try {
            return authUserService.updateCredential(userId, newCredential);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public boolean verifyCode(String identifier, String verifyCode, IdentifierType identifierType) {
        try {
            return verifyService.verifyCode(identifier, verifyCode, identifierType);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }


    public boolean generateVerifyCode(String identifier, IdentifierType identifierType) {
        try {
            return verifyService.generateVerifyCode(identifier, identifierType);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public AuthToken findAuthTokenByUserId(Long userId, String deviceId) {
        try {
            return tokenService.findAuthTokenByUserId(userId, deviceId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public void saveAuthToken(Long userId, String deviceId, AuthToken authToken) {
        try {
            tokenService.saveAuthToken(userId, deviceId, authToken);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    public void revokeToken(Long userId, String deviceId) {
        try {
            tokenService.revokeToken(userId, deviceId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }
}
