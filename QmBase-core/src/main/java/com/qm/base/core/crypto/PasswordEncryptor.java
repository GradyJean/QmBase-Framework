package com.qm.base.core.crypto;

/**
 * 密码加密与验证的通用接口。
 * <p>
 * 该抽象接口允许在不修改调用方代码的前提下替换不同的加密算法实现。
 */
public interface PasswordEncryptor {

    /**
     * 将原始明文密码加密成哈希字符串。
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码哈希
     */
    String encrypt(String rawPassword);

    /**
     * 验证给定的明文密码是否与加密后的密码匹配。
     *
     * @param rawPassword 明文密码
     * @param encryptedPassword 加密后的密码哈希
     * @return 如果匹配则返回 true，否则返回 false
     */
    boolean matches(String rawPassword, String encryptedPassword);
}
