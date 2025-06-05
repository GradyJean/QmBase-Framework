package com.example.it.service;

import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.manager.CredentialManager;
import com.qm.base.core.model.auth.enums.IdentifierType;
import com.qm.base.shared.id.api.QmId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class credentialManagerImpl implements CredentialManager {
    static final Map<Long, Map<String, AuthUser>> DATA_MAP = new HashMap<>();
    static final Map<String, String> CODE_MAP = new HashMap<>();

    @Override
    public AuthUser findByIdentifier(String identifier, IdentifierType identifierType) {
        if (DATA_MAP.isEmpty()) {
            return null;
        }
        for (Map<String, AuthUser> map : DATA_MAP.values()) {
            AuthUser user = map.get(identifier);
            if (user != null) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean verifyCode(String identifier, String verificationCode, IdentifierType identifierType) {
        String code = CODE_MAP.get(identifier);
        if (code == null) {
            return false;
        }
        return code.equals(verificationCode);
    }


    @Override
    public boolean sendVerifyCode(String identifier, IdentifierType identifierType) {
        // 生成 6 位随机数字验证码
        int code = (int) ((Math.random() * 9 + 1) * 100000); // 保证首位不为 0
        String codeStr = String.valueOf(code);

        // 模拟发送验证码，将验证码存储到 CODE_MAP 中
        CODE_MAP.put(identifier, codeStr);

        // 可选：打印日志用于调试
        System.out.println("Sent verification code to " + identifier + ": " + codeStr);

        return true;
    }

    @Override
    public Boolean resetCredential(Long userId, String newCredential) {
        DATA_MAP.get(userId).values().forEach((v) -> {
            v.setCredential(newCredential);
        });
        return true;
    }


    @Override
    public AuthUser createUser(AuthUser authUser) {
        Long userId = QmId.nextId();
        authUser.setUserId(userId);
        Map<String, AuthUser> identifierMap = new HashMap<>();
        identifierMap.put(authUser.getIdentifier(), authUser);
        DATA_MAP.put(userId, identifierMap);
        return authUser;
    }

    @Override
    public AuthUser findByUserId(Long userId) {
        return DATA_MAP.get(userId).values().stream().findFirst().orElse(null);
    }

    @Override
    public void logoutHandler(Long userId) {
        DATA_MAP.remove(userId);
    }
}
