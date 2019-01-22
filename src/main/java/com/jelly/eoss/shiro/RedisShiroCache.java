package com.jelly.eoss.shiro;

import com.google.common.primitives.Bytes;
import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.util.JedisUtil;
import com.jelly.eoss.util.ProtoStuffSerializer;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:46 PM 2019/1/22
 * @Description：${description}
 */

public class RedisShiroCache<K, V> implements Cache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(RedisShiroCache.class);

    private static byte[] KEYS_KEY_BYTES;
    private static byte[] KEY_PREFIX;

    static {
        try {
            KEYS_KEY_BYTES = ("SHIRO_CACHE_ALL_KEYS_KEY_s3n9v329").getBytes(JedisUtil.encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public RedisShiroCache(String name) {
        try {
            KEY_PREFIX = name.getBytes(JedisUtil.encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public V get(K k) throws CacheException {
        log.info("shiro reids, get, k={}", k);
        return ShiroCacheUtil.get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        log.info("shiro reids, put, k={}, value={}", k, v);
        return ShiroCacheUtil.set(k, v);
    }

    @Override
    public V remove(K k) throws CacheException {
        log.info("shiro reids, remove, k={}", k);
        return ShiroCacheUtil.del(k);
    }

    @Override
    public void clear() throws CacheException {
        log.info("shiro reids, clear");
        ShiroCacheUtil.clear();
    }

    @Override
    public int size() {
        int size = ShiroCacheUtil.size();
        log.info("shiro reids, size={}", size);
        return size;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = ShiroCacheUtil.keys();
        log.info("shiro reids, keys={}", keys);
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection values = ShiroCacheUtil.values();
        log.info("shiro reids, values={}", values);
        return values;
    }

    public static final class ShiroCacheUtil {
        public static int size(){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Long size = jedis.scard(KEYS_KEY_BYTES);
                if (size == null) {
                    return 0;
                }
                return size.intValue();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            return 0;
        }

        public static void clear(){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Set<byte[]> keys = jedis.smembers(KEYS_KEY_BYTES);
                if(keys == null || keys.size() == 0){
                    return;
                }
                for(byte[] key : keys){
                    jedis.del(key);
                }
                jedis.del(KEYS_KEY_BYTES);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }
        }

        public static <K> Set<K> keys(){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Set<byte[]> keys = jedis.smembers(KEYS_KEY_BYTES);
                if(keys == null || keys.size() == 0){
                    return null;
                }

                Set<K> keyS = new HashSet();
                for(byte[] key : keys){
                    K k = ProtoStuffSerializer.readObject(key);
                    keyS.add(k);
                }
                return keyS;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            return null;
        }

        public static <V> List<V> values(){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Set<byte[]> keys = jedis.smembers(KEYS_KEY_BYTES);
                if(keys == null || keys.size() == 0){
                    return null;
                }

                List<V> values = new LinkedList();
                for(byte[] key : keys){
                    byte[] value = jedis.get(key);
                    V v = ProtoStuffSerializer.readObject(value);
                    values.add(v);
                }
                return values;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            return null;
        }

        public static <K,V> V get(K k){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();

                byte[] key = ProtoStuffSerializer.writeObject(k);
                key = merge(KEY_PREFIX, key);
                byte[] value = jedis.get(key);

                if (value == null) {
                    return null;
                }

                return ProtoStuffSerializer.readObject(value);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            return null;
        }

        public static <K,V> V set(K k, V v){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();

                byte[] key = ProtoStuffSerializer.writeObject(k);
                key = merge(KEY_PREFIX, key);
                byte[] value = ProtoStuffSerializer.writeObject(v);

                if (key == null) {
                    return null;
                }

                if (value == null) {
                    return null;
                }

                jedis.set(key, value);
                jedis.sadd(KEYS_KEY_BYTES, key);
                return v;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            return null;
        }

        public static <K,V> V del(K k){
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                V v = get(k);
                byte[] key = ProtoStuffSerializer.writeObject(k);
                key = merge(KEY_PREFIX, key);
                jedis.del(key);
                jedis.srem(KEYS_KEY_BYTES, key);
                return v;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();
                }
            }

            throw new RuntimeException("redis del error");
        }

        private static byte[] merge(byte[] prev, byte[] ths){
            return Bytes.concat(prev, ths);
        }
    }

    public static void main(String[] args) {
        String key = "11111";
        AdminUser value = new AdminUser().setId(22).setUsername("tom").setPassword("cat");

        ShiroCacheUtil.set(key, value);

        AdminUser user = ShiroCacheUtil.get(key);
        System.out.println(user.getId() + "," + user.getUsername() + "," + user.getPassword());

        Assert.isTrue(ShiroCacheUtil.size() == 1);
        Assert.isTrue(ShiroCacheUtil.values().size() == 1);

        ShiroCacheUtil.del(key);

        Assert.isTrue(ShiroCacheUtil.size() == 0);

        List<AdminUser> users = ShiroCacheUtil.values();
        Assert.isTrue((users == null ? 0 : users.size()) == 0);
    }
}
