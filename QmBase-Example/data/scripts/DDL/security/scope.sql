DROP TABLE IF EXISTS scope;
-- 权限域管理表,用于定义系统中的权限域
CREATE TABLE scope
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    scope_code  TEXT NOT NULL,                     -- 权限域标识（如：BLOG、COURSE）
    scope_name  TEXT NOT NULL,                     -- 权限域名称（如：博客系统、课程系统,博客后台管理系统）
    description TEXT,                              -- 可选字段，用于说明该权限域的用途
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP -- 创建时间，默认为当前时间
);

-- 为 scope_code 添加唯一索引（虽为主键，显式声明更清晰）
CREATE UNIQUE INDEX idx_scope_code ON scope (scope_code);
CREATE UNIQUE INDEX idx_scope_id ON scope (id);