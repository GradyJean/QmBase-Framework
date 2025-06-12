package com.qm.base.auth.wechat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qm.base.core.utils.StringUtils;

import java.io.Serializable;

/**
 * 微信授权登录接口响应结果封装类。
 * <p>
 * 对应微信开放平台返回的 access_token 响应结构，包含 accessToken、openid、scope 等字段。
 * 支持错误判断与错误码读取。
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatAuthResult implements Serializable {

    /**
     * 用户授权的访问 token。
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * access_token 接口调用凭证超时时间，单位：秒。
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    /**
     * 用户刷新 access_token 的凭证。
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 授权用户的唯一标识。
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 用户授权的作用域，使用逗号（,）分隔。
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * 授权用户统一标识（仅在绑定开放平台账号后提供）。
     */
    @JsonProperty("unionid")
    private String unionid;

    /**
     * 错误码（如果存在错误）。
     */
    @JsonProperty("errcode")
    private String errCode;

    /**
     * 错误信息（如果存在错误）。
     */
    @JsonProperty("errmsg")
    private String errMsg;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * 判断接口返回是否包含错误。
     *
     * @return true 表示有错误码，说明返回的是错误响应。
     */
    public boolean isError() {
        return StringUtils.isNotBlank(errCode);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}