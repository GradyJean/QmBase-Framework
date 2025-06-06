package com.qm.base.auth.model.request;

import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.model.AuthCredential;
import com.qm.base.core.utils.RegexUtils;

public class CredentialRequest extends AuthCredential {
    /**
     * 验证码值（短信、邮箱）。
     */
    private String verifyCode;

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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
