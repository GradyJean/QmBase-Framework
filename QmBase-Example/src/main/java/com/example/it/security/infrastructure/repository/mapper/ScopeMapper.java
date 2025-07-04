package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.ScopePO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScopeMapper extends BaseMapper<ScopePO, Long> {
}
