-- 初始化用户数据
-- 先删后插，确保数据唯一性
delete
from user_profile
where id = 10000000000;
-- 用户个人资料表，用于存储用户的基本信息和状态
insert into user_profile
(id,
 nickname,
 avatar_url,
 phone,
 email,
 bio,
 enabled,
 deleted,
 created_at,
 updated_at,
 created_by,
 updated_by)
values (10000000000,
        'admin',
        'https://example.com/avatar/admin.png',
        '15800000000',
        'example.gmail.com',
        '系统管理员',
        1,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'system',
        'system');


-- 初始化用户凭证数据
-- 注意：这里的 credential 字段存储的是经过加密或哈希处理的密码
-- 登录用户: 15800000000
-- 密码: Example131!

-- 先删后插，确保数据唯一性
delete
from user_credential
where id = 10000000001;
-- 用户凭证表，用于存储用户的登录凭证信息
insert into user_credential
(id,
 user_id,
 identifier,
 credential,
 identifier_type,
 enabled,
 deleted,
 created_at,
 updated_at,
 created_by,
 updated_by)
values (10000000001,
        10000000000,
        '15800000000',
        '8rRtxndVmxaLZonzocl/aw==:y0kOm4wSoiSaqmyVRU1lrDqLaw1kSv9FZv0DHWYBnZg=',
        'PHONE_NUMBER',
        1,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'system',
        'system');