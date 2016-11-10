package com.jelly.eoss.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by jelly on 2016-10-18.
 */
public class RolesOrAuthorizationFilter extends AuthorizationFilter {
    //mappedValue, the filter-specific config value mapped to this filter in the URL rules mappings
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] roles = (String[]) mappedValue;

        if (roles == null || roles.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        for(String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }

        return false;
    }
}
