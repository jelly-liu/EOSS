package com.jelly.eoss.configuration;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:30 PM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class MvcConfiguration {

    public FastJsonHttpMessageConverter messageConverter(){
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();

        List<MediaType> supportedMediaTypes = new ArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        messageConverter.setSupportedMediaTypes(supportedMediaTypes);
        /*
        <property name="features">
            <list>
                <!-- 默认的意思就是不配置这个属性，配置了就不是默认了 -->
                <!-- 是否输出值为null的字段 ，默认是false-->
                <!--<value>WriteMapNullValue</value>-->
                <!--<value>WriteNullNumberAsZero</value>-->
                <!--<value>WriteNullListAsEmpty</value>-->
                <!--<value>WriteNullStringAsEmpty</value>-->
                <!--<value>WriteNullBooleanAsFalse</value>-->
                <!--<value>WriteDateUseDateFormat</value>-->

            </list>
        </property>
         */
        return messageConverter;
    }

    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages");
        viewResolver.setSuffix("");
        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //指定所上传文件的总大小不能超过2048KB。注意maxUploadSize属性的限制不是针对单个文件，而是所有文件的容量之和
        multipartResolver.setMaxUploadSize(2048000);
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("/static/");
            }
        };
    }
}
