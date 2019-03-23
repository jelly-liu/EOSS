package com.jelly.eoss.configuration;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import com.jelly.eoss.db.mapper.basic.iface.AdminFilterchainDefinitionMapper;
import com.jelly.eoss.shiro.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 6:54 PM 2019/1/5
 * @Description：
 * ************************************************
 * bean name default is function name
 * ************************************************
 */

@Configuration
public class ShiroConfiguration {

    @Autowired
    private AdminFilterchainDefinitionMapper filterchainDefinitionMapper;

    // shiro web filter configuration
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        DelegatingFilterProxy shiroFilter = new DelegatingFilterProxy();

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(shiroFilter);
        registration.addUrlPatterns("/*");
        registration.setName("shiroFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public SecureRandomNumberGenerator secureRandomNumberGenerator(){
        return new SecureRandomNumberGenerator();
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    @Bean
    public EossAuthorizingRealm realm(){
        return new EossAuthorizingRealm(credentialsMatcher());
    }

    @Bean
    public CacheManager cacheManager(){
//        return new MemoryConstrainedCacheManager();
        return new RedisShiroCacheManager();
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public RedisSessionDAO sessionDAO(){
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        sessionDAO.setCacheManager(cacheManager());
        return sessionDAO;
    }

    @Bean
    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler(){
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(1000*5);//millis
        return sessionValidationScheduler;
    }

    @Bean
    public Cookie sessionIdCookie(){
        Cookie sessionIdCookie = new SimpleCookie();
        sessionIdCookie.setName("JSESSIONID_SHIRO");
        return sessionIdCookie;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000*3600);//millis, 3600000=1hour
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public RolesOrAuthorizationFilter rolesOr(){
        RolesOrAuthorizationFilter rolesOrAuthorizationFilter = new RolesOrAuthorizationFilter();
        return rolesOrAuthorizationFilter;
    }

    @Bean
    public PermsOrAuthorizationFilter permsOr(){
        PermsOrAuthorizationFilter permsOrAuthorizationFilter = new PermsOrAuthorizationFilter();
        return permsOrAuthorizationFilter;
    }

    @Bean
    public EossShiroFilterFactoryBean shiroFilter() {
        EossShiroFilterFactoryBean shiroFilterFactoryBean = new EossShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/to401");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("rolesOr", rolesOr());
        filters.put("permsOr", permsOr());

        shiroFilterFactoryBean.setFilters(filters);

        AdminFilterchainDefinition filterchainDefinition = filterchainDefinitionMapper.selectByPk(1);
        shiroFilterFactoryBean.setFilterChainDefinitions(filterchainDefinition.getDefinition());

        return shiroFilterFactoryBean;
    }
}
