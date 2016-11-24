package com.jelly.eoss.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by jelly on 2016-11-21.
 */
public class RedisCacheManager extends AbstractCacheManager {
    private static final Logger log = LoggerFactory.getLogger(RedisCacheManager.class);
    private RedisTemplate redisTemplate;
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    private int localSize;
    private int expireTimeSeconds;

    @Override
    protected Cache createCache(String name) throws CacheException {
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, create local cache, name={}", name);
        }
        RedisCache redisCache = new RedisCache(name, redisTemplate, localSize, expireTimeSeconds);
        caches.put(name, redisCache);
        return redisCache;
    }

    //getter and setter
    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public RedisCacheManager setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }

    public int getLocalSize() {
        return localSize;
    }

    public RedisCacheManager setLocalSize(int localSize) {
        this.localSize = localSize;
        return this;
    }

    public int getExpireTimeSeconds() {
        return expireTimeSeconds;
    }

    public RedisCacheManager setExpireTimeSeconds(int expireTimeSeconds) {
        this.expireTimeSeconds = expireTimeSeconds;
        return this;
    }
}
