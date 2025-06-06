package com.qm.base.core.auth.config;

/**
 * 配置文件接口
 */
public interface TokenProperties {
    /**
     * 签名密钥。
     * 要求长度至少为32字节（256位），用于支持HMAC-SHA256及以上的JWT签名算法。
     * 推荐配置方式：在 application.yml 中设置 qm.auth.jwt.secret，长度不少于32个字符。
     */
    String getSecret();

    /**
     * 签发者（Issuer）。
     */
    String getIssuer();
}
