# QmBase-Payment

`QmBase-Payment` 是面向网站扫码支付场景的统一支付抽象，当前只覆盖：

- 微信 Native 扫码支付
- 支付宝预下单扫码支付
- 统一回调接收、验签、解析

当前不包含：

- 业务订单管理
- 退款
- H5 / APP / 小程序支付
- 发票

## 模块结构

- `QmBase-Payment`
  - 核心抽象
  - 统一回调 controller
  - 统一 schema / enum / service / provider SPI
- `QmBase-Payment-Wechat`
  - 微信支付 provider
  - 基于 `wechatpay-java`
- `QmBase-Payment-Alipay`
  - 支付宝支付 provider
  - 基于 `alipay-sdk-java-v3`

## 已实现能力

- 创建支付单
- 查询支付单状态
- 关闭未支付订单
- 接收并解析支付回调

## 核心接口

### `PaymentService`

统一支付入口：

```java
PayCreateSchema.Output create(PayCreateSchema.Input request);

PayQuerySchema.Output query(PayQuerySchema.Input request);

PayCloseSchema.Output close(PayCloseSchema.Input request);

PayNotifySchema.Result parseNotify(PaymentProviderType provider, PayNotifySchema.Input request);
```

### `PaymentNotifyHandler`

业务侧只需要实现这个接口：

```java
public interface PaymentNotifyHandler {

    String handle(PayNotifySchema.Result notifyResult);
}
```

说明：

- `payment` 模块负责验签和解析
- 业务侧负责入库、更新订单、发消息等动作
- `handle(...)` 返回值会直接作为渠道回调响应体
- 如果业务处理失败，直接抛异常即可，Spring 会返回 `500`，渠道会按自身策略重试

## 回调链路

统一回调地址：

```text
/payment/callback/{provider}
```

示例：

```text
/payment/callback/wechat
/payment/callback/alipay
```

处理流程：

1. `PaymentNotifyController` 接收回调
2. `PaymentNotifyService` 组织回调处理流程
3. `PaymentService.parseNotify(...)` 路由到具体 provider
4. provider 完成验签和解析
5. 交给 `PaymentNotifyHandler.handle(...)`

## Schema 说明

### 创建支付

`PayCreateSchema.Input`

- `provider`
- `merchantOrderNo`
- `subject`
- `amount`
- `expireTime`
- `attach`

`PayCreateSchema.Output`

- `provider`
- `merchantOrderNo`
- `channelTradeNo`
- `status`
- `codeUrl`
- `expireTime`

### 查询支付

`PayQuerySchema.Input`

- `provider`
- `merchantOrderNo`

`PayQuerySchema.Output`

- `provider`
- `merchantOrderNo`
- `channelTradeNo`
- `status`
- `amount`
- `paidTime`
- `rawStatus`

### 关闭支付

`PayCloseSchema.Input`

- `provider`
- `merchantOrderNo`

`PayCloseSchema.Output`

- `provider`
- `merchantOrderNo`
- `success`
- `status`
- `rawStatus`

### 支付回调

`PayNotifySchema.Input`

- `headers`
- `parameters`
- `body`

说明：

- `parameters` 统一承接 servlet parameter map
- 微信主要使用 `headers + body`
- 支付宝主要使用 `parameters`

`PayNotifySchema.Result`

- `provider`
- `merchantOrderNo`
- `channelTradeNo`
- `status`
- `amount`
- `paidTime`
- `rawPayload`

## Maven 依赖

核心模块：

```xml
<dependency>
    <groupId>com.qm.base.payment</groupId>
    <artifactId>QmBase-Payment</artifactId>
</dependency>
```

微信支付：

```xml
<dependency>
    <groupId>com.qm.base.payment</groupId>
    <artifactId>QmBase-Payment-Wechat</artifactId>
</dependency>
```

支付宝支付：

```xml
<dependency>
    <groupId>com.qm.base.payment</groupId>
    <artifactId>QmBase-Payment-Alipay</artifactId>
</dependency>
```

## 配置示例

### 微信支付

```yaml
qm:
  payment:
    wechat:
      enabled: true
      app-id: wx1234567890abcdef
      merchant-id: 1234567890
      merchant-serial-number: 1234567890ABCDEF1234567890ABCDEF12345678
      private-key-path: /data/cert/wechat/apiclient_key.pem
      api-v3-key: 0123456789abcdef0123456789abcdef
      notify-url: https://your-domain.com/payment/callback/wechat
```

当前实现使用：

- `RSAAutoCertificateConfig`
- SDK: `com.github.wechatpay-apiv3:wechatpay-java`

### 支付宝

```yaml
qm:
  payment:
    alipay:
      enabled: true
      server-url: https://openapi.alipay.com
      app-id: 2021000000000000
      private-key-path: /data/cert/alipay/app_private_key.pem
      alipay-public-key-path: /data/cert/alipay/alipay_public_key.pem
      charset: UTF-8
      sign-type: RSA2
      notify-url: https://your-domain.com/payment/callback/alipay
```

当前实现使用：

- SDK: `com.alipay.sdk:alipay-sdk-java-v3`

## 使用示例

### 下单

```java
PayCreateSchema.Input input = new PayCreateSchema.Input();
input.setProvider(PaymentProviderType.WECHAT);
input.setMerchantOrderNo("P202605050001");
input.setSubject("测试订单");
input.setAmount(100L);
input.setExpireTime(LocalDateTime.now().plusMinutes(30));
input.setAttach("biz=demo");

PayCreateSchema.Output output = paymentService.create(input);
String codeUrl = output.getCodeUrl();
```

### 查询

```java
PayQuerySchema.Input input = new PayQuerySchema.Input();
input.setProvider(PaymentProviderType.ALIPAY);
input.setMerchantOrderNo("P202605050001");

PayQuerySchema.Output output = paymentService.query(input);
```

## 业务回调示例

```java
@Service
public class DemoPaymentNotifyHandler implements PaymentNotifyHandler {

    @Override
    public String handle(PayNotifySchema.Result notifyResult) {
        // 这里执行入库、更新订单、发消息等业务逻辑

        if (notifyResult.getProvider() == PaymentProviderType.ALIPAY) {
            return "success";
        }
        return "success";
    }
}
```

建议：

- 业务逻辑需要异步化时，在 `handle(...)` 内部自行投递 MQ 或事件
- 不要在 `payment` 模块里承载业务订单逻辑

## 注意事项

- 当前统一抽象只面向扫码支付
- 金额单位统一为分
- 回调失败时直接抛异常，让渠道重试
- 支付宝当前使用 `v3` SDK，不再使用 `v2` 的 `DefaultAlipayClient`
- 微信当前使用官方 `API v3` Java SDK
