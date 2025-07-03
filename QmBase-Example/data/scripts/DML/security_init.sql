-- 初始化数据脚本
-- 删除旧数据
delete
from scope
where id = 1;
-- 权限域管理表,用于定义系统中的权限域
insert into scope
(id,
 scope_code,
 scope_name,
 description)
values (1,
        'SYSTEM',
        '系统权限域',
        '系统级别的权限域，包含所有系统资源和操作');

-- 删除旧数据
delete
from role
where id = 1;
-- 角色管理表，用于定义系统中的角色
insert into role(id,
                 role_code,
                 role_name,
                 role_type,
                 description,
                 status,
                 built_in,
                 created_at,
                 updated_at)
values (1,
        'SYSTEM_ADMIN',
        '系统管理员',
        'SYSTEM',
        '系统管理员角色，拥有所有权限',
        1,
        1,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- 删除旧数据
delete
from role_mapping
where id = 1;
-- 角色映射表，用于定义用户与角色的关系
insert into role_mapping (id,
                          user_id,
                          role_code,
                          domain,
                          created_at)
values (1,
        '10000000000',
        'SYSTEM_ADMIN',
        '*',
        CURRENT_TIMESTAMP);
-- 删除旧数据
delete
from resource_policy
where id = 1;
-- 资源策略表，用于定义系统中的资源访问策略
insert into resource_policy (id,
                             subject,
                             resource,
                             resource_type,
                             action,
                             domain,
                             created_at)
values (1,
        'SYSTEM_ADMIN',
        '*',
        'SYSTEM',
        '*',
        '*',
        CURRENT_TIMESTAMP);