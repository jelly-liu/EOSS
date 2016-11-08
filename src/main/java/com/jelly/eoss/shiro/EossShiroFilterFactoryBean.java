package com.jelly.eoss.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

/**
 * Created by jelly on 2016-11-8.
 */
public class EossShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    public void refreshFilterChainDefinition(String filterChainDefinition, AbstractShiroFilter springShiroFilter){
        setFilterChainDefinitions(filterChainDefinition);

        FilterChainManager manager = createFilterChainManager();
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        springShiroFilter.setFilterChainResolver(chainResolver);
    }
}
