package com.jelly.eoss.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:42 PM 2019/1/9
 * @Description：${description}
 */

@Configuration
public class DataSourceConfiguration {
    String url;
    String username;
    String password;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/eoss?useUnicode=true&amp;characterEncoding=utf8");
        dataSource.setUsername("root");
        dataSource.setPassword("sa");
        return dataSource;
    }
}
