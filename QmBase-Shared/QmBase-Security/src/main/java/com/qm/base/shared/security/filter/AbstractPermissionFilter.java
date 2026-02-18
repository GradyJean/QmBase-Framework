package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.casbin.manager.EnforcerManager;
import com.qm.base.shared.security.casbin.watcher.LocalPolicyWatcher;
import com.qm.base.shared.security.constants.SecurityConstant;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.model.ScopeEntry;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.persist.Watcher;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 抽象权限过滤器，基于 Casbin 实现通用权限检查能力。
 * 只能作为基类使用，不能直接注册为 Bean。
 * 子类需指定具体 Casbin 模型路径，并实现权限校验业务逻辑。
 */
public abstract class AbstractPermissionFilter implements QmFilter, SmartInitializingSingleton {
    private Enforcer enforcer;
    private final String scope;
    private final String modelPath;
    @Autowired
    private EnforcerManager enforcerManager;

    /**
     * 构造函数，初始化 Casbin Enforcer 实例。
     * 子类需要实现 getModelPath 方法以提供具体的模型路径。
     */
    public AbstractPermissionFilter(String scope, String modelPath) {
        // 初始化 Enforcer，加载 Casbin 模型和策略
        this.scope = scope;
        this.modelPath = formPathResource(modelPath);
    }

    /**
     * 构造函数，使用默认权限域和指定的模型路径。
     * 默认权限域为 SecurityConstant.SECURITY_SCOPE_DEFAULT。
     *
     * @param modelPath Casbin 模型文件的路径
     */
    public AbstractPermissionFilter(String modelPath) {
        this(SecurityConstant.SECURITY_SCOPE_DEFAULT, modelPath);
    }

    @Override
    public void afterSingletonsInstantiated() {
        // 这里可以安全查库，所有依赖已就绪
        enforcer = enforcerManager.getEnforcer(scope, modelPath, getWatcher());
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
        ScopeEntry entry = context.getScopeEntry();
        // 先排除路径，再判断是否已授权，确保短路优化与语义清晰
        return !context.isAuthorized()
                && (scope.equals(entry.getScope())
                || SecurityConstant.SECURITY_SCOPE_DEFAULT.equals(entry.getScope()));
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
     * 将类路径资源转换为绝对路径。
     * 用于获取 Casbin 模型文件的绝对路径。
     *
     * @param path 类路径资源的相对路径
     * @return 资源的绝对路径
     */
    private String formPathResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            // JAR 环境下复制到临时文件
            File tempFile = File.createTempFile("casbin_", ".conf");
            tempFile.deleteOnExit();
            Files.copy(resource.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("无法加载 Casbin 配置文件", e);
        }
    }

    /**
     * 获取当前使用的 Watcher 实现，默认返回 LocalPolicyWatcher。
     * 业务可通过子类重写该方法来自定义使用 Redis、Zookeeper 等分布式 Watcher 实现。
     *
     * @return Watcher 实例
     */
    protected Watcher getWatcher() {
        // 返回一个新的 LocalPolicyWatcher 实例，用于监听权限变更事件
        return new LocalPolicyWatcher();
    }
}
