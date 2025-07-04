DROP TABLE IF EXISTS role_mapping;
-- 角色映射表，用于定义用户与角色的关系
CREATE TABLE role_mapping
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id    TEXT NOT NULL,    -- 用户ID
    role_code  TEXT NOT NULL,    -- 映射的角色编码
    domain     TEXT DEFAULT '*', -- 所属领域（如租户ID），默认为全局
    scope_code      TEXT DEFAULT '*', -- 作用域（如应用ID），默认为全局
    created_at TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_role_mapping_user ON role_mapping (user_id);
CREATE INDEX idx_role_mapping_domain ON role_mapping (domain);
CREATE INDEX idx_role_mapping_scope ON role_mapping (scope_code);