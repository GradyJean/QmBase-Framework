package com.qm.base.core.auth.third.provider;

import com.qm.base.core.auth.third.config.LoginProperties;
import com.qm.base.core.auth.third.handler.LoginHandler;

/**
 * 第三方登录平台提供者接口。
 * <p>
 * 所有第三方登录平台（如微信、支付宝、抖音、小红书等）都应实现此接口，
 * 以声明自身的基本信息（平台名称、平台编码）、授权跳转地址生成规则（getLoginUrl），
 * 以及登录回调处理逻辑（LoginHandler）。
 * <p>
 * 本接口是统一抽象的入口，供控制器/服务层根据 platform 代码动态获取并调用对应实现，
 * 从而实现扩展性强、解耦良好的第三方登录支持体系。
 */
public interface LoginProvider {

    /**
     * 获取平台显示名称（用于前端展示）。
     *
     * @return 平台名称（如："微信"）
     */
    String getName();

    /**
     * 获取平台授权跳转地址（如扫码登录地址）。
     *
     * @param state 请存放到第三方登录的 state 字段
     * @return 第三方登录授权地址
     */
    String getLoginUrl(String state);

    /**
     * 获取平台唯一标识编码（如："wechat"、"alipay"）。
     *
     * @return 平台编码
     */
    String getPlatform();

    /**
     * 获取当前平台的登录处理器。
     * <p>
     * 用于处理平台回调请求并提取 openId、unionId 等用户唯一标识。
     * 每个平台应返回其专属的 LoginHandler 实现，封装各自参数结构与校验逻辑。
     *
     * @return 登录处理器
     */
    LoginHandler getLoginHandler();

    /**
     * 获取当前平台对应的配置属性。
     * <p>
     * 包含 clientId、clientSecret、redirectUri、enabled 等平台运行所需参数。
     * 通常由配置文件加载并注入，可用于构建登录地址、判断是否启用平台等场景。
     *
     * @return 平台配置属性对象
     */
    LoginProperties getLoginProperties();
}
