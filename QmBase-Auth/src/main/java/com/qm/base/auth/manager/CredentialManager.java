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
import org.springframework.stereotype.Component;

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
@Component
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

    /**
     * 根据标识符查询用户信息。
     *
     * <p>标识符可以是手机号、邮箱等，底层由 {@link AuthUserService} 实现。
     * 若查询过程中发生异常，会统一封装为 {@link AuthException} 抛出。</p>
     *
     * @param identifier 用户登录标识
     * @return 用户信息对象
     * @throws AuthException 查询失败或服务异常
     */
    public AuthUser findByIdentifier(String identifier) {
        try {
            return authUserService.findByIdentifier(identifier);
        } catch (Exception e) {
            logger.error("Failed to call authUserService.findByIdentifier, identifier={}", identifier, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 创建新用户信息。
     *
     * <p>调用下层 AuthUserService 创建用户对象。如果创建过程发生异常（例如数据库错误、参数不合法等），
     * 会统一捕获异常并封装为 AuthException 抛出，避免业务系统内部异常泄露到外部调用方。
     * </p>
     *
     * @param authUser 要创建的用户对象
     * @return 创建成功后的用户对象
     * @throws AuthException 如果创建过程中发生任何异常
     */
    public AuthUser createUser(AuthUser authUser) {
        try {
            return authUserService.createUser(authUser);
        } catch (Exception e) {
            logger.error("Failed to call authUserService.createUser", e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 更新用户的认证凭证。
     *
     * <p>主要用于密码修改或凭证变更，底层调用 {@link AuthUserService} 完成操作。
     * 异常将被统一封装为 {@link AuthException}。</p>
     *
     * @param userId        用户 ID
     * @param newCredential 新凭证（如新密码）
     * @return 是否更新成功
     * @throws AuthException 更新失败或系统异常
     */
    public Boolean updateCredential(Long userId, String newCredential) {
        try {
            return authUserService.updateCredential(userId, newCredential);
        } catch (Exception e) {
            logger.error("Failed to call authUserService.updateCredential, userId:{}", userId, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 验证验证码是否正确。
     *
     * <p>根据标识符（手机号、邮箱）和验证码进行校验，通常用于登录或注册验证。</p>
     *
     * @param identifier     验证目标（如手机号或邮箱）
     * @param verifyCode     验证码
     * @param identifierType 标识符类型
     * @return 验证是否成功
     * @throws AuthException 校验过程异常
     */
    public boolean verifyCode(String identifier, String verifyCode, IdentifierType identifierType) {
        try {
            return verifyService.verifyCode(identifier, verifyCode, identifierType);
        } catch (Exception e) {
            logger.error("Failed to call verifyService.verifyCode, identifier:{}, identifierType:{}", identifier, identifierType, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 生成并发送验证码。
     *
     * <p>根据手机号或邮箱生成验证码，并通过对应通道发送。</p>
     *
     * @param identifier     验证目标（手机号或邮箱）
     * @param identifierType 标识符类型
     * @return 是否生成成功
     * @throws AuthException 生成或发送失败
     */
    public boolean generateVerifyCode(String identifier, IdentifierType identifierType) {
        try {
            return verifyService.generateVerifyCode(identifier, identifierType);
        } catch (Exception e) {
            logger.error("Failed to call verifyService.generateVerifyCode, identifier:{}, identifierType:{}", identifier, identifierType, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 根据用户 ID 和设备 ID 查询缓存中的认证 Token。
     *
     * @param userId   用户 ID
     * @param deviceId 设备 ID
     * @return 认证 Token 对象
     * @throws AuthException 查询失败或缓存服务异常
     */
    public AuthToken findAuthTokenByUserId(Long userId, String deviceId) {
        try {
            return tokenService.findAuthTokenByUserId(userId, deviceId);
        } catch (Exception e) {
            logger.error("Failed to call tokenService.findAuthTokenByUserId, userId:{}, deviceId:{}", userId, deviceId, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 保存认证 Token 到缓存。
     *
     * <p>使用用户 ID 和设备 ID 作为缓存键，保存对应的 AccessToken 与 RefreshToken。</p>
     *
     * @param userId    用户 ID
     * @param deviceId  设备 ID
     * @param authToken Token 对象
     * @throws AuthException 缓存服务失败
     */
    public void saveAuthToken(Long userId, String deviceId, AuthToken authToken) {
        try {
            tokenService.saveAuthToken(userId, deviceId, authToken);
        } catch (Exception e) {
            logger.error("Failed to call tokenService.saveAuthToken, userId:{}, deviceId:{}", userId, deviceId, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    /**
     * 撤销用户的 Token 信息。
     *
     * <p>通常用于用户主动退出登录，清除其缓存中的认证信息。</p>
     *
     * @param userId   用户 ID
     * @param deviceId 设备 ID
     * @throws AuthException 清除失败或缓存异常
     */
    public void revokeToken(Long userId, String deviceId) {
        try {
            tokenService.revokeToken(userId, deviceId);
        } catch (Exception e) {
            logger.error("Failed to call tokenService.revokeToken, userId:{}, deviceId:{}", userId, deviceId, e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }
}
