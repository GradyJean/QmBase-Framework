package com.qm.base.auth.controller;

import com.qm.base.auth.model.vo.Platform;
import com.qm.base.auth.service.auth.AuthThirdLoginService;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.common.model.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 第三方认证控制器。
 * <p>
 * 提供与外部平台（如微信、支付宝、抖音等）的统一认证接入能力，
 * 包括生成登录二维码地址、处理登录回调、查询支持的第三方平台列表等功能。
 * <p>
 * 接口路径统一以 /auth/third 开头。
 */
@RestController
@RequestMapping("/auth/third")
public class ThirdLoginController {
    private final AuthThirdLoginService authThirdLoginService;

    public ThirdLoginController(AuthThirdLoginService authThirdLoginService) {
        this.authThirdLoginService = authThirdLoginService;
    }

    /**
     * 生成指定平台的第三方登录授权地址。
     * <p>
     * 请求方式：GET
     * 路径：/auth/third/{platform}/url
     *
     * @param platform 第三方平台标识（如：wechat、alipay、douyin）
     * @return 包含跳转地址的响应结果
     */
    @GetMapping("{platform}/url")
    public Result<String> generateLoginUrl(@PathVariable String platform, @RequestParam("deviceId") String deviceId) {
        return Result.SUCCESS(authThirdLoginService.generateLoginUrl(platform, deviceId));
    }

    /**
     * 第三方平台扫码登录处理接口。
     * <p>
     * 请求方式：POST/GET
     * 路径：/auth/third/{platform}/login
     * <p>
     * 请求来源可能是用户扫码授权后的回调跳转，平台参数结构各异，
     * 所以统一传递 HttpServletRequest，由具体平台处理器解析。
     *
     * @param platform 第三方平台标识
     * @param request  HTTP 请求对象（含 query 参数、body、header 等）
     * @return 登录成功后返回系统生成的 AuthToken
     */
    @RequestMapping("{platform}/login")
    public Result<AuthToken> login(@PathVariable String platform, HttpServletRequest request) {
        return Result.SUCCESS(authThirdLoginService.login(platform, request));
    }

    /**
     * 获取当前系统支持的第三方登录平台列表。
     * <p>
     * 请求方式：GET
     * 路径：/auth/third/platforms
     *
     * @return 第三方平台信息集合（用于前端渲染登录入口）
     */
    @GetMapping("platforms")
    public Result<List<Platform>> platforms() {
        return Result.SUCCESS(authThirdLoginService.platforms());
    }
}
