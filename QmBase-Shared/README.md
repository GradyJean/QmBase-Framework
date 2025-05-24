```text
QmBase-Shared/
├── base           ✅ 核心 VO/DTO/Exception/Response
├── infra          ✅ Redis / MyBatis / MQ 抽象
├── event          ✅ 领域事件结构与事件总线接口
├── security       ✅ JWT解析、注解鉴权、上下文持有
├── logger         ✅ 注解日志、AOP切面、日志规范
├── config         ✅ 抽象配置接口（Nacos/Apollo适配）
├── tracing        ✅ TraceId传递、MDC绑定
├── resilience     ✅ 限流、熔断、重试（Sentinel/Resilience4j）
├── lock           ✅ 分布式锁（Redisson/Zookeeper）
├── cache          ✅ 缓存抽象封装（本地 + Redis）
├── id             ✅ ID生成器（雪花、号段、UUID策略）
├── audit          ✅ 操作审计注解 + 持久化接口
├── utils          ✅ 时间、加解密、反射、Bean工具等
```
