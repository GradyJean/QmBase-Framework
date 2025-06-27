DROP TABLE IF EXISTS user_credential;

CREATE TABLE user_credential
(
    id              INTEGER  NOT NULL PRIMARY KEY,               -- 主键 ID
    user_id         INTEGER  NOT NULL,                           -- 关联的用户 ID
    identifier      TEXT     NOT NULL,                           -- 用户标识，如邮箱、手机号、微信 openid
    credential      TEXT              DEFAULT NULL,              -- 凭证信息，如密码、验证码、令牌等
    identifier_type TEXT     NOT NULL,                           -- 标识类型，如 USERNAME、EMAIL、PHONE、WECHAT
    enabled         INTEGER  NOT NULL DEFAULT 1,                 -- 启用状态（1=启用，0=禁用）
    deleted         INTEGER  NOT NULL DEFAULT 0,                 -- 逻辑删除标志（1=已删除，0=未删除）
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 更新时间（可用触发器更新）
    created_by      TEXT              DEFAULT NULL,              -- 创建者
    updated_by      TEXT              DEFAULT NULL,              -- 更新者
    UNIQUE (identifier, identifier_type)                         -- 唯一约束
); -- SQLite 实际不支持 INDEX 语法，需要单独 CREATE INDEX 语句
CREATE INDEX idx_user_id ON user_credential (user_id);