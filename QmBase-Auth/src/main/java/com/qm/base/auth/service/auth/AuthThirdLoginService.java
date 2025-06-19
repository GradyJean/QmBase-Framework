package com.qm.base.auth.service.auth;

import com.qm.base.auth.model.vo.Platform;
import com.qm.base.core.auth.model.AuthToken;
import jakarta.servlet.http.HttpServletRequest;


import java.util.List;

public interface AuthThirdLoginService {
    String generateLoginUrl(String platform);

    AuthToken login(String platform, HttpServletRequest request);

    List<Platform> platforms();
}
