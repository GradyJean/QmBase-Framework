package com.qm.base.core.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 使用 PBKDF2 算法进行密码加密和验证的工具类。
 * 提供生成加密密码和验证密码的方法，适用于用户登录系统的密码安全管理。
 */
public class Pbkdf2PasswordEncryptor implements PasswordEncryptor {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    /**
     * 对明文密码进行加密，返回格式为：salt:hash
     *
     * @param password 明文密码
     * @return 加密后的密码字符串
     */
    public String encrypt(String password) {
        byte[] salt = generateSalt();
        byte[] hash = pbkdf2(password.toCharArray(), salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * 验证密码是否匹配。
     *
     * @param password     明文密码
     * @param encryptedPwd 加密后的密码（格式为 salt:hash）
     * @return 是否匹配
     */
    public boolean matches(String password, String encryptedPwd) {
        String[] parts = encryptedPwd.split(":");
        if (parts.length != 2) {
            return false;
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] expectedHash = Base64.getDecoder().decode(parts[1]);
        byte[] actualHash = pbkdf2(password.toCharArray(), salt);
        if (actualHash.length != expectedHash.length) {
            return false;
        }
        for (int i = 0; i < actualHash.length; i++) {
            if (actualHash[i] != expectedHash[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 生成随机盐值。
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 执行 PBKDF2 算法生成哈希。
     */
    private static byte[] pbkdf2(char[] password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }
}
