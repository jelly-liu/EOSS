package com.jelly.eoss.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:40 AM 2019/1/10
 * @Description：${description}
 */

@Configuration
public class JdbcTemplateConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource);
    }
}
