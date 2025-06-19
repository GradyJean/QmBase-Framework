package com.qm.base.auth.service.auth.impl;

import com.qm.base.auth.context.AuthContextHolder;
import com.qm.base.auth.manager.CredentialManager;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.model.vo.Platform;
import com.qm.base.auth.service.auth.AuthThirdLoginService;
import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.auth.third.handler.LoginHandler;
import com.qm.base.core.auth.third.provider.LoginProvider;
import com.qm.base.core.auth.third.provider.LoginProviderRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthThirdLoginServiceImpl implements AuthThirdLoginService {

    private final LoginProviderRegistry loginProviderRegistry;
    private final CredentialManager credentialManager;

    public AuthThirdLoginServiceImpl(LoginProviderRegistry loginProviderRegistry, CredentialManager credentialManager) {
        this.loginProviderRegistry = loginProviderRegistry;
        this.credentialManager = credentialManager;
    }

    /**
     * 生成第三方平台的登录 URL。
     * <p>
     * 会将传入的 deviceId 编码后注入 state 字段中，用于后续回调时识别用户来源设备。
     *
     * @param platform 第三方平台标识，如 wechat、alipay
     * @return 第三方平台跳转登录地址
     */
    @Override
    public String generateLoginUrl(String platform) {
        String deviceId = AuthAssert.INSTANCE.notBlank(AuthContextHolder.getContext().getDeviceId(), AuthError.AUTH_DEVICE_ID_EMPTY);
        return getLoginProvider(platform).getLoginUrl(deviceId);
    }

    @Override
    public AuthToken login(String platform, HttpServletRequest request) {
        // 获取参数
        Map<String, String> params = extractParams(request);
        AuthAssert.INSTANCE.isTrue(params != null && !params.isEmpty(), AuthError.AUTH_THIRD_LOGIN_CALLBACK_ERROR);
        // 获取第三方登录处理程序
        LoginProvider loginProvider = getLoginProvider(platform);
        LoginHandler loginHandler = AuthAssert.INSTANCE.notNull(loginProvider.getLoginHandler(), AuthError.AUTH_THIRD_ERROR);
        // 获取第三方登录的 unionid
        String unionid = AuthAssert.INSTANCE.notBlank(loginHandler.handleLogin(params), AuthError.AUTH_THIRD_LOGIN_INVALID);
        IdentifierType identifierType = IdentifierType.valueOf(platform.toUpperCase());
        // state 字段用于传递 deviceId
        String deviceId = AuthAssert.INSTANCE.notBlank(params.get("state"), AuthError.AUTH_THIRD_LOGIN_STATE_EMPTY);
        AuthUser authUser = credentialManager.findByIdentifier(unionid);
        // 如果第一次验证码登录记录不存在则直接注册
        if (Objects.isNull(authUser)) {
            authUser = credentialManager.createUser(AuthUser.of(unionid, null, identifierType));
        }
        // 如果 accessToken 没过期就返回旧 token
        AuthToken authToken = credentialManager.findAuthTokenByUserId(authUser.getUserId(), deviceId);
        if (!Objects.isNull(authToken) && !authToken.getAccessToken().hasExpired()) {
            return authToken;
        }
        return credentialManager.generateAuthToken(authUser.getUserId(), deviceId);
    }

    @Override
    public List<Platform> platforms() {
        return loginProviderRegistry.getAll().stream()
                .map(provider ->
                        new Platform(
                                provider.getName(),
                                provider.getPlatform()))
                .collect(Collectors.toList());
    }

    /**
     * 根据平台标识获取对应的登录提供器。
     *
     * @param platform 平台编码
     * @return 匹配的 LoginProvider 实例
     */
    private LoginProvider getLoginProvider(String platform) {
        return AuthAssert.INSTANCE.notNull(
                loginProviderRegistry.getPlatform(platform),
                AuthError.AUTH_THIRD_PLATFORM_NOT_EXIST);
    }

    /**
     * 获取参数
     *
     * @param request 第三方登录回调的参数
     * @return map 参数
     */
    private Map<String, String> extractParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()[0] // 取第一个值即可
                ));
    }
}
