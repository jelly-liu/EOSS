package com.jelly.eoss.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:02 PM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        DelegatingFilterProxy characterEncodingFilter = new DelegatingFilterProxy();

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(characterEncodingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("shiroFilter");
        registration.setOrder(1);
        return registration;
    }
}
