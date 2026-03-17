package com.qm.base.core.security.model;

import com.qm.base.core.security.constants.SecurityConstant;

/**
 * SecurityScope 类表示一个域条目。
 * 它包含请求路径、HTTP 方法、域名和操作等信息。
 */
public interface SecurityScope {

    String getResourcePattern();

    String getHttpMethod();

    String getScope();
}
