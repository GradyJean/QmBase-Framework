package com.qm.base.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.qm.base.core.common.context.IContextHolder;

/**
 * 通用线程上下文持有器抽象类。
 * 使用 TransmittableThreadLocal 实现上下文的线程隔离与线程池透传，
 * 可支持如用户上下文、租户上下文、链路追踪上下文等多种用途。
 *
 * @param <T> 上下文对象类型
 */
public abstract class AbstractContextHolder<T> implements IContextHolder<T> {

    /**
     * 使用 TransmittableThreadLocal 实现的线程上下文存储器。
     * 支持线程池中自动继承父线程上下文，适用于异步场景。
     */
    private final TransmittableThreadLocal<T> threadLocal = new TransmittableThreadLocal<T>() {
        @Override
        protected T initialValue() {
            return null; // 可在子类中使用时覆盖默认行为
        }
    };

    /**
     * 设置当前线程的上下文对象。
     *
     * @param context 上下文内容
     */
    @Override
    public void set(T context) {
        threadLocal.set(context);
    }

    /**
     * 获取当前线程的上下文对象。
     *
     * @return 当前上下文对象，若未设置则为 null
     */
    @Override
    public T get() {
        return threadLocal.get();
    }

    /**
     * 判断当前线程是否已有上下文对象。
     *
     * @return true 表示当前线程存在上下文
     */
    public boolean hasContext() {
        return threadLocal.get() != null;
    }

    /**
     * 清除当前线程上下文，防止线程复用导致的上下文污染。
     */
    @Override
    public void clear() {
        threadLocal.remove();
    }
}
