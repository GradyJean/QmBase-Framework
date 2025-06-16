package com.qm.base.shared.security.filter;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import io.sapl.api.pdp.AuthorizationDecision;
import io.sapl.api.pdp.AuthorizationSubscription;
import io.sapl.api.pdp.PolicyDecisionPoint;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.sapl.api.pdp.Decision.PERMIT;

@Component
public class AuthorizationFilter implements QmFilter {


    @Override
    public boolean match(HttpServletRequest request) {
        return true;
    }


    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain)
            throws IOException, ServletException {

        AuthorizationSubscription sub = buildSubscriptionFromRequest(request);
//        AuthorizationDecision decision = pdp.decide(sub).blockFirst();

//        if (decision != null && PERMIT.equals(decision.getDecision())) {
//            chain.doFilter(request, response);
//        } else {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Access Denied");
//        }
    }

    private AuthorizationSubscription buildSubscriptionFromRequest(HttpServletRequest request) {
        // 示例：构造 JsonNode 的 subject/resource/action
        ObjectNode subject = JsonNodeFactory.instance.objectNode();
        subject.put("userId", getUserId(request));

        ObjectNode action = JsonNodeFactory.instance.objectNode();
        action.put("method", request.getMethod());

        ObjectNode resource = JsonNodeFactory.instance.objectNode();
        resource.put("path", request.getRequestURI());

        return AuthorizationSubscription.of(subject, action, resource);
    }

    private String getUserId(HttpServletRequest request) {
        // 从上下文或 header 中提取用户身份
        return "alice";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
