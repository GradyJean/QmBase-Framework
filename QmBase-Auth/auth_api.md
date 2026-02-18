# QmBase-Auth 模块 API 接口文档

本文档描述了 `QmBase-Auth` 模块中 `AuthController` 和 `ThirdLoginController` 两个控制器的所有接口详情。

---

## 目录

- [1. AuthController（用户认证接口）](#1-authcontroller用户认证接口)
- [2. ThirdLoginController（第三方登录接口）](#2-thirdlogincontroller第三方登录接口)
- [3. 通用数据模型](#3-通用数据模型)

---

## 1. AuthController（用户认证接口）

**基础路径**: `/auth`

### 1.1 用户登录

**接口路径**: `POST /auth/login`

**功能描述**: 用户登录接口，支持多种登录方式（用户名密码、验证码登录等）。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| identifier | String | 是 | 用户登录标识（手机号/邮箱/用户名） |
| credential | String | 是 | 凭证（密码或验证码） |
| verifyCodeLogin | Boolean | 否 | 是否为验证码登录，默认 false |
| identifierType | IdentifierType | 否 | 标识类型（PHONE_NUMBER/EMAIL 等） |

**请求示例**:
```json
{
  "identifier": "13800138000",
  "credential": "123456",
  "verifyCodeLogin": false
}
```

**返回类型**: `Result<AuthToken>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": {
    "accessToken": {
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
      "expireAt": 1708243200000
    },
    "refreshToken": {
      "token": "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4...",
      "expireAt": 1708329600000
    }
  }
}
```

---

### 1.2 用户注册

**接口路径**: `POST /auth/register`

**功能描述**: 新用户注册接口。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| identifier | String | 是 | 用户标识（手机号/邮箱） |
| credential | String | 是 | 密码 |
| verifyCode | String | 否 | 验证码（如需要） |
| identifierType | IdentifierType | 否 | 标识类型 |

**请求示例**:
```json
{
  "identifier": "user@example.com",
  "credential": "password123",
  "verifyCode": "123456"
}
```

**返回类型**: `Result<Boolean>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": true
}
```

---

### 1.3 用户标识是否存在

**接口路径**: `GET /auth/identifier/exists`

**功能描述**: 检查指定用户标识（手机号/邮箱）是否已注册。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| identifier | String | 是 | 用户标识（手机号/邮箱） |

**请求示例**: `GET /auth/identifier/exists?identifier=user@example.com`

**返回类型**: `Result<Boolean>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": false
}
```

---

### 1.4 发送验证码

**接口路径**: `POST /auth/verifyCode/send`

**功能描述**: 发送验证码到指定手机号或邮箱。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| identifier | String | 是 | 手机号或邮箱 |
| identifierType | IdentifierType | 否 | 标识类型（可自动识别） |

**请求示例**:
```json
{
  "identifier": "13800138000"
}
```

**返回类型**: `Result<Boolean>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": true
}
```

---

### 1.5 刷新 Token

**接口路径**: `POST /auth/token/refresh`

**功能描述**: 使用 refreshToken 获取新的 accessToken 和 refreshToken。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| token | String | 是 | refreshToken 字符串 |

**请求示例**:
```json
{
  "token": "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4..."
}
```

**返回类型**: `Result<AuthToken>`

**返回示例**: 同 1.1 登录接口

---

### 1.6 密码重置

**接口路径**: `POST /auth/credential/reset`

**功能描述**: 重置用户密码（通过验证码验证）。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| identifier | String | 是 | 用户标识（手机号/邮箱） |
| credential | String | 是 | 新密码 |
| verifyCode | String | 是 | 验证码 |
| identifierType | IdentifierType | 否 | 标识类型 |

**请求示例**:
```json
{
  "identifier": "13800138000",
  "credential": "newPassword123",
  "verifyCode": "123456"
}
```

**返回类型**: `Result<Boolean>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": true
}
```

---

### 1.7 用户登出

**接口路径**: `POST /auth/logout`

**功能描述**: 用户登出，使当前 Token 失效。

**请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| token | String | 是 | 当前 accessToken |

**请求示例**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**返回类型**: `Result<Boolean>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": true
}
```

---

## 2. ThirdLoginController（第三方登录接口）

**基础路径**: `/auth/third`

### 2.1 生成第三方登录授权地址

**接口路径**: `GET /auth/third/{platform}/url`

**功能描述**: 生成指定第三方平台的登录授权跳转地址（用于网页扫码或跳转授权）。

**路径参数**:

| 参数名 | 类型 | 说明 |
|--------|------|------|
| platform | String | 第三方平台标识（wechat/alipay/douyin 等） |

**请求示例**: `GET /auth/third/wechat/url`

**返回类型**: `Result<String>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": "https://open.weixin.qq.com/connect/qrconnect?appid=..."
}
```

---

### 2.2 获取第三方平台信息

**接口路径**: `GET /auth/third/{platform}/info`

**功能描述**: 获取指定第三方平台的详细信息。

**路径参数**:

| 参数名 | 类型 | 说明 |
|--------|------|------|
| platform | String | 第三方平台标识 |

**请求示例**: `GET /auth/third/wechat/info`

**返回类型**: `Result<PlatformInfo>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": {
    "platform": "wechat",
    "info": {
      "appId": "wx1234567890",
      "scope": "snsapi_login"
    }
  }
}
```

---

### 2.3 第三方登录回调

**接口路径**: `GET|POST /auth/third/{platform}/login`

**功能描述**: 处理第三方平台授权回调，用户扫码授权后跳转至此接口，换取系统 Token。

**路径参数**:

| 参数名 | 类型 | 说明 |
|--------|------|------|
| platform | String | 第三方平台标识 |

**请求参数**: 由第三方平台回调参数决定（如 code、state 等），通过 HttpServletRequest 传递。

**请求示例**: `GET /auth/third/wechat/login?code=AUTH_CODE&state=STATE_VALUE`

**返回类型**: `Result<AuthToken>`

**返回示例**: 同 1.1 登录接口

---

### 2.4 获取支持的第三方平台列表

**接口路径**: `GET /auth/third/platforms`

**功能描述**: 获取系统支持的所有第三方登录平台列表（用于前端渲染登录入口）。

**请求参数**: 无

**请求示例**: `GET /auth/third/platforms`

**返回类型**: `Result<List<Platform>>`

**返回示例**:
```json
{
  "success": true,
  "code": "200",
  "message": "成功",
  "data": [
    {
      "name": "微信",
      "platform": "wechat"
    },
    {
      "name": "支付宝",
      "platform": "alipay"
    },
    {
      "name": "QQ",
      "platform": "qq"
    }
  ]
}
```

---

## 3. 通用数据模型

### 3.1 Result<T> - 通用响应包装

| 字段 | 类型 | 说明 |
|------|------|------|
| success | Boolean | 请求是否成功 |
| code | String | 业务状态码 |
| message | String | 提示信息 |
| data | T | 实际数据 |

### 3.2 AuthToken - 认证令牌

| 字段 | 类型 | 说明 |
|------|------|------|
| accessToken | Token | 访问令牌 |
| refreshToken | Token | 刷新令牌 |

### 3.3 Token - 令牌详情

| 字段 | 类型 | 说明 |
|------|------|------|
| token | String | Token 字符串 |
| expireAt | Long | 过期时间（毫秒时间戳） |

### 3.4 Platform - 第三方平台信息

| 字段 | 类型 | 说明 |
|------|------|------|
| name | String | 平台名称（如：微信） |
| platform | String | 平台编码（如：wechat） |

### 3.5 PlatformInfo - 平台详细信息

| 字段 | 类型 | 说明 |
|------|------|------|
| platform | String | 平台编码 |
| info | Map<String, String> | 平台扩展信息（如 appId、scope 等） |

### 3.6 IdentifierType - 标识类型枚举

| 值 | 说明 |
|----|------|
| PHONE_NUMBER | 手机号 |
| EMAIL | 邮箱 |
| WECHAT | 微信 |
| QQ | QQ |
| ALIPAY | 支付宝 |
| GITHUB | GitHub |
| GOOGLE | Google |
| FACEBOOK | Facebook |
| DING_TALK | 钉钉 |

---

## 附录

### 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录/Token 失效 |
| 403 | 无权限 |
| 500 | 服务器内部错误 |

### 注意事项

1. 所有请求的 `Content-Type` 应为 `application/json`（除第三方回调外）
2. Token 相关接口需要在请求头中携带有效的 accessToken
3. 第三方登录回调接口由外部平台跳转，参数格式因平台而异
