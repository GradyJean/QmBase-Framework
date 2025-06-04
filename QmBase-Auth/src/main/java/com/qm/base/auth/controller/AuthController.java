package com.qm.base.auth.controller;

import com.qm.base.auth.model.dto.*;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.auth.service.AuthService;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.shared.base.model.Result;
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
    public Result<AuthToken> register(@RequestBody RegisterRequest request) {
        return Result.SUCCESS(authService.register(request));
    }

    /**
     * 用户标识是否存在。
     *
     * @param identifier     用户标识
     * @param identifierType 标识类型
     * @return 注册响应结果
     */
    @GetMapping("identifier/exists")
    public Result<Boolean> identifierExists(@RequestParam("identifier") String identifier, @RequestParam("type") IdentifierType identifierType) {
        return Result.SUCCESS(authService.identifierExists(identifier, identifierType));
    }

    /**
     * 发送验证码
     *
     * @param request 用户标识
     * @return 注册响应结果
     */
    @PostMapping("verifyCode/send")
    public Result<Boolean> sendVerifyCode(@RequestBody VerifyCodeRequest request) {
        return Result.SUCCESS(authService.sendVerifyCode(request.getIdentifier(), request.getIdentifierType()));
    }

    /**
     * 刷新Token接口。
     *
     * @param request refreshToken字符串
     * @return 新的Token
     */
    @PostMapping("/refresh")
    public Result<AuthToken> refresh(@RequestBody RefreshTokenRequest request) {
        return Result.SUCCESS(authService.refresh(request.getRefreshToken()));
    }

    /**
     * 密码重置。
     *
     * @param request 当前 accessToken
     * @return 操作结果
     */
    @PostMapping("/password/reset")
    public Result<Boolean> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean result = authService.resetPassword(request.getIdentifier(), request.getCredential(), request.getVerificationCode(), request.getIdentifierType());
        return Result.SUCCESS(result);
    }

    /**
     * 登出接口。
     *
     * @param request 当前 accessToken
     * @return 操作结果
     */
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestBody LogoutRequest request) {
        return Result.SUCCESS(authService.logout(request.getAccessToken()));
    }
}
