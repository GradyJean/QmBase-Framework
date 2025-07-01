DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT, -- 主键 ID
    role_code   TEXT NOT NULL UNIQUE,              -- 角色编码（唯一）
    role_name   TEXT NOT NULL,                     -- 角色名称
    role_type   TEXT DEFAULT 'CUSTOM',             -- 角色类型，例如 SYSTEM / TENANT / CUSTOM
    description TEXT,                              -- 角色描述信息
    status      INTEGER DEFAULT 1,                 -- 状态标识，例如是否启用（0/1）
    built_in    INTEGER DEFAULT 0,                 -- 是否内置角色（1 表示是，0 表示否）
    created_at  TEXT DEFAULT CURRENT_TIMESTAMP,    -- 创建时间，建议为 ISO 日期格式
    updated_at  TEXT DEFAULT CURRENT_TIMESTAMP     -- 更新时间
);