package com.qm.base.auth.manager;


import com.qm.base.auth.service.user.AuthUserService;
import com.qm.base.auth.service.verify.VerificationService;

/**
 * 凭据管理器
 */
public interface CredentialManager extends AuthUserService, VerificationService {

    /**
     * 重置密码
     *
     * @param userId        用户 ID
     * @param newCredential 新密码或新凭证
     * @return 是否重置成功
     */
    Boolean resetCredential(Long userId, String newCredential);

    /**
     * 令牌撤销
     *
     * @param userId 登录的用户 ID
     */
    boolean revokeToken(Long userId);
}
