# QmBase Example 模块

> 本模块为 QmBase 框架的业务示例，采用经典 DDD 分层结构，演示用户注册等典型功能如何基于 QmBase 插件体系快速构建。

---

## 🧩 项目结构概览

```
com.example.it
├── application             # 应用层：封装用例逻辑（编排业务流程）
│   └── service
│       └── UserAppService.java
├── domain                  # 领域层：定义核心业务模型
│   ├── model
│   │   ├── User.java
│   │   ├── UserCredential.java
│   │   └── enums
│   │       └── IdentifierType.java
│   └── repository
│       └── UserRepository.java  # 抽象接口
├── infrastructure          # 基础设施层：实现持久化或外部依赖
│   ├── repository
│   │   └── impl
│   │       └── UserRepositoryImpl.java  # 接口实现
│   └── config
│       └── SqliteConfig.java           # 数据源配置
├── interfaces              # 接口层：对外暴露 API / DTO 定义
│   ├── controller
│   │   └── UserController.java
│   └── dto
│       ├── request
│       │   └── RegisterUserRequest.java
│       └── response
│           └── UserVO.java
└── ExampleStarter.java     # 启动类
```

---

## 🚀 快速开始

### ✅ 启动服务

```bash
# 请确保 QmBase 相关模块已 install 到本地
mvn clean install
mvn spring-boot:run
```

### ✅ 示例接口（用户注册）

```
POST /users/register
Content-Type: application/json

{
  "identifier": "test@example.com",
  "credential": "123456",
  "identifierType": "EMAIL"
}
```

---

## 🧱 技术要点

- 使用 **Spring Boot 3.x** + SQLite 作为演示数据源
- 依赖 QmBase 提供的通用能力：
    - ✅ QmBase-Auth：身份认证与令牌逻辑
    - ✅ QmBase-Logger：日志追踪与上下文管理
    - ✅ QmBase-Id：分布式 ID 生成器
- 采用 DDD 架构，支持长期演进与业务复用

---

## 💡 为什么采用 DDD 分层结构？

| 层级         | 责任说明                                |
|--------------|-----------------------------------------|
| `interfaces` | 请求接入、参数校验、返回视图            |
| `application`| 应用用例编排（如注册/登录等）           |
| `domain`     | 核心业务模型、业务逻辑、接口抽象        |
| `infrastructure` | 实现外部依赖（如 DB、Redis）         |

结构清晰，便于测试、演进、插件化集成。

---

## 📎 相关项目

| 模块名            | 功能说明                   |
|------------------|----------------------------|
| `QmBase-Auth`    | 认证服务、令牌发放与校验   |
| `QmBase-Logger`  | 上下文日志追踪与链路ID     |
| `QmBase-Id`      | 分布式 ID 生成器（Snowflake） |
| `QmBase-Lock`    | 分布式锁（支持 Redis / ZK）|
| `QmBase-Cache`   | 本地 + 分布式缓存封装       |

---

## 📮 联系作者

本项目由 `GradyJean` 独立开发，旨在打造企业级 Spring Boot 插件脚手架，欢迎交流与反馈。

GitHub 项目地址：  
[https://github.com/GradyJean/QmBase-Framework](https://github.com/GradyJean/QmBase-Framework)