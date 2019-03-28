package com.jelly.eoss.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 6:18 PM 2019/3/28
 * @Description：${description}
 */

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }

            redirectToLoginPageByTopJs(request, response);
            return false;
        }
    }

    private void redirectToLoginPageByTopJs(ServletRequest request, ServletResponse response) throws IOException {
        saveRequest(request);
        String loginUrl = getLoginUrl();

        PrintWriter out = response.getWriter();
        out.write("<script type=\"text/javascript\">");
        out.write("top.window.location.href = '" + loginUrl + "';");
        out.write("</script>");
    }
}
