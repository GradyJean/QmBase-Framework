package com.qm.base.auth.controller;

import com.qm.base.auth.model.dto.LoginRequest;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.auth.service.AuthService;
import com.qm.base.shared.base.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证相关控制器。
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;

    }

    /**
     * 登录接口。
     *
     * @param request 登录请求参数
     * @return 登录响应结果
     */
    @PostMapping("/login")
    public Result<AuthToken> login(@RequestBody LoginRequest request) {
        return Result.SUCCESS(authService.login(request));
    }

    /**
     * 刷新Token接口。
     *
     * @param refreshToken refreshToken字符串
     * @return 新的Token
     */
    @PostMapping("/refresh")
    public Result<AuthToken> refresh(@RequestParam("refreshToken") String refreshToken) {
        return Result.SUCCESS(authService.refresh(refreshToken));
    }

    /**
     * 登出接口。
     *
     * @param accessToken 当前 accessToken
     * @return 操作结果
     */
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(name = "Authorization") String accessToken) {
        // TODO: 可选实现缓存失效、黑名单、上下文清理等
        return Result.SUCCESS();
    }
}
