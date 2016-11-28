package com.jelly.eoss.shiro.cache;

import com.google.common.cache.CacheBuilder;
import com.jelly.eoss.util.JavaSerializer;
import com.jelly.eoss.util.ProtoStuffSerializer;
import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by jelly on 2016-11-21.
 */
public class RedisCache<K, V> implements Cache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(RedisCache.class);
    private com.google.common.cache.Cache cache;
    private Charset charset = Charset.forName("UTF-8");

    private String name;
    private RedisTemplate redisTemplate;

    private int localSize;
    private int expireTimeSeconds;

    public RedisCache(String name, RedisTemplate redisTemplate, int localSize, int expireTimeSeconds) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.localSize = localSize;
        this.expireTimeSeconds = expireTimeSeconds;
        cache = CacheBuilder.newBuilder().maximumSize(localSize).expireAfterAccess(expireTimeSeconds, TimeUnit.SECONDS).build();
    }

    @Override
    public V get(K key) throws CacheException {
        V value = (V)cache.getIfPresent(key);
        if(value == null) {
            try {
                value = (V) redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        byte[] bytes = redisConnection.get(key.toString().getBytes(charset));
                        if (bytes == null || bytes.length == 0) return null;
                        V v = (V)ProtoStuffSerializer.readObject(bytes);
//                        V v = JavaSerializer.deserialize(bytes);
                        cache.put(key, v);
                        return v;
                    }
                });
            }catch (Exception e){
                if(log.isDebugEnabled()){
                    log.debug("R_CACHE, exception", e);
                }
            }
            if(log.isDebugEnabled()){
                log.debug("R_CACHE, cache name={}, get from redis, key{}, value={}", name, key, value);
            }
        }
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, cache name={}, get from cache, key{}, value={}", name, key, value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V v = (V)get(key);

        cache.put(key, value);
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] bytes = ProtoStuffSerializer.writeObject(value);
//                    byte[] bytes = JavaSerializer.serialize(value);
                    redisConnection.set(key.toString().getBytes(charset), bytes);
                    if (log.isDebugEnabled()) {
                        log.debug("R_CACHE, cache name={}, put to redis, key{}, value={}", name, key, value);
                    }
                    return null;
                }
            });
        }catch (Exception e){
            if(log.isDebugEnabled()){
                log.debug("R_CACHE, exception", e);
            }
        }

        if(log.isDebugEnabled()){
            log.debug("R_CACHE, cache name={}, put to cache, key{}, value={}, oldValue={}", name, key, value, v);
        }
        return v;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v = get(key);
        cache.invalidate(key);
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.del(key.toString().getBytes(charset));
                    if (log.isDebugEnabled()) {
                        log.debug("R_CACHE, cache name={}, remove from redis, key{}, oldValue={}", name, key, v);
                    }
                    return null;
                }
            });
        }catch (Exception e){
            if(log.isDebugEnabled()){
                log.debug("R_CACHE, exception", e);
            }
        }
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, cache name={}, remove from cache, key{}, oldValue={}", name, key, v);
        }
        return v;
    }

    @Override
    public void clear() throws CacheException {
        throw new ShiroException("un support cache method clear()");
    }

    @Override
    public int size() {
        throw new ShiroException("un support cache method size()");
    }

    @Override
    public Set<K> keys() {
        throw new ShiroException("un support cache method keys()");
    }

    @Override
    public Collection<V> values() {
        throw new ShiroException("un support cache method values()");
    }

    public int getLocalSize() {
        return localSize;
    }

    public RedisCache setLocalSize(int localSize) {
        this.localSize = localSize;
        return this;
    }

    public int getExpireTimeSeconds() {
        return expireTimeSeconds;
    }

    public RedisCache setExpireTimeSeconds(int expireTimeSeconds) {
        this.expireTimeSeconds = expireTimeSeconds;
        return this;
    }
}
