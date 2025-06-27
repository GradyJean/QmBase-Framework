DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
                              id          INTEGER     NOT NULL PRIMARY KEY,                     -- 主键，通常使用 INTEGER 类型配合 AUTOINCREMENT
                              nickname    TEXT        NOT NULL,                                 -- 昵称
                              avatar_url  TEXT        DEFAULT NULL,                             -- 头像 URL
                              phone       TEXT        DEFAULT NULL,                             -- 电话号码
                              email       TEXT        DEFAULT NULL,                             -- 邮箱地址
                              bio         TEXT        DEFAULT NULL,                             -- 个人简介

                              enabled     INTEGER     NOT NULL DEFAULT 1,                       -- 启用状态（1=启用，0=禁用）
                              deleted     INTEGER     NOT NULL DEFAULT 0,                       -- 逻辑删除标记（0=正常，1=已删除）

                              created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- 创建时间
                              updated_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,       -- 更新时间（需通过触发器维护）
                              created_by  TEXT        DEFAULT NULL,                             -- 创建者 ID 或用户名
                              updated_by  TEXT        DEFAULT NULL                              -- 更新者 ID 或用户名
);