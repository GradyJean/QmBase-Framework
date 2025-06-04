package com.qm.base.auth.service.impl;

import com.qm.base.auth.exception.AuthException;
import com.qm.base.auth.model.constant.AuthErrorCodeEnum;
import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.auth.model.dto.RegisterRequest;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.AuthService;
import com.qm.base.auth.service.AuthUserService;
import com.qm.base.auth.service.TokenService;
import com.qm.base.core.crypto.PasswordUtils;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.core.user.User;
import com.qm.base.shared.base.utils.RegexUtils;
import com.qm.base.shared.base.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {


    private final TokenService tokenService;
    private final AuthUserService authUserService;

    public AuthServiceImpl(TokenService tokenService, AuthUserService authUserService) {
        this.tokenService = tokenService;
        this.authUserService = authUserService;
    }

    @Override
    public AuthToken login(LoginRequest request) {
        AuthUser authUser = null;
        // 获取用户标识
        String identifier = request.getIdentifier();
        if (StringUtils.isBlank(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_CREDENTIAL_EMPTY);
        }
        // 获取登录类型
        IdentifierType identifierType = request.getIdentifierType();
        if (Objects.isNull(identifierType)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_IDENTIFIER_TYPE_EMPTY);
        }
        // 是否验证码登录
        if (request.isUseVerificationCode()) {
            if (identifierType == IdentifierType.USER_NAME) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_LOGIN_TYPE_ERROR);
            }
            // 获取验证码
            String verificationCode = request.getVerificationCode();
            if (StringUtils.isBlank(verificationCode)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_EMPTY);
            }
            if (authUserService.verifyIdentifierCode(identifier, verificationCode, identifierType)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_ERROR);
            }
            authUser = authUserService.findByIdentifier(identifier, identifierType);
            // 如果第一次验证码登录记录不存在则直接注册
            if (Objects.isNull(authUser)) {
                authUser = authUserService.createUser(AuthUser.of(identifier, null, identifierType));
            }
        }
        // 获取密码
        String credential = request.getCredential();
        if (StringUtils.isBlank(credential)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_CREDENTIAL_EMPTY);
        }
        // 非验证码登录
        authUser = authUserService.findByIdentifier(identifier, identifierType);
        if (Objects.isNull(authUser)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_NOT_EXIST);
        }
        // 账号被禁用
        if (!authUser.isEnabled()) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_DISABLED);
        }
        // 密码未设置
        if (StringUtils.isBlank(authUser.getCredential())) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_PASSWORD_NOT_SET);
        }
        // 密码对比
        if (!PasswordUtils.matches(credential, authUser.getCredential())) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_LOGIN_FAILED);
        }
        return tokenService.generateToken(JwtPayload.ofUser(authUser.getUserId()));
    }

    /**
     * 普通注册（非第三方注册）
     *
     * @param request 注册请求参数
     * @return AuthToken
     */
    @Override
    public AuthToken register(RegisterRequest request) {
        IdentifierType identifierType = request.getIdentifierType();
        if (Objects.isNull(identifierType)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_IDENTIFIER_TYPE_EMPTY);
        }

        // 是否需要校验验证码（手机号或邮箱）
        boolean requiresVerificationCode = false;
        String identifier = request.getIdentifier();

        // 按类型校验用户名、手机号或邮箱
        switch (identifierType) {
            case USER_NAME:
                if (StringUtils.isBlank(identifier) || !RegexUtils.isUsername(identifier)) {
                    throw new AuthException(AuthErrorCodeEnum.AUTH_USERNAME_INVALID);
                }
                break;
            case PHONE_NUMBER:
                if (StringUtils.isBlank(identifier) || !RegexUtils.isPhone(identifier)) {
                    throw new AuthException(AuthErrorCodeEnum.AUTH_PHONE_INVALID);
                }
                requiresVerificationCode = true;
                break;
            case EMAIL:
                if (StringUtils.isBlank(identifier) || !RegexUtils.isEmail(identifier)) {
                    throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_INVALID);
                }
                requiresVerificationCode = true;
                break;
            default:
                throw new AuthException(AuthErrorCodeEnum.AUTH_IDENTIFIER_TYPE_INVALID);
        }

        // 密码不能为空且必须满足密码格式要求
        String credential = request.getCredential();
        if (StringUtils.isBlank(credential)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_CREDENTIAL_EMPTY);
        }
        if (!RegexUtils.isPassword(credential)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_PASSWORD_INVALID);
        }

        // 用户是否已存在
        if (identifierExists(identifier, identifierType)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_EXIST);
        }

        // 验证码校验（如果需要）
        if (requiresVerificationCode) {
            String code = request.getVerificationCode();
            if (StringUtils.isBlank(code)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_EMPTY);
            }
            if (authUserService.verifyIdentifierCode(identifier, code, identifierType)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_ERROR);
            }
        }
        // 创建用户
        try {
            User newUser = authUserService.createUser(AuthUser.of(identifier, credential, identifierType));
            return tokenService.generateToken(JwtPayload.ofUser(newUser.getUserId()));
        } catch (Exception e) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ERROR);
        }
    }

    @Override
    public void resetPassword(String identifier, String newPassword, String credential) {

    }

    @Override
    public AuthToken refresh(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_EMPTY);
        }
        JwtPayload payload = tokenService.parseToken(refreshToken);
        if (Objects.isNull(payload) || payload.getUserId() == null) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_INVALID);
        }
        AuthUser authUser = authUserService.findByUserId(payload.getUserId());
        if (Objects.isNull(authUser) || !authUser.isEnabled()) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_NOT_EXIST);
        }
        return tokenService.generateToken(JwtPayload.ofUser(authUser.getUserId()));
    }

    @Override
    public boolean identifierExists(String identifier, IdentifierType identifierType) {
        return authUserService.findByIdentifier(identifier, identifierType) != null;
    }

    @Override
    public Boolean logout(String accessToken) {
        if (StringUtils.isBlank(accessToken)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_EMPTY);
        }
        JwtPayload payload = tokenService.parseToken(accessToken);
        if (Objects.isNull(payload) || payload.getUserId() == null) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_INVALID);
        }
        authUserService.logoutHandler(payload.getUserId());
        return true;
    }
}
