package com.qm.base.auth.service.auth.impl;

import com.qm.base.auth.model.vo.Platform;
import com.qm.base.auth.service.auth.AuthThirdLoginService;
import com.qm.base.core.auth.model.AuthToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthThirdLoginServiceImpl implements AuthThirdLoginService {

    @Override
    public String generateLoginUrl(String platform) {
        return "";
    }

    @Override
    public AuthToken login(String platform, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<Platform> platforms() {
        return List.of();
    }
}
