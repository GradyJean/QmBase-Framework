package com.qm.base.auth.service.verify;

import com.qm.base.core.model.auth.enums.IdentifierType;

public interface VerificationService {
    /**
     * 验证用户标识是否合法、邮箱验证码、短信验证码等
     *
     * @param identifier       用户凭证标识（如手机号、邮箱）
     * @param verificationCode 用户提交的凭证（如明文密码或验证码）
     * @return 是否验证通过
     */
    boolean verifyCode(String identifier, String verificationCode, IdentifierType identifierType);

    /**
     * 发送验证码
     *
     * @param identifier     用户标识
     * @param identifierType 用户类型
     * @return 是否发送成功
     */
    boolean sendVerifyCode(String identifier, IdentifierType identifierType);
}
