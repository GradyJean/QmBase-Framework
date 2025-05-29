package com.qm.base.shared.base.dto;


import java.util.List;

/**
 * 通用分页数据结构，适用于分页查询的响应结果。
 *
 * @param <T> 每页记录的数据类型
 */
public class PageDTO<T> {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页记录列表
     */
    private List<T> records;

    /**
     * 构造函数
     *
     * @param total   总记录条数
     * @param records 当前页数据列表
     */
    public PageDTO(long total, List<T> records) {
        this.total = total;
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}