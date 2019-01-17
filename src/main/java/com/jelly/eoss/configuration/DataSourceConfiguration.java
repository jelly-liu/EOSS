package com.jelly.eoss.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:40 AM 2019/1/10
 * @Description：
 * MySQL, Redis, RabbitMQ, ETC...
 */

@Configuration
public class DataSourceConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(false);
        JedisPool pool = new JedisPool(config,
                env.getProperty("spring.redis.url"),
                env.getProperty("spring.redis.port", Integer.class),
                Integer.parseInt(StringUtils.substringBeforeLast(env.getProperty("spring.redis.timeout"), "MS")),
                env.getProperty("spring.redis.passowrd"));
        return pool;
    }
}
