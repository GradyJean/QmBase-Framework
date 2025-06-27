package com.example.it.auth.infrastructure.repository.po;

import com.qm.base.core.entity.QmEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserCredentialPo extends QmEntity {
    /**
     * 用户 ID，对应 user_profile.id
     */
    private Long userId;
    /**
     * 登录标识（如手机号、邮箱、微信 openid）
     */
    private String identifier;
    /**
     * 凭证信息（如密码、验证码、第三方 token）
     */
    private String credential;
    /**
     * 标识类型（如 USERNAME、EMAIL、PHONE、WECHAT）
     */
    private String identifierType;
    /**
     * 启用状态（true=启用，false=禁用）
     */
    private Boolean enabled;
    /**
     * 是否逻辑删除（true=删除，false=正常）
     */
    private Boolean deleted;
}
