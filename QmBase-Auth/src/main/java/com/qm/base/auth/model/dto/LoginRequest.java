package com.qm.base.auth.model.dto;

import com.qm.base.core.model.auth.dto.AbstractAuth;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.shared.base.utils.RegexUtils;

/**
 * 登录请求对象，封装多种登录方式的通用参数结构。
 * 可支持用户名/手机号/邮箱/第三方平台标识等作为登录入口。
 */
public class LoginRequest extends AbstractAuth {

    /**
     * 是否为验证码登录（如短信验证码或邮箱验证码），默认为 false。
     */
    private boolean useVerificationCode = false;

    /**
     * 验证码值（短信、邮箱或图形验证码）。
     */
    private String verificationCode;

    public boolean isUseVerificationCode() {
        return useVerificationCode;
    }

    public void setUseVerificationCode(boolean useVerificationCode) {
        this.useVerificationCode = useVerificationCode;
    }

    /**
     * 验证码值（短信、邮箱或图形验证码）。
     */
    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * 根据 identifier 判断其类型（邮箱、手机号或用户名），用于注册逻辑分流。
     */
    @Override
    public IdentifierType getIdentifierType() {
        if (identifierType != null) {
            return identifierType;
        }
        String id = this.identifier;
        if (RegexUtils.isEmail(id)) {
            return IdentifierType.EMAIL;
        }

        if (RegexUtils.isPhone(id)) {
            return IdentifierType.PHONE_NUMBER;
        }

        return IdentifierType.USER_NAME;
    }
}
