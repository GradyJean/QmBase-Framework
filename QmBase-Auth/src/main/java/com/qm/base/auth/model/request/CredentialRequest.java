package com.qm.base.auth.model.request;

import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.model.AuthCredential;
import com.qm.base.core.utils.RegexUtils;

public class CredentialRequest extends AuthCredential {
    /**
     * 验证码值（短信、邮箱）。
     */
    private String verifyCode;

    @Override
    public IdentifierType getIdentifierType() {
        String id = getIdentifier();
        IdentifierType identifierType = null;
        if (RegexUtils.isEmail(id)) {
            identifierType = IdentifierType.EMAIL;
        }

        if (RegexUtils.isPhone(id)) {
            identifierType = IdentifierType.PHONE_NUMBER;
        }
        // 必须为手机号或者邮箱
        AuthAssert.INSTANCE.isTrue(identifierType == IdentifierType.EMAIL
                        || identifierType == IdentifierType.PHONE_NUMBER,
                AuthError.AUTH_EMAIL_OR_PHONE_INVALID);
        return identifierType;
    }

    @Override
    public String getIdentifier() {
        return AuthAssert.INSTANCE.notBlank(identifier, AuthError.AUTH_EMAIL_OR_PHONE_EMPTY);
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
