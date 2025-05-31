package com.qm.base.shared.base.utils;

/**
 * 枚举工具类，用于根据 code 值查找对应的枚举实例。
 */
public class EnumUtils {

    /**
     * 根据 code 获取枚举实例
     *
     * @param enumClass 枚举类
     * @param code      code 值
     * @param <E>       枚举类型
     * @return 匹配的枚举项，未匹配返回 null
     */
    public static <E extends Enum<E> & BaseEnum> E fromCode(Class<E> enumClass, String code) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 枚举必须实现该接口，以提供 getCode() 方法
     */
    public interface BaseEnum {
        String getCode();
    }
}
