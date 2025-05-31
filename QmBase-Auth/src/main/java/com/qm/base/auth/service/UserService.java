package com.qm.base.auth.service;


import com.qm.base.core.user.User;

/**
 * 用户服务接口，定义用户认证体系中涉及的操作，如注册、验证、找回密码等。
 */
public interface UserService {

    /**
     * 根据用户名查找用户信息（用户名可以是手机号、邮箱或其他凭证标识）
     *
     * @param identifier 用户凭证标识
     * @return User 对象，若未找到则返回 null
     */
    User findByIdentifier(String identifier);

    /**
     * 验证凭证是否合法，例如密码、短信验证码等
     *
     * @param identifier 用户凭证标识（如手机号、邮箱）
     * @param credential 用户提交的凭证（如明文密码或验证码）
     * @return 是否验证通过
     */
    boolean verifyCredential(String identifier, String credential);

    /**
     * 忘记密码流程，支持发送重置凭证
     *
     * @param identifier 用户凭证标识
     * @return 是否发送成功
     */
    boolean sendResetToken(String identifier);

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
     * @param identifier 用户凭证标识（如手机号、邮箱）
     * @param credential 用户凭证（如密码、验证码）
     * @return 注册后的 User 对象
     */
    User register(String identifier, String credential);
}
