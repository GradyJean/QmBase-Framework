package com.qm.base.auth.model.dto;

import com.qm.base.core.model.auth.dto.AbstractAuth;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.shared.base.utils.RegexUtils;

public class ResetPasswordRequest extends AbstractAuth {
    /**
     * 验证码值（短信、邮箱或图形验证码）。
     */
    private String verificationCode;

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
