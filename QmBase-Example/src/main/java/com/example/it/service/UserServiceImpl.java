package com.example.it.service;

import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.AuthUserService;
import com.qm.base.core.model.auth.enums.IdentifierType;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements AuthUserService {

    @Override
    public AuthUser findByIdentifier(String identifier, IdentifierType identifierType) {
        return null;
    }

    @Override
    public boolean verifyIdentifierCode(String identifier, String verificationCode, IdentifierType identifierType) {
        return true;
    }


    @Override
    public boolean sendVerifyIdentifierCode(String identifier, IdentifierType identifierType) {
        return false;
    }

    @Override
    public boolean resetCredential(String identifier, String newCredential) {
        return false;
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
