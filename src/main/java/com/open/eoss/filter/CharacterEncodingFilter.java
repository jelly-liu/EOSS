package com.open.eoss.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*", filterName = "CharacterEncodingFilter")
public class CharacterEncodingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);

    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getContextPath();
//        logger.info("REQUEST PATH: " + path);
        filterChain.doFilter(request, response);
    }

    public void destroy() {

    }
}
