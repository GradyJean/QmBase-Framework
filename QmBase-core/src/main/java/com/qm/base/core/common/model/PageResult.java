package com.qm.base.core.common.model;

import java.util.List;

/**
 * 分页查询结果封装
 *
 * @param <M> 继承自 BaseModel 的实体类型
 *            <p>
 *            用于统一分页接口返回结构。
 */
public class PageResult<M> {

    /**
     * 当前页数据列表
     */
    private List<M> list;

    /**
     * 数据总条数
     */
    private long total;

    /**
     * 当前页码（从 1 开始）
     */
    private int page;

    /**
     * 每页条数
     */
    private int size;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 是否存在下一页
     */
    private boolean hasNext;

    /**
     * 是否存在上一页
     */
    private boolean hasPrev;

    public PageResult() {
    }

    public PageResult(List<M> list, long total, int page, int size) {
        this.list = list;
        this.total = total;
        this.page = page;
        this.size = size;
        this.pages = size == 0 ? 0 : (int) Math.ceil((double) total / size);
        this.hasNext = page < pages;
        this.hasPrev = page > 1;
    }

    public List<M> getList() {
        return list;
    }

    public void setList(List<M> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrev() {
        return hasPrev;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }
}
