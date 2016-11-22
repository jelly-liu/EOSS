package com.jelly.eoss.filter;

import com.jelly.eoss.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            User user = (User)subject.getSession().getAttribute("USER_INFO");
            if(user != null){
                if(log.isDebugEnabled()){
                    log.debug("user info in Shiro.Subject, user.id={}, user.name={}", user.getId(), user.getUsername());
                }
            }
        }
        filterChain.doFilter(req, res);
	}

	public void destroy() {

	}
}
