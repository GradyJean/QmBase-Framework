package com.qm.base.auth.manager;


import com.qm.base.auth.service.user.AuthUserService;
import com.qm.base.auth.service.verify.VerificationService;
import com.qm.base.core.model.auth.dto.AuthToken;

/**
 * 凭据管理器
 */
public interface CredentialManager extends AuthUserService, VerificationService {

    AuthToken findTokenByUserId(Long userId);

    void saveToken(Long userId, AuthToken authToken);

    /**
     * 令牌撤销
     *
     * @param userId 登录的用户 ID
     */
    void revokeToken(Long userId);
}
