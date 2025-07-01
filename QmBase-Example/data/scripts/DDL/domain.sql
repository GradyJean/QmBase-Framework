DROP TABLE IF EXISTS domain;

CREATE TABLE domain
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    domain_code TEXT NOT NULL, -- 权限域标识（如：BLOG、COURSE）
    domain_name TEXT NOT NULL, -- 权限域名称（如：博客系统、课程系统）
    description TEXT,
    status      INTEGER  DEFAULT 1,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 为 domain_code 添加唯一索引（虽为主键，显式声明更清晰）
CREATE UNIQUE INDEX idx_domain_code ON domain (domain_code);
CREATE UNIQUE INDEX idx_domain_id ON domain (id);