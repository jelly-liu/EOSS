package com.jelly.eoss.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by jelly on 2016-10-18.
 */
public class PermsOrAuthorizationFilter extends AuthorizationFilter {
    private static final Logger log = LoggerFactory.getLogger(PermsOrAuthorizationFilter.class);

    //mappedValue, the filter-specific config value mapped to this filter in the URL rules mappings
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        if(perms == null || perms.length == 0){
            //no permissions specified, so nothing to check - allow access.
            return true;
        }

        if (perms != null && perms.length > 0) {
            for(String perm : perms){
                if (subject.isPermitted(perm)) {
                    return true;
                }
            }
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        log.info("perms authorization failed, path={}", httpServletRequest.getRequestURI());
        return false;
    }

    public void setPathMather(EossAntPathMatcher pathMather){
        this.pathMatcher = pathMather;
    }
}
