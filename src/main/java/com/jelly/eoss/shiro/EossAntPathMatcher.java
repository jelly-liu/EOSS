package com.jelly.eoss.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.AntPathMatcher;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:03 AM 2019/1/7
 * @Description：
 * the only different between EossAntPathMatcher and AntPathMatcher is:
 * EossAntPathMatcher does not care any capital or small letter
 */

public class EossAntPathMatcher extends AntPathMatcher {
    @Override
    protected boolean doMatch(String pattern, String path, boolean fullMatch) {
        boolean matched = super.doMatch(StringUtils.lowerCase(pattern), StringUtils.lowerCase(path), fullMatch);
        return matched;
    }
}
