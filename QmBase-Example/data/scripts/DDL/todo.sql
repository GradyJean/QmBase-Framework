DROP TABLE IF EXISTS todo;

CREATE TABLE todo
(
    id         INTEGER  NOT NULL PRIMARY KEY,               -- 主键 ID
    title      TEXT     NOT NULL,                           -- 任务标题
    content    TEXT              DEFAULT NULL,              -- 任务内容
    due_time   DATETIME          DEFAULT NULL,              -- 截止时间
    completed  INTEGER  NOT NULL DEFAULT 0,                 -- 是否完成（0=未完成，1=已完成）
    priority   INTEGER  NOT NULL DEFAULT 0,                 -- 优先级，0=普通，1=高，2=最高
    deleted    INTEGER  NOT NULL DEFAULT 0,                 -- 逻辑删除标志（0=正常，1=已删除）
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 创建时间
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP  -- 更新时间（可配合触发器更新）
);

-- 为 title 添加普通索引，便于标题查询
CREATE INDEX idx_todo_title ON todo (title);