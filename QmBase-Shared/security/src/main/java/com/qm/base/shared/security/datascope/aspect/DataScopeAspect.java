package com.qm.base.shared.security.datascope.aspect;

import com.qm.base.shared.security.datascope.annotation.DataScope;
import com.qm.base.shared.security.datascope.core.DataScopeContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据权限注解切面
 * 用于拦截 @DataScope 注解并设置上下文参数
 */
@Aspect
@Order(100)
@Component
public class DataScopeAspect {

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint joinPoint, DataScope dataScope) {
        Map<String, Object> context = new HashMap<>();
        context.put("deptAlias", dataScope.deptAlias());
        context.put("userAlias", dataScope.userAlias());
        context.put("includeSubDept", dataScope.includeSubDept());

        DataScopeContextHolder.set(context);
    }
}