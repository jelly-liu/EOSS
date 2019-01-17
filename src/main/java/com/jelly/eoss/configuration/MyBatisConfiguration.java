package com.jelly.eoss.configuration;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:11 AM 2019/1/9
 * @Description：${description}
 */

@Configuration
@MapperScan("com.jelly.eoss.db.mapper")
public class MyBatisConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(dataSource);
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/com/jelly/eoss/db/mapper/**/*Mapper.xml");
            sqlSessionFactory.setMapperLocations(resources);

            Properties properties = new Properties();
            properties.put("dialect", "com.github.pagehelper.PageHelper");
            properties.put("helperDialect", "mysql");

            PageInterceptor interceptor = new PageInterceptor();
            interceptor.setProperties(properties);
            sqlSessionFactory.setPlugins(new Interceptor[]{interceptor});

            return sqlSessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}
