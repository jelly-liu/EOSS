package com.jelly.eoss.configuration;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import com.jelly.eoss.db.mapper.basic.iface.AdminFilterchainDefinitionMapper;
import com.jelly.eoss.shiro.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public MemoryConstrainedCacheManager cacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(realm());
        return securityManager;
    }

    @Bean
    public RolesOrAuthorizationFilter rolesOr(){
        RolesOrAuthorizationFilter rolesOrAuthorizationFilter = new RolesOrAuthorizationFilter();
        rolesOrAuthorizationFilter.setPathMather(new EossAntPathMatcher());
        return rolesOrAuthorizationFilter;
    }

    @Bean
    public PermsOrAuthorizationFilter permsOr(){
        PermsOrAuthorizationFilter permsOrAuthorizationFilter = new PermsOrAuthorizationFilter();
        permsOrAuthorizationFilter.setPathMather(new EossAntPathMatcher());
        return permsOrAuthorizationFilter;
    }

    @Bean
    public EossShiroFilterFactoryBean shiroFilter() {
        EossShiroFilterFactoryBean shiroFilterFactoryBean = new EossShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401.htm");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("rolesOr", rolesOr());
        filters.put("permsOr", permsOr());
        shiroFilterFactoryBean.setFilters(filters);

        AdminFilterchainDefinition filterchainDefinition = filterchainDefinitionMapper.selectByPk(1);
        shiroFilterFactoryBean.setFilterChainDefinitions(filterchainDefinition.getDefinition());

        return shiroFilterFactoryBean;
    }
}
