# QmBase-Notifier

`QmBase-Notifier` 是统一通知发送抽象，当前只做核心能力，不绑定具体厂商。

当前包含：

- 统一通知发送接口
- provider SPI
- 默认 `log` provider
- Spring Boot 自动装配

当前不包含：

- 短信厂商实现
- 邮件厂商实现
- 飞书 / 钉钉 / 企业微信 webhook 实现
- 模板持久化
- 发送记录入库

## 核心接口

### `NotifierService`

```java
NotifySendSchema.Result send(NotifySendSchema.Input request);
```

### `NotifyProvider`

```java
public interface NotifyProvider {

    String getProvider();

    String getName();

    NotifySendSchema.Result send(NotifySendSchema.Input request);
}
```

## Schema

`NotifySendSchema.Input`

- `provider`
- `channel`
- `bizKey`
- `subject`
- `content`
- `receivers`

`NotifySendSchema.Result`

- `provider`
- `channel`
- `success`
- `requestId`
- `messageId`
- `code`
- `message`

## 通道枚举

`NotifyChannelType`

- `SMS`
- `EMAIL`
- `WEBHOOK`

## 默认 provider

框架内置了一个默认日志 provider：

- provider 编码：`log`
- 类：`LogNotifyProvider`

作用：

- 本地开发调试
- 尚未接入真实厂商前的兜底发送
- 验证调用链路

## 可选 SPI

如果业务需要在发送成功后做落库、审计或别的动作，可以实现：

```java
public interface NotifySendHandler {

    void handle(NotifySendSchema.Input request, NotifySendSchema.Result result);
}
```

如果不需要，这个 SPI 可以不实现。

## 使用示例

```java
NotifySendSchema.Input input = new NotifySendSchema.Input();
input.setProvider("log");
input.setChannel(NotifyChannelType.EMAIL);
input.setBizKey("register-success");
input.setSubject("注册成功");
input.setContent("欢迎加入系统");
input.setReceivers(List.of("demo@example.com"));

NotifySendSchema.Result output = notifierService.send(input);
```

## 后续扩展建议

后续如果要接真实厂商，建议按 provider 拆实现，例如：

- `aliyun-sms`
- `tencent-sms`
- `smtp`
- `feishu-webhook`
- `dingtalk-webhook`

做法就是新增 `NotifyProvider` 实现，不需要改核心模块。
