package com.jelly.eoss.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:38 PM 2019/1/22
 * @Description：${description}
 */

public class RedisShiroCacheManager extends AbstractCacheManager {
    @Override
    protected Cache createCache(String name) throws CacheException {
        return new RedisShiroCache(name);
    }
}
