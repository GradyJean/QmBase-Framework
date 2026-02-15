package com.example.it.user.infrastructure.adapter;

import com.example.it.common.exception.AssertUtil;
import com.example.it.common.exception.BusinessError;
import com.example.it.core.user.model.BasicUserInfo;
import com.example.it.core.user.profile.UserProfileFetcher;
import com.example.it.core.user.profile.UserProfileSaver;
import com.example.it.core.user.profile.UserProfileUpdater;
import com.example.it.core.user.profile.UserProfileVerifier;
import com.example.it.user.application.assembler.UserProfileAssembler;
import com.example.it.user.infrastructure.repository.mapper.UserProfileMapper;
import com.example.it.user.infrastructure.repository.po.UserProfilePO;
import com.qm.base.shared.id.api.QmId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserProfilePortAdapter implements UserProfileFetcher, UserProfileSaver, UserProfileUpdater, UserProfileVerifier {
    private final UserProfileMapper userProfileMapper;

    public UserProfilePortAdapter(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public String create(BasicUserInfo userInfo) {
        UserProfilePO po = UserProfileAssembler.assemblePOFromBasic(userInfo);
        String id = QmId.nextId();
        LocalDateTime localDateTime = LocalDateTime.now();
        po.setId(id);
        po.setCreatedAt(localDateTime);
        po.setUpdatedAt(localDateTime);
        po.setCreatedBy(id);
        po.setUpdatedBy(id);
        po.setEnabled(true);
        po.setDeleted(false);
        AssertUtil.INSTANCE.isTrue(userProfileMapper.insert(po) == 1, BusinessError.USER_INSERT_ERROR);
        return id;
    }

    @Override
    public BasicUserInfo selectById(String id) {
        return UserProfileAssembler.assembleBasicFromPO(userProfileMapper.selectById(id));
    }
}
