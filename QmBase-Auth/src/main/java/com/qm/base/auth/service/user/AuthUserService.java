package com.qm.base.auth.service.user;

import com.qm.base.auth.model.vo.AuthUser;

/**
 * 用户服务接口
 */
public interface AuthUserService {
    /**
     * 根据用户名查找用户信息（用户名可以是手机号、邮箱或其他凭证标识）
     *
     * @param identifier 用户凭证标识
     * @return User 对象，若未找到则返回 null
     */
    AuthUser findByIdentifier(String identifier);

    /**
     * 注册新用户
     *
     * @param authUser 用户对象
     * @return 注册后的 User 对象
     */
    AuthUser createUser(AuthUser authUser);

    /**
     * 更新密码
     *
     * @param userId        用户 ID
     * @param newCredential 新密码或新凭证
     * @return 是否重置成功
     */
    Boolean updateCredential(String userId, String newCredential);
}
