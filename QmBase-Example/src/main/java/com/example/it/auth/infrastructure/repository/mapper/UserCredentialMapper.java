package com.example.it.auth.infrastructure.repository.mapper;


import com.example.it.auth.infrastructure.repository.po.UserCredentialPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCredentialMapper {
    UserCredentialPo getByIdentifier(String identifier);

    int insert(UserCredentialPo userCredentialPo);

    int updateCredential(String userId, String newCredential);
}
