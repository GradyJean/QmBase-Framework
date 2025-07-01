-- 插入系统内置角色
INSERT INTO role (role_code, role_name, role_type, description, status, built_in)
VALUES ('ADMIN', '管理员', 'SYSTEM', '系统管理员，拥有最高权限', 1, 1),
       ('TENANT_OWNER', '租户拥有者', 'TENANT', '租户负责人，拥有租户内管理权限', 1, 1);

-- 插入自定义角色
INSERT INTO role (role_code, role_name, role_type, description, status, built_in)
VALUES ('EDITOR', '内容编辑', 'CUSTOM', '负责内容编辑与发布的用户', 1, 0),
       ('VIEWER', '只读用户', 'CUSTOM', '只拥有查看权限的普通用户', 1, 0);

-- 用户 593718197424578560 映射为系统管理员角色（全局，无 domain）
INSERT INTO role_mapping (user_id, role_code, domain)
VALUES ('593718197424578560', 'ADMIN', NULL);

-- 用户 593718197424578560 映射为租户拥有者角色，属于 domain "tenant_001"
INSERT INTO role_mapping (user_id, role_code, domain)
VALUES ('593718197424578560', 'TENANT_OWNER', 'tenant_001');

-- 用户 593718197424578560 同时是 tenant_002 下的编辑者
INSERT INTO role_mapping (user_id, role_code, domain)
VALUES ('593718197424578560', 'EDITOR', 'tenant_002');

INSERT INTO resource_policy (subject, resource, resource_type, action, domain)
VALUES ('admin', '*', '*', '*', '*');

INSERT INTO resource_domain_mapping (resource_pattern,
                                     http_method,
                                     action,
                                     domain,
                                     description)
VALUES ('/user/test',
        'GET',
        'view',
        'user',
        'user域下的用户测试接口');