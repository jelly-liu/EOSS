package com.jelly.eoss.filter;

import com.jelly.eoss.model.UserRolesPerms;
import com.jelly.eoss.security.FilterCore;
import com.jelly.eoss.util.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);

	private FilterCore filterCore;

	public void init(FilterConfig filterConfig) throws ServletException {
		String filterDefinition = filterConfig.getInitParameter("filterDefinition");
        log.debug("filterDefinition={}", filterDefinition);

        String config = StringUtils.substringBetween(filterDefinition, "[config]", "[urls]");
        String urls = StringUtils.substringAfter(filterDefinition, "[urls]");

		filterCore = new FilterCore();
        filterCore.init(config, urls);
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String contextPath = request.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        String path = StringUtils.substringAfter(uri, contextPath);
        log.debug("contextPath={}, uri={}, path={}", contextPath, uri, path);

        UserRolesPerms userRolesPerms = (UserRolesPerms) request.getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        Set<String> roleSet = null;
        Set<String> permSet = null;

        boolean authc = false;
        if(userRolesPerms != null){
            authc = true;
            roleSet = userRolesPerms.getRolesOfUser();
            permSet = userRolesPerms.getPermsOfUser();
        }
        log.debug("authc={}, roleSetOfUser={}, permSetOfUser={}", authc, roleSet, permSet);

        if(filterCore.doFilter(path, authc, roleSet, permSet)){
            filterChain.doFilter(request, response);
        }else{
            if(userRolesPerms == null){
                if(StringUtils.startsWith(path, filterCore.getLoginUrl())){
                    log.debug("is login url, pass, path={}, loginUrl={}", path, filterCore.getLoginUrl());
                    filterChain.doFilter(request, response);
                    return;
                }
                //跳转到登陆页面
                log.debug("security filter, deney, " + request.getRequestURI());
                String html = "<script type=\"text/javascript\">top.window.location.href=\"_BP_/toLogin.ac\"</script>";
                html = html.replace("_BP_", Const.BASE_PATH);
                response.getWriter().write(html);
                return;
            }else{
                request.getRequestDispatcher("/401.jsp").forward(request, response);
            }
        }
	}

	public void destroy() {

	}
}
