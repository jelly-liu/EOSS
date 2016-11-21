package com.jelly.eoss.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jelly on 2016-11-21.
 */
public class RedisCacheManager extends AbstractCacheManager {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheManager.class);
    private RedisTemplate redisTemplate;

    @Override
    protected Cache createCache(String name) throws CacheException {
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, create local cache, name={}", name);
        }
        return new RedisCache(name, redisTemplate);
    }

    //getter and setter
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public RedisCacheManager setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }
}
