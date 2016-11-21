package com.jelly.eoss.shiro.cache;

import com.google.common.cache.CacheBuilder;
import com.jelly.eoss.util.ProtoStuffSerializer;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.nio.ByteBuffer;
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

    public RedisCache(String name, RedisTemplate redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        cache = CacheBuilder.newBuilder().maximumSize(10000).build();
    }

    @Override
    public V get(K key) throws CacheException {
        V value = (V)cache.getIfPresent(key);
        if(value == null) {
            value = (V)redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] bytes = redisConnection.get(key.toString().getBytes(charset));
                    if(bytes == null || bytes.length == 0)return null;
                    V v = (V)ProtoStuffSerializer.readObject(bytes);
                    cache.put(key, v);
                    return v;
                }
            });
        }
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, get from cache, key{}, value={}", key, value);
        }
        return value;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V v = (V)get(key);

        cache.put(key, value);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(key.toString().getBytes(charset), ProtoStuffSerializer.writeObject(value));
                return null;
            }
        });

        if(log.isDebugEnabled()){
            log.debug("R_CACHE, put to cache, key{}, value={}, oldValue={}", key, value, v);
        }
        return v;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v = get(key);
        cache.invalidate(key);
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.del(key.toString().getBytes(charset));
                return null;
            }
        });
        if(log.isDebugEnabled()){
            log.debug("R_CACHE, remove from cache, key{}, oldValue={}", key, v);
        }
        return v;
    }

    @Override
    public void clear() throws CacheException {
        throw new RuntimeException("not support");
    }

    @Override
    public int size() {
        throw new RuntimeException("not support");
    }

    @Override
    public Set<K> keys() {
        throw new RuntimeException("not support");
    }

    @Override
    public Collection<V> values() {
        throw new RuntimeException("not support");
    }
}
