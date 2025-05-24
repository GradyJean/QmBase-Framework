# QmBase ID Generator Starter

基于 Twitter Snowflake 算法实现的全局唯一 ID 生成器，支持多节点部署、自动装配、YAML 配置注入，适用于微服务架构下的分布式唯一 ID 场景。

---

## ✅ 特性

- 雪花算法实现（64位 long 类型 ID）
- 支持 workerId / datacenterId 参数配置
- 提供默认注入的 `IdGenerator` Bean，可直接使用
- 支持 `application.yml` 自定义配置
- 提供 starter 自动装配，开箱即用
- 支持字符串类型 ID 生成（纯数字）

---

## 🛠️ 使用方式

### 1. 引入依赖

```xml
<dependency>
  <groupId>com.qm.base</groupId>
  <artifactId>qmbase-id-starter</artifactId>
</dependency>
```

### 2. YAML 配置参数

```yaml
qm:
  base:
    id:
      worker-id: 1
      datacenter-id: 0
```

### 3. 注入使用

```java
@Autowired
private IdGenerator idGenerator;

Long id = idGenerator.nextId();
String idStr = idGenerator.nextIdStr();
```

---

## 🔍 示例测试接口（可选）

引入测试模块后访问：

- `GET /test/id` → 返回 Long 类型 ID
- `GET /test/id/str` → 返回 String 类型 ID

---

## 📦 兼容性

- ✅ 支持 Spring Boot 2.x
- ✅ 支持 Spring Boot 3.x
- ✅ 提供 YAML 配置提示（spring-configuration-metadata.json）

---

## 📌 TODO（扩展建议）

- 支持批量预生成/缓存 ID 提高吞吐
- 支持数据库/Redis 作为号段服务的持久化实现
- 支持实例自动注册 workerId（如 ZooKeeper/etcd）
