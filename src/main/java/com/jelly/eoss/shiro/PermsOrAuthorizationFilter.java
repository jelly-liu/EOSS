package com.jelly.eoss.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by jelly on 2016-10-18.
 */
public class PermsOrAuthorizationFilter extends AuthorizationFilter {
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

        return false;
    }
}
