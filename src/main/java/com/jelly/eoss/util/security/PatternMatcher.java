package com.jelly.eoss.util.security;

/**
 * Created by jelly on 2016-10-25.
 */
public interface PatternMatcher {
    boolean matches(String pattern, String source);
}
