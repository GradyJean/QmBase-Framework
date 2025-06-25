# QmBase-Auth 模块

## 简介
QmBase-Auth 模块是 QmBase-Framework 的一部分，主要负责用户认证和授权功能。它提供了多种认证方式的支持，并包含了相关的过滤器、控制器、服务和管理器等组件。

## 功能
- 用户认证：支持多种认证方式（如用户名密码、第三方登录等）。
- 授权管理：处理用户权限的分配和校验。
- 异常处理：统一处理认证和授权过程中的异常。
- 可扩展性：支持自定义认证逻辑和扩展。

## 目录结构
```
QmBase-Auth/
├── config/                # 配置类
│   ├── AuthAutoConfiguration.java
│   ├── AuthProperties.java
├── context/              # 上下文管理
│   ├── AuthContextHolder.java
├── controller/           # 控制器
│   ├── AuthController.java
│   ├── ThirdLoginController.java
├── filter/               # 过滤器
│   ├── AuthHeaderFilter.java
├── handler/              # 异常处理
│   ├── AuthExceptionHandler.java
├── manager/              # 管理器
│   ├── CredentialManager.java
├── model/                # 数据模型
│   ├── request/          # 请求模型
│   │   ├── CredentialRequest.java
│   │   ├── ...
│   ├── vo/               # 值对象
├── provider/             # 提供者
│   ├── DefaultLoginProviderRegistry.java
├── service/              # 服务
│   ├── auth/             # 认证服务
│   ├── user/             # 用户服务
│   ├── verify/           # 验证服务
```

## 主要类说明

### 配置类
- **AuthAutoConfiguration**: 自动配置类，初始化认证相关的组件。
- **AuthProperties**: 配置属性类，用于加载认证相关的配置。

### 控制器
- **AuthController**: 提供用户认证相关的接口。
- **ThirdLoginController**: 提供第三方登录相关的接口。

### 过滤器
- **AuthHeaderFilter**: 处理认证头信息的过滤器。

### 异常处理
- **AuthExceptionHandler**: 统一处理认证和授权过程中的异常。

### 服务
- **AuthService**: 处理用户认证的核心服务。
- **AuthUserService**: 用户相关的服务。
- **VerifyService**: 验证相关的服务。

## 使用方法

### 1. 添加依赖
确保在项目的 `pom.xml` 文件中添加对 QmBase-Auth 模块的依赖：
```xml
<dependency>
    <groupId>com.qm.base</groupId>
    <artifactId>QmBase-Auth</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. 配置
在 `application.yml` 或 `application.properties` 文件中添加认证相关的配置：
```yaml
qm:
  auth:
    enable: true
    token-expiration: 3600
```

### 3. 使用
通过注入相关服务（如 `AuthService`）或使用提供的控制器接口实现认证功能。

## 扩展

### 自定义认证逻辑
1. 实现自定义的认证服务类。
2. 在配置类中替换默认的认证服务。

### 添加新的认证方式
1. 实现新的认证提供者。
2. 注册到 `DefaultLoginProviderRegistry` 中。

## 测试
模块包含单元测试和集成测试，测试代码位于 `src/test/java` 目录下。运行以下命令执行测试：
```bash
mvn test
```

## 贡献
欢迎提交 Issue 或 Pull Request 来改进此模块。

## 许可证
此模块遵循 MIT 许可证。
