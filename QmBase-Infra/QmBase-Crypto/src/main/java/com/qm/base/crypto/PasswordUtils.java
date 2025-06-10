package com.qm.base.crypto;

import com.qm.base.core.crypto.PasswordEncryptor;

/**
 * 密码工具类，提供加密与验证功能。
 * 内部使用 PBKDF2 算法，确保密码安全性。
 */
public class PasswordUtils {

    private static final PasswordEncryptor passwordEncryptor = new Pbkdf2PasswordEncryptor();

    /**
     * 对明文密码进行加密，返回加密后的字符串（包含 salt 与 hash）
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码字符串，格式为 Base64(salt):Base64(hash)
     */
    public static String encode(String rawPassword) {
        return passwordEncryptor.encrypt(rawPassword);
    }

    /**
     * 验证明文密码与加密后字符串是否匹配
     *
     * @param rawPassword       明文密码
     * @param encryptedPassword 加密后的密码字符串
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encryptedPassword) {
        return passwordEncryptor.matches(rawPassword, encryptedPassword);
    }

}
