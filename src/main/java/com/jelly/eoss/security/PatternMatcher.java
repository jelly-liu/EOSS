package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */
public interface PatternMatcher {
    boolean matches(String pattern, String source);
}
