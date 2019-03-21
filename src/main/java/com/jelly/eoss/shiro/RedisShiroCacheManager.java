package com.jelly.eoss.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:38 PM 2019/1/22
 * @Description：${description}
 */

public class RedisShiroCacheManager implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(RedisShiroCacheManager.class);
    private static final ConcurrentHashMap<String, Cache> cacheMap = new ConcurrentHashMap();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache<K, V> cache = cacheMap.get(name);
        if(cache != null){
            return cache;
        }

        log.info("RedisShiroCacheManager, create new cache category, name is {}", name);
        cache = new RedisShiroCache<K, V>(name);
        cacheMap.put(name, cache);
        return cache;
    }
}
