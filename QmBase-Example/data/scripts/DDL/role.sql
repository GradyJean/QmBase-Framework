DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    role_code   TEXT NOT NULL UNIQUE,     -- 角色编码（唯一）
    role_name   TEXT NOT NULL,            -- 角色名称
    role_type   TEXT    DEFAULT 'CUSTOM', -- 类型：SYSTEM / TENANT / CUSTOM
    description TEXT,                     -- 角色描述
    status      INTEGER DEFAULT 1,        -- 状态：1启用，0禁用
    built_in    INTEGER DEFAULT 0,        -- 是否内置：1是，0否
    created_at  TEXT    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TEXT    DEFAULT CURRENT_TIMESTAMP
);