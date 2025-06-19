//package com.qm.base.shared.security.filter;
//
//import com.qm.base.shared.web.filter.QmFilter;
//import com.qm.base.shared.web.filter.QmFilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class AuthorizationFilter implements QmFilter {
//
//
//    @Override
//    public boolean match(HttpServletRequest request) {
//        return true;
//    }
//
//
//    @Override
//    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain)
//            throws IOException, ServletException {
//    }
//
//    private String getUserId(HttpServletRequest request) {
//        // 从上下文或 header 中提取用户身份
//        return "alice";
//    }
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//}
