package com.qm.base.auth.service.impl;

import com.qm.base.auth.exception.AuthException;
import com.qm.base.auth.model.constant.AuthErrorCodeEnum;
import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.auth.model.dto.RegisterRequest;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.AuthService;
import com.qm.base.auth.manager.CredentialManager;
import com.qm.base.auth.service.token.TokenService;
import com.qm.base.core.crypto.PasswordUtils;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.core.model.auth.enums.TokenType;
import com.qm.base.core.user.User;
import com.qm.base.shared.base.utils.RegexUtils;
import com.qm.base.shared.base.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {


    private final TokenService tokenService;
    private final CredentialManager credentialManager;

    public AuthServiceImpl(TokenService tokenService, CredentialManager credentialManager) {
        this.tokenService = tokenService;
        this.credentialManager = credentialManager;
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
        if (request.isVerifyCodeLogin()) {
            if (identifierType == IdentifierType.USER_NAME) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_LOGIN_TYPE_ERROR);
            }
            // 获取验证码
            String verifyCode = request.getVerifyCode();
            if (StringUtils.isBlank(verifyCode)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_EMPTY);
            }
            if (!credentialManager.verifyCode(identifier, verifyCode, identifierType)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_ERROR);
            }
            authUser = credentialManager.findByIdentifier(identifier);
            // 如果第一次验证码登录记录不存在则直接注册
            if (Objects.isNull(authUser)) {
                authUser = credentialManager.createUser(AuthUser.of(identifier, null, identifierType));
            }
        } else {
            // 获取密码
            String credential = request.getCredential();
            if (StringUtils.isBlank(credential)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_CREDENTIAL_EMPTY);
            }
            // 非验证码登录
            authUser = credentialManager.findByIdentifier(identifier);
            if (Objects.isNull(authUser)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_NOT_EXIST);
            }
            // 密码未设置
            if (StringUtils.isBlank(authUser.getCredential())) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_PASSWORD_NOT_SET);
            }
            // 密码对比
            if (!PasswordUtils.matches(credential, authUser.getCredential())) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_LOGIN_FAILED);
            }
        }
        // 账号被禁用
        if (!authUser.isEnabled()) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_DISABLED);
        }

        return generateAuthToken(authUser.getUserId());
    }

    /**
     * 生成 AuthToken
     *
     * @param userId 用户 ID
     * @return AuthToken
     */
    private AuthToken generateAuthToken(Long userId) {
        credentialManager.revokeToken(userId);
        AuthToken authToken = tokenService.generateToken(JwtPayload.ofUser(userId));
        credentialManager.saveToken(userId, authToken);
        return authToken;
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
        if (identifierExists(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ACCOUNT_EXIST);
        }

        // 验证码校验（如果需要）
        if (requiresVerificationCode) {
            String verifyCode = request.getVerifyCode();
            if (StringUtils.isBlank(verifyCode)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_EMPTY);
            }
            if (credentialManager.verifyCode(identifier, verifyCode, identifierType)) {
                throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_ERROR);
            }
        }
        // 创建用户
        try {
            User newUser = credentialManager.createUser(AuthUser.of(identifier, credential, identifierType));
            return tokenService.generateToken(JwtPayload.ofUser(newUser.getUserId()));
        } catch (Exception e) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_ERROR);
        }
    }

    @Override
    public Boolean resetPassword(String identifier, String newCredential, String verificationCode, IdentifierType identifierType) {
        // 用户标识为空
        if (StringUtils.isBlank(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_OR_PHONE_EMPTY);
        }
        // 新密码为空
        if (StringUtils.isBlank(newCredential)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_PASSWORD_EMPTY);
        }
        // 验证码为空
        if (StringUtils.isBlank(verificationCode)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_EMPTY);
        }
        // 验证邮箱或手机号
        validateIdentifier(identifier, identifierType);
        // 邮箱或手机未注册
        AuthUser authUser = credentialManager.findByIdentifier(identifier);
        if (Objects.isNull(authUser)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_OR_PHONE_NOT_EXIST);
        }
        // 验证码校验
        if (!credentialManager.verifyCode(identifier, verificationCode, identifierType)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_VERIFICATION_CODE_ERROR);
        }
        // 更新密码
        return credentialManager.updateCredential(authUser.getUserId(), PasswordUtils.encode(newCredential));
    }

    @Override
    public AuthToken refresh(String refreshToken) {
        // 刷新 token 判空
        if (StringUtils.isBlank(refreshToken)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_EMPTY);
        }
        // 解析 refreshToken
        JwtPayload payload = tokenService.parseToken(refreshToken);
        if (Objects.isNull(payload) || payload.getUserId() == null) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_INVALID);
        }
        // 判断 Token 类型是否匹配
        if (!TokenType.REFRESH.equals(payload.getType())) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_INVALID);
        }
        // 获取用户 ID
        Long userId = payload.getUserId();
        // 查询 AuthToken
        AuthToken authToken = credentialManager.findTokenByUserId(userId);
        if (Objects.isNull(authToken)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_UNAUTHORIZED);
        }
        // 匹配 refreshToken
        if (!authToken.getRefreshToken().equals(refreshToken)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_REFRESH_TOKEN_INVALID);
        }
        return generateAuthToken(userId);
    }

    @Override
    public boolean identifierExists(String identifier) {
        if (StringUtils.isBlank(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_USERNAME_INVALID);
        }
        return credentialManager.findByIdentifier(identifier) != null;
    }

    @Override
    public boolean sendVerifyCode(String identifier, IdentifierType identifierType) {
        // 验证邮箱或手机号
        validateIdentifier(identifier, identifierType);
        return credentialManager.sendVerifyCode(identifier, identifierType);
    }

    /**
     * 验证是否为邮箱或手机号
     *
     * @param identifier     用户标识
     * @param identifierType 标识类型
     */
    private void validateIdentifier(String identifier, IdentifierType identifierType) {
        if (StringUtils.isBlank(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_OR_PHONE_EMPTY);
        }
        if (identifierType != IdentifierType.EMAIL && identifierType != IdentifierType.PHONE_NUMBER) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_OR_PHONE_INVALID);
        }
        if (identifierType == IdentifierType.PHONE_NUMBER && !RegexUtils.isPhone(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_PHONE_INVALID);
        }
        if (identifierType == IdentifierType.EMAIL && !RegexUtils.isEmail(identifier)) {
            throw new AuthException(AuthErrorCodeEnum.AUTH_EMAIL_INVALID);
        }
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
        // 失效 Token
        credentialManager.revokeToken(payload.getUserId());
        return true;
    }
}
