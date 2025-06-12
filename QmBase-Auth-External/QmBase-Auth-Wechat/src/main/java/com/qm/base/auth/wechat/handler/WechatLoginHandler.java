package com.qm.base.auth.wechat.handler;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qm.base.auth.wechat.config.WechatProperties;
import com.qm.base.auth.wechat.model.WechatAuthResult;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.core.auth.third.handler.LoginHandler;
import com.qm.base.core.utils.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class WechatLoginHandler implements LoginHandler {
    private final WechatProperties wechatProperties;
    private final RestTemplate wechatRestTemplate;
    private final ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(WechatLoginHandler.class);

    public WechatLoginHandler(WechatProperties wechatProperties) {
        this.wechatProperties = wechatProperties;
        this.wechatRestTemplate = getWechatRestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String handleLogin(Map<String, String> params) throws AuthException {
        // 获取 appid
        String appId = AuthAssert.INSTANCE.notBlank(wechatProperties.getAppId(), AuthError.AUTH_THIRD_WECHAT_APPID_EMPTY);
        String secret = AuthAssert.INSTANCE.notBlank(wechatProperties.getAppSecret(), AuthError.AUTH_THIRD_WECHAT_APP_SECRET_EMPTY);
        // 获取回调传回的 code
        String code = AuthAssert.INSTANCE.notBlank(params.get("code"), AuthError.AUTH_THIRD_WECHAT_CODE_EMPTY);
        // 获取鉴权地址
        String accessTokenUrl = AuthAssert.INSTANCE.notBlank(wechatProperties.getAccessTokenUrl(), AuthError.AUTH_THIRD_WECHAT_APP_SECRET_EMPTY);
        AuthAssert.INSTANCE.isTrue(RegexUtils.isUrl(accessTokenUrl), AuthError.AUTH_THIRD_URI_INVALID);
        String uri = accessTokenUrl
                + "?appid=" + appId
                + "&secret=" + secret
                + "&code=" + code
                + "&grant_type=authorization_code";
        // 发起请求
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = wechatRestTemplate.getForEntity(uri, String.class);
        } catch (RestClientException e) {
            logger.error(e.getMessage(), e);
            throw new AuthException(AuthError.AUTH_THIRD_ERROR);
        }
        // 请求错误 打印日志
        if (responseEntity.getStatusCode().isError()) {
            logger.error(responseEntity.toString());
            throw new AuthException(AuthError.AUTH_THIRD_ERROR);
        }
        // 获取字符串结果对象
        String resultRaw = responseEntity.getBody();
        WechatAuthResult wechatAuthResult;
        // 转对象
        try {
            wechatAuthResult = objectMapper.readValue(resultRaw, WechatAuthResult.class);
        } catch (JacksonException e) {
            logger.error(e.getMessage(), e);
            throw new AuthException(AuthError.AUTH_THIRD_ERROR);
        }
        AuthAssert.INSTANCE.notNull(wechatAuthResult, AuthError.AUTH_THIRD_ERROR);
        assert wechatAuthResult != null;
        if (wechatAuthResult.isError()) {
            logger.warn("微信登录错误:{}-{}", wechatAuthResult.getErrCode(), wechatAuthResult.getErrMsg());
            throw new AuthException(AuthError.AUTH_THIRD_ERROR);
        }
        return AuthAssert.INSTANCE.notBlank(wechatAuthResult.getUnionid(), AuthError.AUTH_THIRD_ERROR);
    }

    private RestTemplate getWechatRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }
}
