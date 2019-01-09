package com.jelly.eoss.configuration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:11 AM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class MyBatisConfiguration {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("config/mybatis/mybatis_base.xml"));
        sqlSessionFactory.setMapperLocations(new Resource[]{new ClassPathResource("com/jelly/eoss/db/mapper/**/*Mapper.xml")});

        Properties properties = new Properties();
        properties.put("dialect", "com.github.pagehelper.PageHelper");
        properties.put("helperDialect", "mysql");

        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        sqlSessionFactory.setPlugins(new Interceptor[]{interceptor});

        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory().getObject());
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.jelly.eoss.db.mapper");
        return mapperScannerConfigurer;
    }
}
