package com.open.eoss.configuration;

import com.open.eoss.web.config.SysLoginCheckInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:30 PM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MvcConfiguration.class);

    @Autowired
    SysLoginCheckInterceptor sysLoginInterceptor;

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //指定所上传文件的总大小不能超过2048KB。注意maxUploadSize属性的限制不是针对单个文件，而是所有文件的容量之和
        multipartResolver.setMaxUploadSize(2048000);
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysLoginInterceptor).addPathPatterns("/**");
    }
}
