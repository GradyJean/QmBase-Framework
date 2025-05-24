# QmBase Logger Starter

提供开箱即用的统一日志切面功能，包括方法入参、返回值、耗时自动打印，支持注解 + YAML 配置双重控制。

---

## ✅ 特性

- `@Log` 注解支持模块名、操作名、日志级别、是否打印参数/返回值
- `LogAspect` 切面统一收敛日志输出
- 支持通过 `application.yml` 控制日志行为
- 支持自定义日志级别（TRACE/DEBUG/INFO/WARN/ERROR）
- 自动装配，开箱即用
- logback 默认配置已包含：
  - 控制台输出
  - 每日滚动的业务日志文件
  - 独立错误日志文件
  - 支持异步输出
  - 业务包 Debug 精细化配置

---

## 🛠️ 使用方式

### 1. 引入依赖

```xml
<dependency>
  <groupId>com.qm.base</groupId>
  <artifactId>qmbase-logger-starter</artifactId>
</dependency>
```

### 2. 使用注解

```java
@Log(module = "订单", action = "创建订单", level = LogLevel.INFO)
public Order create(OrderDTO dto) {
    return orderService.create(dto);
}
```

### 3. YAML 配置项（可选）

```yaml
qmbase:
  logger:
    log-params: true
    log-result: true
    level: INFO
```

---

## 📄 默认日志配置（logback-spring.xml）

- 自动读取 `spring.application.name` 作为文件前缀
- 默认日志目录为 `logs/`
- 生成日志文件示例：

```
logs/order-service.log
logs/order-service.2024-05-10.log
logs/order-service-error.log
```

支持异步控制台输出、文件滚动、ERROR 日志分类，业务包单独设为 DEBUG。

---

## 📦 兼容性

- ✅ 支持 Spring Boot 2.x （通过 spring.factories）
- ✅ 支持 Spring Boot 3.x （通过 AutoConfiguration.imports）
- ✅ 提供 YAML 配置提示（spring-configuration-metadata.json）

---

## 📌 TODO（扩展建议）

- 支持 traceId 链路打印（MDC）
- 支持 marker 打分流日志文件（如 audit.log）
