package com.qm.base.auth.service;


import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.core.model.auth.enums.IdentifierType;

/**
 * 用户服务接口，定义用户认证体系中涉及的操作，如注册、验证、找回密码等。
 */
public interface CredentialService {

    /**
     * 根据用户名查找用户信息（用户名可以是手机号、邮箱或其他凭证标识）
     *
     * @param identifier     用户凭证标识
     * @param identifierType 用户类型
     * @return User 对象，若未找到则返回 null
     */
    AuthUser findByIdentifier(String identifier, IdentifierType identifierType);

    /**
     * 验证用户标识是否合法、邮箱验证码、短信验证码等
     *
     * @param identifier       用户凭证标识（如手机号、邮箱）
     * @param verificationCode 用户提交的凭证（如明文密码或验证码）
     * @return 是否验证通过
     */
    boolean verifyIdentifierCode(String identifier, String verificationCode, IdentifierType identifierType);

    /**
     * 发送验证码
     *
     * @param identifier     用户标识
     * @param identifierType 用户类型
     * @return 是否发送成功
     */
    boolean sendVerifyIdentifierCode(String identifier, IdentifierType identifierType);

    /**
     * 重置密码
     *
     * @param identifier    用户凭证标识
     * @param newCredential 新密码或新凭证
     * @return 是否重置成功
     */
    boolean resetCredential(String identifier, String newCredential);

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

    /**
     * 登出处理
     *
     * @param userId 登录的用户 ID
     */
    void logoutHandler(Long userId);

    /**
     * 更新密码
     *
     * @param authUser 用户对象
     */
    Boolean updatePassword(AuthUser authUser);
}
