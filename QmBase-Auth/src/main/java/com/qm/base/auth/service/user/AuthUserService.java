package com.qm.base.auth.service.user;

import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.core.model.auth.enums.IdentifierType;

/**
 * 用户服务接口
 */
public interface AuthUserService {
    /**
     * 根据用户名查找用户信息（用户名可以是手机号、邮箱或其他凭证标识）
     *
     * @param identifier     用户凭证标识
     * @param identifierType 用户类型
     * @return User 对象，若未找到则返回 null
     */
    AuthUser findByIdentifier(String identifier, IdentifierType identifierType);

    /**
     * 注册新用户
     *
     * @param authUser 用户对象
     * @return 注册后的 User 对象
     */
    AuthUser createUser(AuthUser authUser);

    /**
     * 用 userId 查询用户信息
     *
     * @param userId 用户 ID
     * @return user 对象
     */
    AuthUser findByUserId(Long userId);
}
