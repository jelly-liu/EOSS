package com.jelly.eoss.shiro;

import com.google.common.primitives.Bytes;
import com.jelly.eoss.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:46 PM 2019/1/22
 * @Description：${description}
 */

public class RedisShiroCache<K, V> implements Cache<K, V> {
    private static final Logger log = LoggerFactory.getLogger(RedisShiroCache.class);

    private String KEYS_KEY;
    private byte[] KEYS_KEY_BYTES;
    private String KEY_PREFIX;

    public RedisShiroCache(String name) {
        KEY_PREFIX = name + "_";
        KEYS_KEY = name + "_ALL_KEY";
        KEYS_KEY_BYTES = stringToBytes(KEYS_KEY);
    }

    private static byte[] stringToBytes(String str){
        try {
            return str.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public V get(K k) throws CacheException {
        String key = KEY_PREFIX + k.toString();
        V v = ShiroCacheUtil.get(key);
        log.info("shiro reids, get, k={}, v={}", key, v);
        return v;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String key = KEY_PREFIX + k.toString();
        ShiroCacheUtil.set(key, v);
        ShiroCacheUtil.sadd(KEYS_KEY_BYTES, stringToBytes(key));
        log.info("shiro reids, put, k={}, value={}, sadd={}", key, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = KEY_PREFIX + k.toString();
        log.info("shiro reids, remove, k={}", key);
        V v = ShiroCacheUtil.del(key);
        Long res = ShiroCacheUtil.srem(KEYS_KEY_BYTES, stringToBytes(key));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        log.info("shiro reids, clear");
        ShiroCacheUtil.clear(KEYS_KEY_BYTES);
    }

    @Override
    public int size() {
        int size = ShiroCacheUtil.size(KEYS_KEY_BYTES);
        log.info("shiro reids, size={}", size);
        return size;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = ShiroCacheUtil.keys(KEYS_KEY_BYTES);
        log.info("shiro reids, keys={}", keys);
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = ShiroCacheUtil.values(KEYS_KEY_BYTES);
        log.info("shiro reids, values={}", values);
        return values;
    }

    // 把session对象转化为byte保存到redis中
    public static  <V> byte[] objectToByte(V v){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(v);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 把byte还原为session
    public static  <V> V byteToObject(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        V v = null;
        try {
            in = new ObjectInputStream(bi);
            v = (V) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public static final class ShiroCacheUtil {

        public static <K> int size(byte[] kb) {
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();

                Long size = jedis.scard(kb);
                if (size == null) {
                    return 0;
                }

                return size.intValue();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return 0;
        }

        public static <K> void clear(byte[] keyb) {
            Jedis jedis = null;

            try {
                Set<byte[]> keys = jedis.smembers(keyb);
                if (keys == null || keys.size() == 0) {
                    return;
                }

                jedis = JedisUtil.jedisPool.getResource();
                for (byte[] key : keys) {
                    jedis.del(key);
                }
                jedis.del(keyb);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }

        public static <K> Set<K> keys(byte[] kb) {
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Set<byte[]> keys = jedis.smembers(kb);
                if (keys == null || keys.size() == 0) {
                    return null;
                }

                Set<K> keyS = new HashSet();
                for (byte[] key : keys) {
                    K keyk = (K)new String(key);
                    keyS.add(keyk);
                }
                return keyS;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> List<V> values(byte[] kb) {
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                Set<byte[]> keys = jedis.smembers(kb);
                if (keys == null || keys.size() == 0) {
                    return null;
                }

                List<V> values = new LinkedList();
                for (byte[] key : keys) {
                    byte[] value = jedis.get(key);
                    V v = (V)byteToObject(value);
                    values.add(v);
                }
                return values;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> V get(K k) {
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();

                byte[] key = stringToBytes(k.toString());
                byte[] value = jedis.get(key);

                if (value == null) {
                    return null;
                }

                V v = byteToObject(value);
                return v;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> Long sadd(byte[] k, byte[] v) {
            Jedis jedis = null;

            try {
                if (k == null) {
                    return null;
                }

                if (v == null) {
                    return null;
                }

                jedis = JedisUtil.jedisPool.getResource();
                Long res = jedis.sadd(k, v);
                return res;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> Long srem(byte[] k, byte[] v) {
            Jedis jedis = null;

            try {
                if (k == null) {
                    return null;
                }

                if (v == null) {
                    return null;
                }

                jedis = JedisUtil.jedisPool.getResource();
                Long res = jedis.srem(k, v);
                return res;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> V set(K k, V v) {
            Jedis jedis = null;

            try {
                byte[] key = stringToBytes(k.toString());
                byte[] value = objectToByte(v);

                if (key == null) {
                    return null;
                }

                if (value == null) {
                    return null;
                }

                jedis = JedisUtil.jedisPool.getResource();
                jedis.set(key, value);
                return v;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            return null;
        }

        public static <K, V> V del(K k) {
            Jedis jedis = null;

            try {
                jedis = JedisUtil.jedisPool.getResource();
                byte[] key = stringToBytes(k.toString());
                byte[] value = jedis.get(key);

                if (value == null) {
                    return null;
                }

                V v = byteToObject(value);
                jedis.del(stringToBytes(k.toString()));
                return v;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            throw new RuntimeException("redis del error");
        }

        public static void main(String[] args) {
            RedisShiroCache<String, Session> testCash = new RedisShiroCache("TestCash");
            String key = "11111";
            Session session = new SimpleSession();
            ((SimpleSession) session).setId("K11");

            testCash.put(key, session);

            Session session1 = testCash.get(key);

            Assert.isTrue(testCash.size() == 1);
            Assert.isTrue(testCash.values().size() == 1);

            testCash.remove(key);

            Assert.isTrue(testCash.size() == 0);

            List<Session> users = (List<Session>) testCash.values();
            Assert.isTrue((users == null ? 0 : users.size()) == 0);

        }
    }
}
