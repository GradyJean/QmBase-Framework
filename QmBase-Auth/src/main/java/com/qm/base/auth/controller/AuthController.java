package com.qm.base.auth.controller;

import com.qm.base.auth.model.request.CredentialRequest;
import com.qm.base.auth.model.request.LoginRequest;
import com.qm.base.auth.model.request.RegisterRequest;
import com.qm.base.auth.model.request.TokenRequest;
import com.qm.base.auth.service.auth.AuthService;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证相关控制器。
 */
@RestController
@RequestMapping("/auth")
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
     * 注册接口。
     *
     * @param request 注册请求参数
     * @return 注册响应结果
     */
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody RegisterRequest request) {
        return Result.SUCCESS(authService.register(request));
    }

    /**
     * 用户标识是否存在。
     *
     * @param identifier 用户标识
     * @return 注册响应结果
     */
    @GetMapping("identifier/exists")
    public Result<Boolean> identifierExists(@RequestParam("identifier") String identifier) {
        return Result.SUCCESS(authService.identifierExists(identifier));
    }

    /**
     * 发送验证码
     *
     * @param request 用户标识
     * @return 注册响应结果
     */
    @PostMapping("verifyCode/send")
    public Result<Boolean> sendVerifyCode(@RequestBody CredentialRequest request) {
        return Result.SUCCESS(authService.sendVerifyCode(request.getIdentifier(), request.getIdentifierType()));
    }

    /**
     * 刷新Token接口。
     *
     * @param request refreshToken字符串
     * @return 新的Token
     */
    @PostMapping("/token/refresh")
    public Result<AuthToken> tokenRefresh(@RequestBody TokenRequest request) {
        return Result.SUCCESS(authService.tokenRefresh(request.getToken()));
    }

    /**
     * 密码重置。
     *
     * @param request 当前 accessToken
     * @return 操作结果
     */
    @PostMapping("/credential/reset")
    public Result<Boolean> resetCredential(@RequestBody CredentialRequest request) {
        boolean result = authService.resetCredential(request);
        return Result.SUCCESS(result);
    }

    /**
     * 登出接口。
     *
     * @param request 当前 accessToken
     * @return 操作结果
     */
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestBody TokenRequest request) {
        return Result.SUCCESS(authService.logout(request.getToken()));
    }
}
