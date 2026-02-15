package com.qm.base.core.common.model;

/**
 * 通用分页请求参数
 * <p>
 * 定义分页基础字段：
 * - page 当前页码（从 1 开始）
 * - size 每页条数
 */
public class PageInput {

    /**
     * 当前页（从 1 开始）
     */
    private int page = 1;

    /**
     * 每页大小
     */
    private int size = 10;

    public PageInput() {
    }

    public PageInput(int page, int size) {
        this.page = Math.max(page, 1);
        this.size = size < 1 ? 10 : size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 1);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size < 1 ? 10 : size;
    }

    /**
     * 计算数据库查询偏移量（offset）
     */
    public int getOffset() {
        return (page - 1) * size;
    }
}
