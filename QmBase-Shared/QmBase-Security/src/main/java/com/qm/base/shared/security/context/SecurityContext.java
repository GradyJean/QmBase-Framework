package com.qm.base.shared.security.context;

import com.qm.base.shared.security.model.DomainEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 安全上下文类，用于存储当前请求的安全相关信息。
 * 包括用户 ID、设备 ID、请求链路追踪 ID、授权状态以及其他扩展属性。
 * <p>
 * 该类可用于在应用程序中传递和共享安全相关信息，支持属性透传到下游服务。
 * </p>
 */
public class SecurityContext {

    /**
     * 当前用户 ID
     */
    private Long userId;
    /**
     * 设备 ID
     */
    private String deviceId;
    /**
     * 是否已授权，默认为 false
     * 在实际业务中可根据需要设置为 true 或 false
     */
    private boolean authorized = false;

    /**
     * 权限域信息
     */
    private DomainEntry domainEntry;
    /**
     * 扩展字段（如角色、租户 ID、部门 ID 等），可按需透传
     */
    private final Map<String, Object> attributes = new HashMap<>();
    /**
     * 需要传递的 Keys
     */
    private final List<String> propagatedKeys = new ArrayList<>();

    /**
     * 添加上下文属性，并根据需要标记为透传字段
     *
     * @param key          属性键名
     * @param value        属性值（可为任意对象）
     * @param isPropagated 是否需要向下游服务透传
     */
    public void addAttribute(String key, Object value, boolean isPropagated) {
        if (isPropagated) {
            propagatedKeys.add(key);
        }
        attributes.put(key, value);
    }

    /**
     * 添加上下文属性，默认不透传
     *
     * @param key   属性键名
     * @param value 属性值（可为任意对象）
     */
    public void addAttribute(String key, Object value) {
        addAttribute(key, value, false);
    }

    /**
     * 获取标记为需要透传的属性集合
     *
     * @return 仅包含已声明为需要透传的属性键值对
     */
    public Map<String, Object> getPropagatedAttributes() {
        Map<String, Object> result = new HashMap<>();
        for (String key : propagatedKeys) {
            if (attributes.containsKey(key)) {
                result.put(key, attributes.get(key));
            }
        }
        return result;
    }

    /**
     * @param propagatedAttributes 设置传递过来的
     */
    public SecurityContext(Long userId, String deviceId, Map<String, Object> propagatedAttributes) {
        this.userId = userId;
        this.deviceId = deviceId;
        attributes.putAll(propagatedAttributes);
    }

    public SecurityContext(Long userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }

    /**
     * 按指定类型获取上下文属性值
     *
     * @param key  属性键名
     * @param type 期望返回的类型类对象
     * @param <T>  返回类型
     * @return 若存在则返回对应类型的值，否则返回 null
     * @throws ClassCastException 若类型不匹配则抛出异常
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, Class<T> type) {
        Object value = attributes.get(key);
        if (value == null) {
            return null;
        }
        if (!type.isInstance(value)) {
            throw new ClassCastException("Attribute [" + key + "] is not of type " + type.getName());
        }
        return (T) value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public DomainEntry getDomainEntry() {
        return domainEntry;
    }

    public void setDomainEntry(DomainEntry domainEntry) {
        this.domainEntry = domainEntry;
    }
}
