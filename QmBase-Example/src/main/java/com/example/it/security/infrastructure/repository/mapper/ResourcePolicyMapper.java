package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.ResourcePolicyPO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourcePolicyMapper extends BaseMapper<ResourcePolicyPO, Long> {
    List<ResourcePolicyPO> listByDomain(String domain);
}
