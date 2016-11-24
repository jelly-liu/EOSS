package com.jelly.eoss.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.servlet.NameableFilter;
import org.apache.shiro.web.servlet.ProxiedFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jelly on 2016-11-24.
 *
 * this class only used under this condition
 * if your config an rule like:
 *  /business/** = authc, rolesOr[业务员, 管理员], permsOr[perm:add, perm:delete]
 * default logic is authc success and rolesOr success and permsOr success
 * but you want authc must, and(hasAnyRole of 业务员, 管理员  ******OR******  hasAnyPerm of   perm:add, perm:delete), then allow access
 *
 * step 1
 * you also need config anyFilterName in com.jelly.eoss.shiro.EossShiroFilterFactoryBean through spring
 *
 * step 2
 * let com.jelly.eoss.shiro.EossShiroFilterFactoryBean.createInstance() to create this class: EossSpringShiroFilter
 *
 * step 3
 * your also need to rewrite onAccessDenied() method in your custom AuthorizationFilter,
 * for the reason that any onAccessDenied() will call sendRedirect() finally
 * and will cause error like: Cannot call sendRedirect() after the response has been committed
 */
public class EossSpringShiroFilter extends AbstractShiroFilter {
    private static final Logger log = LoggerFactory.getLogger(EossSpringShiroFilter.class);

    private String anyFilterName;
    private Set<String> anyFilterNameSet;

    private Field filtersField;
    private Method preHandleMethod;
    private Field nameField;

    public EossSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver, String anyFilterName) {
        super();
        if (webSecurityManager == null) {
            throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
        }
        setSecurityManager(webSecurityManager);
        if (resolver != null) {
            setFilterChainResolver(resolver);
        }

        this.anyFilterName = anyFilterName;
        this.anyFilterNameSet = new HashSet<>();
        String[] filterNames = StringUtils.split(anyFilterName, ",");
        for(String filterName : filterNames){
            filterName = StringUtils.trim(filterName);
            this.anyFilterNameSet.add(filterName);
        }
    }

    @Override
    protected void executeChain(ServletRequest request, ServletResponse response, FilterChain origChain) throws IOException, ServletException {
        FilterChain chain = getExecutionChain(request, response, origChain);
        if(!(chain instanceof ProxiedFilterChain)){
            return;
        }

        ProxiedFilterChain proxiedFilterChain = (ProxiedFilterChain)chain;
        if(this.filtersField == null){
            this.filtersField = ReflectionUtils.findField(ProxiedFilterChain.class, "filters");
            this.filtersField.setAccessible(true);
        }
        List<Filter> filters = (List<Filter>)ReflectionUtils.getField(this.filtersField, proxiedFilterChain);
        if(log.isDebugEnabled()){
            log.debug("execute filter chain, size={}", (filters == null ? 0 : filters.size()));
        }
        if(filters == null || filters.size() == 0){
            origChain.doFilter(request, response);
            return;
        }

        boolean anySuccess = false;
        for(Filter filter : filters){
            AdviceFilter adviceFilter = (AdviceFilter)filter;
            if(filter instanceof AdviceFilter && filter instanceof NameableFilter){
                this.preHandleMethod = ReflectionUtils.findMethod(AdviceFilter.class, "preHandle", ServletRequest.class, ServletResponse.class);
                this.preHandleMethod.setAccessible(true);
                boolean isFilterChainContinued = (boolean)ReflectionUtils.invokeMethod(this.preHandleMethod, adviceFilter, request, response);
                if(log.isDebugEnabled()){
                    NameableFilter nameableFilter = (NameableFilter)filter;
                    log.debug("isFilterChainContinued={}, nameableFilter={}", isFilterChainContinued, nameableFilter.toString());
                }

                if(this.nameField == null){
                    this.nameField = ReflectionUtils.findField(NameableFilter.class, "name");
                    this.nameField.setAccessible(true);
                }

                NameableFilter nameableFilter = (NameableFilter)filter;
                String name = (String)ReflectionUtils.getField(this.nameField, nameableFilter);

                if(filters.size() == 1){
                    anySuccess = isFilterChainContinued;
                    break;
                }

                if(isFilterChainContinued && this.anyFilterNameSet.contains(name)){
                    anySuccess = true;
                    break;
                }
            }else{
                log.debug("not AdviceFilter, name={}", filter.getClass().getCanonicalName());
            }
        }

        if(anySuccess){
            origChain.doFilter(request, response);
        }
    }
}
