package com.jelly.eoss.configuration;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.AdminFilterchainDefinition;
import com.jelly.eoss.shiro.EossAuthorizingRealm;
import com.jelly.eoss.shiro.EossShiroFilterFactoryBean;
import com.jelly.eoss.shiro.PermsOrAuthorizationFilter;
import com.jelly.eoss.shiro.RolesOrAuthorizationFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 6:54 PM 2019/1/5
 * @Description：${description}
 */

@Configuration
public class ShiroConfiguration {

    @Resource
    private BaseService baseService;

    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        return new HashedCredentialsMatcher("MD5");
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
        return new RolesOrAuthorizationFilter();
    }

    @Bean
    public PermsOrAuthorizationFilter permsOr(){
        return new PermsOrAuthorizationFilter();
    }

    @Bean
    public EossShiroFilterFactoryBean shiroFilter() throws Exception {
        EossShiroFilterFactoryBean shiroFilterFactoryBean = new EossShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/toLogin.ac");
        shiroFilterFactoryBean.setUnauthorizedUrl("/401.jsp");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("rolesOr", rolesOr());
        filters.put("permsOr", permsOr());
        shiroFilterFactoryBean.setFilters(filters);

        AdminFilterchainDefinition filterchainDefinition = baseService.mySelectOne(AdminFilterchainDefinition.SelectByPk, 1);
        shiroFilterFactoryBean.setFilterChainDefinitions(filterchainDefinition.getDefinition());

        return shiroFilterFactoryBean;
    }
}
