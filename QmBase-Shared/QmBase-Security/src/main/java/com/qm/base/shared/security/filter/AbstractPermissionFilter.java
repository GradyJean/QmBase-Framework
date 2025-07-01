package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.casbin.adapter.CasbinPolicyAdapter;
import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.model.DomainEntry;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * 抽象权限过滤器，基于 Casbin 实现通用权限检查能力。
 * 只能作为基类使用，不能直接注册为 Bean。
 * 子类需指定具体 Casbin 模型路径，并实现权限校验业务逻辑。
 */
public abstract class AbstractPermissionFilter implements QmFilter {
    private Enforcer enforcer;
    private final String domain;
    private final PolicyLoader policyLoader;

    /**
     * 构造函数，初始化 Casbin Enforcer 实例。
     * 子类需要实现 getModelPath 方法以提供具体的模型路径。
     */
    public AbstractPermissionFilter(PolicyLoader policyLoader, String domain) {
        // 初始化 Enforcer，加载 Casbin 模型和策略
        this.domain = domain;
        this.policyLoader = policyLoader;
        reloadPolicy();
    }

    /**
     * 判断当前请求是否需要经过权限检查。
     * 如果请求路径在排除列表中，则不执行权限检查。
     *
     * @param request 当前请求对象
     * @return 如果需要执行权限检查，返回 true；否则返回 false
     */
    @Override
    public boolean match(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取当前请求的权限域
        DomainEntry entry = context.getDomainEntry();
        // 先排除路径，再判断是否已授权，确保短路优化与语义清晰
        return !context.isAuthorized() && (domain.equals(entry.getDomain()) || entry.getDomain() == null);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        Object[] params = getRequestParameters(request, context);
        boolean permitted = enforcer.enforce(params);
        if (permitted) {
            context.setAuthorized(true); // 权限已通过，标记跳过后续权限过滤器
        } else {
            throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_NO_PERMISSION);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取过滤器执行顺序，基于 FilterOrder.PERMISSION 的基础上添加偏移量。
     * 子类可以通过实现 getOrderOffset 方法来调整执行顺序。
     *
     * @return 过滤器的执行顺序
     */
    @Override
    public int getOrder() {
        return FilterOrder.CUSTOM_PERMISSION.getOrder() + getOrderOffset();
    }

    /**
     * 获取模型路径，子类需要实现此方法以返回具体的 Casbin 模型文件路径。
     *
     * @return 模型文件路径
     */
    protected abstract String getModelPath();

    /**
     * 获取权限检查的偏移量，用于调整过滤器的执行顺序。
     * 子类可以通过实现此方法来指定不同的执行顺序。
     *
     * @return 权限检查的偏移量
     */
    protected abstract int getOrderOffset();

    /**
     * 获取权限模型中定义的请求参数。
     * 子类根据 Casbin 模型的 request_definition 部分提供相应顺序的参数。
     *
     * @param request 当前请求对象
     * @param context 当前线程上下文中的安全信息
     * @return 参数数组，用于传入 Casbin 的 enforcer.enforce(...) 方法
     */
    protected abstract String[] getRequestParameters(HttpServletRequest request, SecurityContext context);

    /**
     * 重新加载 Casbin 策略。
     * 在策略发生变化时调用此方法以更新 Enforcer 实例。
     */
    protected void reloadPolicy() {
        this.enforcer = new Enforcer(getModelPath(), new CasbinPolicyAdapter(policyLoader, domain));
        // 加载策略文件
        this.enforcer.loadPolicy();
    }

    /**
     * 将类路径资源转换为绝对路径。
     * 用于获取 Casbin 模型文件的绝对路径。
     *
     * @param path 类路径资源的相对路径
     * @return 资源的绝对路径
     */
    public String formPathResource(String path) {
        try {
            return new ClassPathResource(path).getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("无法加载 Casbin 配置文件", e);
        }
    }
}
