package com.jelly.eoss.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:20 PM 2019/1/24
 * @Description：${description}
 */

public class EossFormAuthenticationFilter extends FormAuthenticationFilter {
    public EossFormAuthenticationFilter() {
        this.pathMatcher = new EossAntPathMatcher();
    }
}
