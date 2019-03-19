package com.jelly.eoss.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:38 PM 2019/1/22
 * @Description：${description}
 */

public class RedisShiroCacheManager extends AbstractCacheManager {
    private static final Logger log = LoggerFactory.getLogger(RedisShiroCacheManager.class);

    @Override
    protected Cache createCache(String name) throws CacheException {
        log.info("RedisShiroCacheManager, create cache name is {}", name);
        return new RedisShiroCache(name);
    }
}
