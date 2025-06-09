package com.qm.base.core.common.model;


import java.util.List;

/**
 * 通用分页数据结构，适用于分页查询的响应结果。
 *
 * @param <T> 每页记录的数据类型
 *
 * 新增字段：
 * pageSize - 每页条数
 * current - 当前页码（从1开始）
 * pages - 总页数（自动计算）
 */
public class Page<T> {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页记录列表
     */
    private List<T> records;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 当前页码（从1开始）
     */
    private int current;

    /**
     * 总页数（自动计算）
     */
    private int pages;

    /**
     * 构造函数
     *
     * @param total    总记录条数
     * @param records  当前页数据列表
     * @param current  当前页码（从1开始）
     * @param pageSize 每页条数
     */
    public Page(long total, List<T> records, int current, int pageSize) {
        this.total = total;
        this.records = records;
        this.current = current;
        this.pageSize = pageSize;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.pages = (int) ((total + pageSize - 1) / pageSize);
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }
}