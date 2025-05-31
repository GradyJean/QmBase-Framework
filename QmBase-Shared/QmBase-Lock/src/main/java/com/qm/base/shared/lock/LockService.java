package com.qm.base.shared.lock;

import java.util.function.Supplier;

/**
 * 通用锁服务接口
 * 提供基础加锁与释放能力，同时支持封装执行体（带或不带返回值）
 * 适配多种锁实现（如本地锁、分布式锁）
 * <p>
 * 这是通用锁接口，可被本地和分布式实现共同继承。
 */
public interface LockService {

    /**
     * 尝试获取锁（不阻塞）
     *
     * @param key          锁标识（通常为业务唯一标识）
     * @param value        请求唯一标识（如 UUID、线程 ID，用于防止释放时误删他人锁）
     * @param expireMillis 锁超时时间（毫秒）
     * @return true: 加锁成功；false: 加锁失败
     */
    boolean tryLock(String key, String value, long expireMillis);

    /**
     * 尝试获取锁（阻塞等待）
     *
     * @param key          锁标识
     * @param value        请求唯一标识
     * @param expireMillis 锁超时时间（毫秒）
     * @param waitMillis   最多等待时间（毫秒）
     *                     <p>
     *                     等待期间为自旋或阻塞实现，具体取决于实现类，通常不支持中断。
     * @return true: 加锁成功；false: 加锁失败
     */
    boolean tryLock(String key, String value, long expireMillis, long waitMillis);

    /**
     * 释放锁
     *
     * @param key   锁标识
     * @param value 请求唯一标识（需与加锁时一致）
     * @return true: 释放成功；false: 非当前线程或释放失败（可能锁已过期、被他人占有或实现失败）
     *         释放失败不会抛异常
     */
    boolean releaseLock(String key, String value);

    /**
     * 加锁并执行任务（无返回值）
     *
     * @param key          锁标识
     * @param task         要执行的任务
     * @param expireMillis 锁超时时间
     */
    void execute(String key, Runnable task, long expireMillis);

    /**
     * 加锁并执行任务（带返回值）
     *
     * @param key          锁标识
     * @param task         要执行的任务（返回值）
     * @param expireMillis 锁超时时间
     * @param <T>          返回值类型
     * @return 任务执行结果
     * <p>
     * 若 task 抛出异常，锁是否自动释放取决于具体实现，建议用户在任务内做好异常捕获。
     */
    <T> T execute(String key, Supplier<T> task, long expireMillis);

    /**
     * 加锁并执行任务（自定义请求唯一值与超时时间）
     *
     * @param key          锁标识
     * @param value        请求唯一标识
     * @param task         要执行的任务
     * @param expireMillis 锁自动释放时间
     * @param waitMillis   最多等待时间
     * @param <T>          返回值类型
     * @return 任务执行结果
     */
    <T> T execute(String key, String value, Supplier<T> task, long expireMillis, long waitMillis);
}