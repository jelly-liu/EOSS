package com.jelly.eoss.shiro;

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

import javax.servlet.Filter;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by jelly on 2016-11-8.
 */
public class EossShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private static final Logger log = LoggerFactory.getLogger(EossShiroFilterFactoryBean.class);

    private String filterChainDefinitions;
    private AbstractShiroFilter instance;

    @Override
    public void setFilterChainDefinitions(String definitions) {
        definitions = format(definitions);
        super.setFilterChainDefinitions(definitions);
        this.filterChainDefinitions = definitions;
    }

    //for more information, check: ShiroFilterFactoryBean.createInstance();
    public void updateFilterChainDefinitions(String definitions, AbstractShiroFilter abstractShiroFilter) {
        this.setFilterChainDefinitions(definitions);

        FilterChainManager manager = createFilterChainManager();
        PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
        filterChainResolver.setFilterChainManager(manager);
        filterChainResolver.setPathMatcher(new EossAntPathMatcher());

        injectEossAntPathMatcher(filterChainResolver);

        abstractShiroFilter.setFilterChainResolver(filterChainResolver);
    }

    @Override
    public Object getObject() throws Exception {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    /**
     * here, we create our custom EossAntPathMather, this path mather do not care capital or lower case
     *
     * @return
     * @throws Exception
     */
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {

        log.debug("Creating Shiro Filter instance.");

        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            String msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            String msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        }

        FilterChainManager manager = createFilterChainManager();
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);

        return new SpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    // inject EossAntPathMatcher, do not care about upper or lower case of request path
    private static void injectEossAntPathMatcher(PathMatchingFilterChainResolver filterChainResolver) {
        try {
            //inject EossAntPathMatcher to all filters
            FilterChainManager manager = filterChainResolver.getFilterChainManager();
            Map<String, Filter> filterMap = manager.getFilters();

            Field pathMatcherField = PathMatchingFilter.class.getDeclaredField("pathMatcher");
            pathMatcherField.setAccessible(true);
            for (Map.Entry<String, Filter> entry : filterMap.entrySet()) {
                Filter value = entry.getValue();
                if(value instanceof PathMatchingFilter){
                    pathMatcherField.set(value, new EossAntPathMatcher());
                }
            }
            pathMatcherField.setAccessible(false);

            //inject EossAntPathMatcher to filterChainResolver
            filterChainResolver.setPathMatcher(new EossAntPathMatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final class SpringShiroFilter extends AbstractShiroFilter {
        protected SpringShiroFilter(WebSecurityManager webSecurityManager, PathMatchingFilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            injectEossAntPathMatcher(resolver);
            setSecurityManager(webSecurityManager);
            setFilterChainResolver(resolver);
        }
    }

    private String format(String definitions) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            LineReader lineReader = new LineReader(new StringReader(definitions));

            String line = null;
            while ((line = lineReader.readLine()) != null) {
                line = StringUtils.trimToNull(line);
                if (StringUtils.isNotEmpty(line)) {
                    stringBuilder.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public String getFilterChainDefinitions() {
        return this.filterChainDefinitions;
    }
}
