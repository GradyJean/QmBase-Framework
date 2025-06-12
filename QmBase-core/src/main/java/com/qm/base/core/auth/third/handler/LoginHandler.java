package com.qm.base.core.auth.third.handler;

import com.qm.base.core.auth.exception.AuthException;

import java.util.Map;

/**
 * LoginHandler 接口用于定义第三方登录处理器的统一规范。
 * 该接口的实现类负责处理来自第三方平台的回调请求，并解析出平台用户的唯一标识（如 openId 或 unionId）。
 * 注意：此接口仅处理与第三方的交互，实际生成 AuthToken 的逻辑由上层服务（如 ThirdLoginService）完成。
 */
public interface LoginHandler {

    /**
     * 处理第三方登录回调请求，返回第三方平台用户的唯一标识。
     *
     * @param params 第三方登录回调的原始参数
     * @return 第三方用户的 openId 或 unionId
     * @throws AuthException 处理失败时抛出异常（如参数不全、签名校验失败等）
     */
    String handleLogin(Map<String, String> params) throws AuthException;
}
