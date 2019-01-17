package com.jelly.eoss.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:30 PM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCache(false);
        viewResolver.setViewNames(new String[]{"*.htm", "*.htm", "*.xhtml"});
        return viewResolver;
    }

    @Bean
    public SpringResourceTemplateResolver TemplateResolver(){
        SpringResourceTemplateResolver resourceTemplateResolver = new SpringResourceTemplateResolver();
        resourceTemplateResolver.setPrefix("classpath:/templates");
        resourceTemplateResolver.setCacheable(false);
        return resourceTemplateResolver;
    }

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
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
