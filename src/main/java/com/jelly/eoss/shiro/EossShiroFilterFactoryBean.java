package com.jelly.eoss.shiro;

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;

import java.io.StringReader;

/**
 * Created by jelly on 2016-11-8.
 */
public class EossShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    private static final Logger log = LoggerFactory.getLogger(EossShiroFilterFactoryBean.class);

    private String filterChainDefinitions;
    private AbstractShiroFilter instance;
    private EossAntPathMatcher pathMatcher = new EossAntPathMatcher();

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
        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        chainResolver.setFilterChainManager(manager);
        chainResolver.setPathMatcher(pathMatcher);

        abstractShiroFilter.setFilterChainResolver(chainResolver);
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
        chainResolver.setPathMatcher(pathMatcher);

        return new SpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }

    private static final class SpringShiroFilter extends AbstractShiroFilter {

        protected SpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }
    }

    private String format(String definitions){
        StringBuilder stringBuilder = new StringBuilder();

        try {
            LineReader lineReader = new LineReader(new StringReader(definitions));

            String line = null;
            while ((line = lineReader.readLine()) != null) {
                line = StringUtils.trimToNull(line);
                if(StringUtils.isNotEmpty(line)){
                    stringBuilder.append(line).append("\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public String getFilterChainDefinitions() {
        return this.filterChainDefinitions;
    }
}
