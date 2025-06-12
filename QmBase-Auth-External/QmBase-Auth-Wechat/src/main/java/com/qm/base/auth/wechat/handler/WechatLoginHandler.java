package com.qm.base.auth.wechat.handler;

import com.qm.base.auth.wechat.config.WechatProperties;
import com.qm.base.auth.wechat.model.WechatAuthResult;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.core.auth.third.handler.LoginHandler;
import com.qm.base.core.utils.RegexUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class WechatLoginHandler implements LoginHandler {
    private final WechatProperties wechatProperties;
    private final RestTemplate wechatRestTemplate;

    public WechatLoginHandler(WechatProperties wechatProperties, RestTemplate wechatRestTemplate) {
        this.wechatProperties = wechatProperties;
        this.wechatRestTemplate = wechatRestTemplate;
    }

    @Override
    public String handleLogin(Map<String, String> params) throws AuthException {
        // 获取 appid
        String appId = AuthAssert.INSTANCE.notBlank(wechatProperties.getAppId(), AuthError.AUTH_THIRD_WECHAT_APPID_EMPTY);
        String secret = AuthAssert.INSTANCE.notBlank(wechatProperties.getAppSecret(), AuthError.AUTH_THIRD_WECHAT_APP_SECRET_EMPTY);
        // 获取回调传回的 code
        String code = AuthAssert.INSTANCE.notBlank(params.get("code"), AuthError.AUTH_THIRD_WECHAT_CODE_EMPTY);
        // 获取鉴权地址
        String authorizeUrl = AuthAssert.INSTANCE.notBlank(wechatProperties.getAuthorizeUrl(), AuthError.AUTH_THIRD_WECHAT_APP_SECRET_EMPTY);
        AuthAssert.INSTANCE.isTrue(RegexUtils.isUrl(authorizeUrl), AuthError.AUTH_THIRD_URI_INVALID);
        String uri = authorizeUrl
                + "?appid=" + appId
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
        ResponseEntity<WechatAuthResult> responseEntity;
        try {
            responseEntity = wechatRestTemplate.getForEntity(uri, WechatAuthResult.class);
        } catch (RestClientException e) {
            throw new AuthException(AuthError.AUTH_THIRD_ERROR);
        }
        AuthAssert.INSTANCE.isTrue(responseEntity.getStatusCode().isError(), AuthError.AUTH_THIRD_ERROR);
        WechatAuthResult wechatAuthResult = responseEntity.getBody();
        AuthAssert.INSTANCE.notNull(wechatAuthResult, AuthError.AUTH_THIRD_ERROR);
        return AuthAssert.INSTANCE.notBlank(wechatAuthResult.getUnionid(), AuthError.AUTH_THIRD_ERROR);
    }
}
