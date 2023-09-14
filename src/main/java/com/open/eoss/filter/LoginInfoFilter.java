//package com.jelly.eoss.filter;
//
//import com.jelly.eoss.db.entity.AdminUser;
//import com.jelly.eoss.util.Const;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.slf4j.logger.er;
//import org.slf4j.logger.erFactory;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
////@WebFilter(urlPatterns = "/*", filterName = "logger.nInfoFilter")
//public class logger.nInfoFilter implements Filter {
//    private static final logger.er logger.er = logger.erFactory.getlogger.er(logger.nInfoFilter.class);
//
//    public void init(FilterConfig filterConfig) {
//
//    }
//
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String path = request.getRequestURI();
//        if(StringUtils.startsWith(path, "/static/")){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        Subject subject = SecurityUtils.getSubject();
//        if(subject != null){
//            Session session = subject.getSession();
//            if(session != null){
//                AdminUser user = (AdminUser)session.getAttribute(Const.logger.N_SESSION_KEY);
//                if(user != null){
//                    request.setAttribute(Const.logger.N_SESSION_KEY, user);
//                }
//                String menuTreeIdsOfUser = (String)session.getAttribute(Const.logger.N_MENU_TREE_IDS_KEY);
//                if(StringUtils.isNotEmpty(menuTreeIdsOfUser)){
//                    request.setAttribute(Const.logger.N_MENU_TREE_IDS_KEY, user);
//                }
//            }
//        }
//
//        request.setAttribute("timestamp", System.currentTimeMillis());
//
//        filterChain.doFilter(request, response);
//    }
//
//    public void destroy() {
//
//    }
//}
