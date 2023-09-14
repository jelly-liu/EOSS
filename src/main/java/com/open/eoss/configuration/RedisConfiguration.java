package com.open.eoss.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:40 AM 2019/1/10
 * @Description：
 * MySQL, Redis, RabbitMQ, ETC...
 */

@Configuration
public class RedisConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public JedisPool jedisPool(){
        String host = env.getProperty("spring.redis.host");
        Integer port = env.getProperty("spring.redis.port", Integer.class);
        Integer timeout = Integer.parseInt(StringUtils.substringBeforeLast(env.getProperty("spring.redis.timeout"), "MS"));
        String password = env.getProperty("spring.redis.password");

        JedisPoolConfig config = new JedisPoolConfig();
        // Resolve ERROR: MXBean already registered with name org.apache.commons.pool2:type=GenericObjectPool,name=pool
        config.setJmxEnabled(false);
        JedisPool pool = new JedisPool(config, host, port, timeout, password);
        return pool;
    }
}
