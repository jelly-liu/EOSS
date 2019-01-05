package com.jelly.eoss.shiro;

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Component;

import java.io.StringReader;

/**
 * Created by jelly on 2016-11-8.
 */
public class EossShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    private String filterChainDefinitions;

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

        abstractShiroFilter.setFilterChainResolver(chainResolver);
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
