package com.qm.base.shared.lock.local;

import com.qm.base.shared.lock.LockService;
/**
 * 本地锁服务接口
 * 可以在此接口添加注释，说明它仅适用于单机环境，作为 `LockService` 的轻量实现。
 * 也可以考虑是否需要为本地锁额外扩展专属方法，如可重入检测、线程绑定状态等。
 */
public interface LocalLockService extends LockService {
}
