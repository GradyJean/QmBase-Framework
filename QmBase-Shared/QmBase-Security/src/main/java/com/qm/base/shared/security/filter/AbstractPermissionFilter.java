package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.persist.SecurityAdapter;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.casbin.jcasbin.main.Enforcer;

import java.io.IOException;

/**
 * 抽象权限过滤器，提供基本的权限检查功能。
 * 子类需要实现具体的权限匹配逻辑。
 * 该过滤器使用 Casbin 进行权限控制，
 * 并通过 Enforcer 对象来添加和检查策略。
 * * 注意：此过滤器仅作为权限检查的基础，具体的权限逻辑需要在子类中实现。
 */
public abstract class AbstractPermissionFilter implements QmFilter {
    Enforcer enforcer = new Enforcer("alice", new SecurityAdapter());

    /**
     * 匹配请求路径，添加到 Casbin 策略中。
     * 子类可以重写此方法以实现更复杂的匹配逻辑。
     *
     * @param request 当前请求对象
     * @return 如果匹配成功，返回 true；否则返回 false
     */
    @Override
    public boolean match(HttpServletRequest request) {
        enforcer.addPolicy(request.getServletPath());
        return false;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        // TODO: 实现具体的权限检查逻辑
    }

    @Override
    public int getOrder() {
        return FilterOrder.PERMISSION.getNextOrder();
    }
}
