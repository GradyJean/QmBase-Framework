package com.example.it.auth.infrastructure.provider;

import com.example.it.auth.infrastructure.repository.mapper.UserCredentialMapper;
import com.example.it.auth.infrastructure.repository.po.UserCredentialPo;
import com.example.it.common.exception.AssertUtil;
import com.example.it.common.exception.BusinessError;
import com.example.it.common.exception.BusinessException;
import com.example.it.core.user.model.BasicUserInfo;
import com.example.it.core.user.profile.UserProfileFetcher;
import com.example.it.core.user.profile.UserProfileSaver;
import com.qm.base.auth.model.vo.AuthUser;
import com.qm.base.auth.service.user.AuthUserService;
import com.qm.base.auth.service.verify.VerifyService;
import com.qm.base.core.auth.enums.IdentifierType;
import com.qm.base.core.auth.model.AuthToken;
import com.qm.base.core.auth.token.TokenService;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.api.QmCacheManager;
import com.qm.base.shared.id.api.QmId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 示例认证服务提供者实现类，提供用户认证、验证码验证和令牌管理等功能。
 * 该类实现了 AuthUserService、VerifyService 和 TokenService 接口。
 */
@Component
public class ExampleAuthServiceProvider implements AuthUserService, VerifyService, TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleAuthServiceProvider.class);
    private final UserProfileSaver userProfileSaver;
    private final UserCredentialMapper userCredentialMapper;
    private final UserProfileFetcher userProfileFetcher;
    private final QmCacheManager qmCacheManager;
    // 缓存命名空间
    private final static String CACHE_NAME_SPACE_TOKEN = "authToken";
    private final static String CACHE_NAME_SPACE_VERIFY_CODE = "verifyCode";
    private final static String CACHE_NAME_SPACE_VERIFY_CODE_INTERVAL = "verifyCode_interval";

    public ExampleAuthServiceProvider(UserProfileSaver userProfileSaver,
                                      UserCredentialMapper userCredentialMapper,
                                      UserProfileFetcher userProfileFetcher,
                                      QmCacheManager qmCacheManager) {
        this.userProfileSaver = userProfileSaver;
        this.userCredentialMapper = userCredentialMapper;
        this.userProfileFetcher = userProfileFetcher;
        this.qmCacheManager = qmCacheManager;
    }

    // user-------------
    @Override
    public AuthUser findByIdentifier(String identifier) {
        UserCredentialPo userCredentialPo = userCredentialMapper.getByIdentifier(identifier);
        if (userCredentialPo == null) {
            return null;
        }
        BasicUserInfo basicUserInfo = userProfileFetcher.selectById(userCredentialPo.getUserId());
        if (basicUserInfo == null) {
            return null;
        }
        AuthUser authUser = new AuthUser();
        authUser.setIdentifier(userCredentialPo.getIdentifier());
        authUser.setCredential(userCredentialPo.getCredential());
        authUser.setUserId(userCredentialPo.getUserId());
        authUser.setIdentifierType(IdentifierType.valueOf(userCredentialPo.getIdentifierType()));
        authUser.setEnabled(basicUserInfo.getEnabled());
        return authUser;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public AuthUser createUser(AuthUser authUser) {
        BasicUserInfo basicUserInfo = new BasicUserInfo();
        basicUserInfo.setNickname("MoMo");
        String userId = userProfileSaver.create(basicUserInfo);
        LocalDateTime now = LocalDateTime.now();
        authUser.setUserId(userId);
        UserCredentialPo userCredentialPo = new UserCredentialPo();
        userCredentialPo.setId(QmId.nextId());
        userCredentialPo.setUserId(userId);
        userCredentialPo.setCredential(authUser.getCredential());
        userCredentialPo.setIdentifier(authUser.getIdentifier());
        userCredentialPo.setIdentifierType(authUser.getIdentifierType().name());
        userCredentialPo.setEnabled(true);
        userCredentialPo.setDeleted(false);
        userCredentialPo.setCreatedAt(now);
        userCredentialPo.setUpdatedAt(now);
        userCredentialPo.setCreatedBy(userId);
        userCredentialPo.setUpdatedBy(userId);
        AssertUtil.INSTANCE.isTrue(userCredentialMapper.insert(userCredentialPo) == 1, BusinessError.USER_INSERT_ERROR);
        authUser.setEnabled(true);
        return authUser;
    }

    @Override
    public Boolean updateCredential(String userId, String newCredential) {
        return userCredentialMapper.updateCredential(userId, newCredential) > 0;
    }

    // token---------------
    @Override
    public AuthToken findAuthTokenByUserId(String userId, String deviceId) {
        QmCache cache = getQmCache(CACHE_NAME_SPACE_TOKEN);
        return cache.get(generateTokenKey(userId, deviceId));
    }

    /**
     * 保存用户的认证令牌信息到缓存中
     *
     * @param userId    用户 ID
     * @param deviceId  设备 ID
     * @param authToken 认证令牌对象
     */
    @Override
    public void saveAuthToken(String userId, String deviceId, AuthToken authToken) {
        QmCache cache = getQmCache(CACHE_NAME_SPACE_TOKEN);
        Long expire = authToken.getRefreshToken().getExpireAt();
        // 计算 RefreshToken 距当前时间的剩余有效期（单位：秒），确保最小为 1 秒
        long ttlInSeconds = Math.max(Duration.between(Instant.now(), Instant.ofEpochMilli(expire)).getSeconds(), 1L);
        cache.put(generateTokenKey(userId, deviceId), authToken, ttlInSeconds);
    }

    @Override
    public void revokeToken(String userId, String deviceId) {
        QmCache cache = getQmCache(CACHE_NAME_SPACE_TOKEN);
        cache.evict(generateTokenKey(userId, deviceId));
    }

    private String generateTokenKey(String userId, String deviceId) {
        return String.format("%s:%s", userId, deviceId);
    }

    /**
     * 生成 6 位验证码
     * 100000 ~ 999999 保证是 6 位数，首位不为 0
     *
     * @return 验证码
     */
    private String generate6DigitCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private QmCache getQmCache(String nameSpace) {
        return qmCacheManager.getCache(nameSpace);
    }

    //----------验证码处理---------------------
    @Override
    public boolean verifyCode(String identifier, String verifyCode, IdentifierType identifierType) {
        QmCache cache = getQmCache(CACHE_NAME_SPACE_VERIFY_CODE);
        String code = cache.get(identifier);
        return StringUtils.isNotBlank(code) && code.equals(verifyCode);
    }

    @Override
    public boolean generateVerifyCode(String identifier, IdentifierType identifierType) {
        String code = generate6DigitCode();
        // 缓存验证码
        QmCache verifyCodeCache = getQmCache(CACHE_NAME_SPACE_VERIFY_CODE);
        verifyCodeCache.put(identifier, code, 5 * 60);
        // 增加 60 秒计时防刷验证码
        QmCache verifyCodeIntervalCache = getQmCache(CACHE_NAME_SPACE_VERIFY_CODE_INTERVAL);
        verifyCodeIntervalCache.put(identifier, code, 60);
        LOGGER.info("identifier:{},identifierType:{},Cached verify code: {}", identifier, identifierType.name(), code);
        return true;
    }

    @Override
    public boolean isSendIntervalAllowed(String identifier, IdentifierType identifierType) {
        QmCache verifyCodeIntervalCache = getQmCache(CACHE_NAME_SPACE_VERIFY_CODE_INTERVAL);
        return verifyCodeIntervalCache.get(identifier) == null;
    }

    @Override
    public boolean revokeVerifyCode(String identifier, IdentifierType identifierType) {
        QmCache verifyCodeCache = getQmCache(CACHE_NAME_SPACE_VERIFY_CODE);
        verifyCodeCache.evict(identifier);
        return true;
    }
}
