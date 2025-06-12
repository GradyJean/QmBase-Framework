package com.qm.base.auth.wechat.provider;

import com.qm.base.auth.wechat.config.WechatProperties;
import com.qm.base.auth.wechat.handler.WechatLoginHandler;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.auth.third.config.LoginProperties;
import com.qm.base.core.auth.third.handler.LoginHandler;
import com.qm.base.core.auth.third.provider.LoginProvider;
import com.qm.base.core.utils.RegexUtils;
import org.springframework.stereotype.Component;

@Component
public class WechatProvider implements LoginProvider {
    private final WechatProperties props;
    private final WechatLoginHandler wechatLoginHandler;

    public WechatProvider(WechatProperties props, WechatLoginHandler handler) {
        this.props = props;
        this.wechatLoginHandler = handler;
    }

    @Override
    public String getName() {
        return "微信";
    }

    @Override
    public String getLoginUrl(String state) {
        String appId = AuthAssert.INSTANCE.notBlank(props.getAppId(), AuthError.AUTH_THIRD_WECHAT_APPID_EMPTY);
        String redirectUri = AuthAssert.INSTANCE.notBlank(props.getRedirectUri(), AuthError.AUTH_THIRD_WECHAT_REDIRECT_URI_EMPTY);
        AuthAssert.INSTANCE.isTrue(RegexUtils.isUrl(redirectUri), AuthError.AUTH_THIRD_URI_INVALID);
        return props.getAuthorizeUrl()
                + "?appid=" + appId
                + "&redirect_uri=" + redirectUri
                + "&state=" + state
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=" + state;
    }

    @Override
    public String getPlatform() {
        return "wechat";
    }

    @Override
    public LoginHandler getLoginHandler() {
        return this.wechatLoginHandler;
    }

    @Override
    public LoginProperties getLoginProperties() {
        return this.props;
    }
}
