package com.qm.base.auth.model.dto;

import com.qm.base.core.model.auth.dto.AbstractAuth;

/**
 * 注册请求对象
 * 支持多种注册场景，使用通用凭证（用户名、邮箱、手机号或第三方 openId）与密码进行注册。
 */
public class RegisterRequest extends AbstractAuth {
    /**
     * 验证码（可来自手机或邮箱），用于校验身份合法性。
     */
    private String verificationCode;



    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
