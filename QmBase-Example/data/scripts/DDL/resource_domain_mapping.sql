DROP TABLE IF EXISTS resource_domain_mapping;

CREATE TABLE resource_domain_mapping
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    resource_pattern TEXT NOT NULL, -- 支持 Ant 风格，例如 /doc/**、/user/${id}
    http_method      TEXT not null, -- GET, POST, PUT, DELETE，可为空表示任意方法
    action           TEXT not null, -- 资源上的行为，如 view/edit/delete，不可为空
    domain           TEXT NOT NULL, -- 权限域，必须指定
    description      TEXT,          -- 可选字段，用于说明该映射的用途
    create_time      DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 索引建议
CREATE INDEX idx_resource_domain_mapping_domain ON resource_domain_mapping (domain);
CREATE INDEX idx_resource_domain_mapping_method ON resource_domain_mapping (http_method);
CREATE INDEX idx_resource_domain_mapping_pattern ON resource_domain_mapping (resource_pattern);