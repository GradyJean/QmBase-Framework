package com.qm.base.auth.service.auth.impl;

import com.qm.base.auth.context.AuthContextHolder;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.auth.manager.CredentialManager;
import com.qm.base.auth.model.request.CredentialRequest;
import com.qm.base.auth.model.request.LoginRequest;
import com.qm.base.auth.model.request.RegisterRequest;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.auth.AuthService;
import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.enums.TokenType;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.auth.model.Payload;
import com.qm.base.core.utils.RegexUtils;
import com.qm.base.crypto.PasswordUtils;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthServiceImpl implements AuthService {
    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final CredentialManager credentialManager;

    public AuthServiceImpl(CredentialManager credentialManager) {
        this.credentialManager = credentialManager;
    }

    @Override
    public AuthToken login(LoginRequest request) {
        // 设备 ID
        String deviceId = AuthAssert.INSTANCE.notBlank(AuthContextHolder.getContext().getDeviceId(), AuthError.AUTH_DEVICE_ID_EMPTY);
        AuthUser authUser = null;
        AuthAssert.INSTANCE.notNullObject(request, AuthError.AUTH_REQUEST_ERROR);
        // 获取用户标识
        String identifier = request.getIdentifier();
        // 获取用户
        authUser = credentialManager.findByIdentifier(identifier);
        // 获取登录类型
        IdentifierType identifierType = request.getIdentifierType();
        // 是否验证码登录
        if (request.isVerifyCodeLogin()) {
            // 获取验证码
            String verifyCode = AuthAssert.INSTANCE.notBlank(request.getVerifyCode(), AuthError.AUTH_VERIFY_CODE_EMPTY);
            // 校验验证码
            AuthAssert.INSTANCE.isTrue(credentialManager.verifyCode(identifier, verifyCode, identifierType), AuthError.AUTH_VERIFY_CODE_ERROR);
            // 如果第一次验证码登录记录不存在则直接注册
            if (Objects.isNull(authUser)) {
                authUser = credentialManager.createUser(AuthUser.of(identifier, null, identifierType));
            }
        } else {
            // 非验证码登录
            // 账号不存在
            AuthAssert.INSTANCE.notNullObject(authUser, AuthError.AUTH_ACCOUNT_OR_PASSWORD_ERROR);
            // 获取密码
            String credential = AuthAssert.INSTANCE.notBlank(request.getCredential(), AuthError.AUTH_CREDENTIAL_EMPTY);
            // 密码未设置
            String authUserCredential = AuthAssert.INSTANCE.notBlank(authUser.getCredential(), AuthError.AUTH_CREDENTIAL_NOT_SET);
            // 密码对比
            AuthAssert.INSTANCE.isTrue(PasswordUtils.matches(credential, authUserCredential), AuthError.AUTH_ACCOUNT_OR_PASSWORD_ERROR);
        }
        // 账号被禁用
        AuthAssert.INSTANCE.isTrue(authUser.isEnabled(), AuthError.AUTH_ACCOUNT_DISABLED);
        return credentialManager.generateAuthToken(authUser.getUserId(), deviceId);
    }


    /**
     * 普通注册（非第三方注册）
     *
     * @param request 注册请求参数
     * @return 注册结果
     */
    @Override
    public Boolean register(RegisterRequest request) {
        // 获取账号类型
        IdentifierType identifierType = request.getIdentifierType();
        // 获取用户标识
        String identifier = request.getIdentifier();
        // 密码不能为空且必须满足密码格式要求
        String credential = AuthAssert.INSTANCE.notBlank(request.getCredential(), AuthError.AUTH_CREDENTIAL_EMPTY);
        AuthAssert.INSTANCE.isTrue(RegexUtils.isPassword(credential), AuthError.AUTH_CREDENTIAL_INVALID);
        // 用户是否已存在
        AuthAssert.INSTANCE.notTrue(identifierExists(identifier), AuthError.AUTH_ACCOUNT_EXIST);
        // 获取验证码
        String verifyCode = AuthAssert.INSTANCE.notBlank(request.getVerifyCode(), AuthError.AUTH_VERIFY_CODE_EMPTY);
        // 校验验证码
        AuthAssert.INSTANCE.isTrue(credentialManager.verifyCode(identifier, verifyCode, identifierType), AuthError.AUTH_VERIFY_CODE_ERROR);
        // 创建用户
        try {
            return credentialManager.createUser(AuthUser.of(identifier, credential, identifierType)) != null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AuthException(AuthError.AUTH_ERROR);
        }
    }

    @Override
    public Boolean resetCredential(CredentialRequest request) {
        AuthAssert.INSTANCE.notNullObject(request, AuthError.AUTH_REQUEST_ERROR);
        // 账号类型
        IdentifierType identifierType = request.getIdentifierType();
        // 用户标识为空
        String identifier = request.getIdentifier();
        // 新密码为空
        String newCredential = AuthAssert.INSTANCE.notBlank(request.getCredential(), AuthError.AUTH_CREDENTIAL_EMPTY);
        // 校验密码格式
        AuthAssert.INSTANCE.isTrue(RegexUtils.isPassword(newCredential), AuthError.AUTH_CREDENTIAL_INVALID);
        // 验证码为空
        String verifyCode = AuthAssert.INSTANCE.notBlank(request.getVerifyCode(), AuthError.AUTH_VERIFY_CODE_EMPTY);
        // 邮箱或手机未注册
        AuthUser authUser = AuthAssert.INSTANCE.notNull(credentialManager.findByIdentifier(identifier), AuthError.AUTH_EMAIL_OR_PHONE_NOT_EXIST);
        // 验证码校验
        AuthAssert.INSTANCE.isTrue(credentialManager.verifyCode(identifier, verifyCode, identifierType), AuthError.AUTH_VERIFY_CODE_ERROR);
        // 更新密码
        return credentialManager.updateCredential(authUser.getUserId(), PasswordUtils.encode(newCredential));
    }

    @Override
    public AuthToken tokenRefresh(String refreshToken) {
        Payload refreshPayload = parseToken(refreshToken);
        // 判断是否为 refreshToken
        AuthAssert.INSTANCE.isTrue(refreshPayload.getType() == TokenType.REFRESH, AuthError.AUTH_TOKEN_INVALID);
        // 查询 AuthToken
        AuthToken authToken = AuthAssert.INSTANCE.notNull(
                credentialManager.findAuthTokenByUserId(refreshPayload.getUserId(), refreshPayload.getDeviceId()),
                AuthError.AUTH_UNAUTHORIZED);
        // refreshToken 匹配
        AuthAssert.INSTANCE.isTrue(authToken.getRefreshToken().getToken().equals(refreshToken), AuthError.AUTH_TOKEN_INVALID);
        return credentialManager.tokenRefresh(refreshPayload);
    }

    /**
     * token 解析
     *
     * @param token token 字符串
     * @return Payload
     */
    private Payload parseToken(String token) {
        token = AuthAssert.INSTANCE.notBlank(token, AuthError.AUTH_TOKEN_EMPTY);
        Payload payload;
        try {
            payload = credentialManager.parseToken(token);
        } catch (JwtException e) {
            logger.error(e.getMessage(), e);
            throw new AuthException(AuthError.AUTH_TOKEN_INVALID);
        }
        AuthAssert.INSTANCE.notNull(payload, AuthError.AUTH_TOKEN_INVALID);
        return payload;
    }

    @Override
    public boolean identifierExists(String identifier) {
        identifier = AuthAssert.INSTANCE.notBlank(identifier, AuthError.AUTH_ACCOUNT_EMPTY);
        return credentialManager.findByIdentifier(identifier) != null;
    }

    @Override
    public boolean sendVerifyCode(String identifier, IdentifierType identifierType) {
        return credentialManager.generateVerifyCode(identifier, identifierType);
    }


    @Override
    public Boolean logout(String accessToken) {
        Payload payload = parseToken(accessToken);
        // 判断是否为 accessToken
        AuthAssert.INSTANCE.isTrue(payload.getType() == TokenType.ACCESS, AuthError.AUTH_TOKEN_INVALID);
        // 失效 Token
        credentialManager.revokeToken(payload.getUserId(), payload.getDeviceId());
        return true;
    }
}
