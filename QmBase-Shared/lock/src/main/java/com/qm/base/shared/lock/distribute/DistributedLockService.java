package com.qm.base.shared.lock.distribute;

import com.qm.base.shared.lock.LockService;

/**
 * 分布式锁服务接口
 * <p>
 * 该接口仅用于表示具有分布式一致性语义的锁实现。
 * 如后续有特定扩展（如续期、跨服务识别），可在此添加默认方法定义。
 */
public interface DistributedLockService extends LockService {
}
