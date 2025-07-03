DROP TABLE IF EXISTS resource_policy;
-- 资源权限策略表，用于定义主体对资源的访问控制策略
CREATE TABLE resource_policy
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    subject       TEXT NOT NULL,    -- 主体（如用户ID或角色）
    resource      TEXT NOT NULL,    -- 资源对象
    resource_type TEXT,             -- 资源类型（如文档、页面等）
    action        TEXT NOT NULL,    -- 操作行为（如read、write）
    domain        TEXT DEFAULT '*', -- 所属领域（如租户ID）
    created_at    TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_resource_policy_subj ON resource_policy (subject);
CREATE INDEX idx_resource_policy_domain ON resource_policy (domain);
CREATE UNIQUE INDEX idx_resource_policy_unique
    ON resource_policy (subject, resource, action, domain);