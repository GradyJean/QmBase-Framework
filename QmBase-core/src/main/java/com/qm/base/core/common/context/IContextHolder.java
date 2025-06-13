package com.qm.base.core.common.context;

public interface IContextHolder<T> {

    /**
     * 设置上下文对象。
     * @param context 上下文内容
     */
    void set(T context);

    /**
     * 获取当前线程的上下文对象。
     * @return 上下文对象
     */
    T get();

    /**
     * 清除当前线程的上下文，防止内存泄漏。
     */
    void clear();
}
