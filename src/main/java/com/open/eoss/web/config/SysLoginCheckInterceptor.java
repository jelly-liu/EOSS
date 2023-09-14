package com.open.eoss.web.config;

import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.service.sys.SysSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class SysLoginCheckInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SysLoginCheckInterceptor.class);

    @Autowired
    SysSessionManager sysSessionManager;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final List<String> greenWhenNotLogin = Arrays.asList(
            "/static/**", "/sys/to-login", "/sys/login", "/sys/to401"
    );

    private final List<String> greenAfterLogin = Arrays.asList(
            "/sys/to-main", "/sys/to-west", "/sys/west", "/sys/logout"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(">>>>>>1, {}", request.getRequestURI());
        request.setAttribute("timestamp", new Date().getTime());

        request.setAttribute("timestamp", new Date().getTime());

        // 不登陆，不需要拦截的地址
        for(String uri : greenWhenNotLogin){
            if(antPathMatcher.match(uri, request.getRequestURI())){
                return true;
            }
        }

        SysSessionInfo sessionInfo = sysSessionManager.getSessionInfo(request);
        // 没有登录就引导至 登录页面
        if(sessionInfo == null){
            this.redirectToLoginPageByTopJs(request, response);
            return false;
        }

        request.setAttribute("sessionInfo", sessionInfo);

        //已登陆，不需要查询的地址
        for(String uri : greenAfterLogin){
            if(antPathMatcher.match(uri, request.getRequestURI())){
                return true;
            }
        }

        // 已登录的需要检索是否有权限
        boolean hasPermission = false;
        Set<String> permUrlList = sessionInfo.getPerms();
        if(!CollectionUtils.isEmpty(permUrlList)){
            for(String u : permUrlList){
                if(antPathMatcher.match(u, request.getRequestURI())){
                    hasPermission = true;
                    break;
                }
            }
        }
        if(!hasPermission){
            this.redirectTo401(request, response);
            return false;
        }

        sysSessionManager.access(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

    private void redirectToLoginPageByTopJs(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if(isAjaxRequest(request)){
//
//        }else{
//            response.sendRedirect("/toLogin");
//        }

        PrintWriter out = response.getWriter();
        out.write("<script type=\"text/javascript\">");
        out.write("top.window.location.href = '/sys/to-login';");
        out.write("</script>");
    }

    private void redirectTo401(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if(isAjaxRequest(request)){
//
//        }else{
//            response.sendRedirect("/toLogin");
//        }

        PrintWriter out = response.getWriter();
        out.write("<script type=\"text/javascript\">");
        out.write("window.location.href = '/sys/to401';");
        out.write("</script>");
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        return false;
    }
}
