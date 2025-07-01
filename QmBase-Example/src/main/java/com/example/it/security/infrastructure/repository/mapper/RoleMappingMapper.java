package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.RoleMappingPO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMappingMapper extends BaseMapper<RoleMappingPO, Long> {
}
