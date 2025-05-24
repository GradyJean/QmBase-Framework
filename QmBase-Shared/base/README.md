

# QmBase Shared Base 模块

该模块为 QmBase 框架中所有共享组件的基础核心，提供统一响应结构、基础实体、通用错误码定义等通用能力，供所有上层模块与业务模块复用。

---

## ✅ 功能模块说明

- `Result<T>`：标准统一响应结构，封装业务成功/失败逻辑
- `ResultCode`：通用返回码定义（SUCCESS、FAIL、PARAM_ERROR 等）
- `ErrorCode`：可扩展的接口级错误码体系
- `BaseEntity`：基础抽象实体类，预定义 id、创建时间、更新时间等字段
- `IdGenerator` 接口（依赖于 `qmbase-id-starter` 实现）
- 通用常量与工具类（后续补充）

---

## 📄 示例：统一响应结构

```java
return Result.SUCCESS(data); // 成功返回
return Result.FAIL("10001", "参数错误"); // 失败返回
```

响应结构示例：

```json
{
  "success": true,
  "code": "0",
  "message": "Success",
  "data": { ... }
}
```

---

## 🧩 设计理念

- 所有模块应依赖 shared.base 而非自行定义基础结构
- 保持结构轻量，避免依赖具体业务或技术栈
- 面向通用能力抽象，支持跨模块/跨服务共享

---

## 📦 注意事项

- 该模块不应依赖 Spring Boot 或其他容器，仅提供纯 Java 能力
- 上层模块通过 `shared.base` 提供的统一结构与响应协议保持一致性

---

## 📌 TODO（扩展建议）

- 增加国际化支持（错误码多语言 message）
- 提供更丰富的字段级错误提示结构（如字段级校验异常）