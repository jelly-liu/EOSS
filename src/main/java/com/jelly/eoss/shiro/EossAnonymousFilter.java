package com.jelly.eoss.shiro;

import org.apache.shiro.web.filter.authc.AnonymousFilter;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:24 PM 2019/1/24
 * @Description：${description}
 */

public class EossAnonymousFilter extends AnonymousFilter {
    public EossAnonymousFilter() {
        this.pathMatcher = new EossAntPathMatcher();
    }
}
