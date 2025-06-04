package com.example.it.service;

import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.CredentialService;
import com.qm.base.core.model.auth.enums.IdentifierType;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Override
    public AuthUser findByIdentifier(String identifier, IdentifierType identifierType) {
        return null;
    }

    @Override
    public boolean verifyCode(String identifier, String verificationCode, IdentifierType identifierType) {
        return true;
    }


    @Override
    public boolean sendVerifyCode(String identifier, IdentifierType identifierType) {
        return false;
    }

    @Override
    public Boolean resetCredential(Long userId, String newCredential) {
        return null;
    }


    @Override
    public AuthUser createUser(AuthUser authUser) {
        return null;
    }

    @Override
    public AuthUser findByUserId(Long userId) {
        return null;
    }

    @Override
    public void logoutHandler(Long userId) {
    }
}
