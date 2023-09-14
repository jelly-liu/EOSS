package com.open.eoss.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 6:39 PM 2019/1/21
 * @Description：${description}
 */

public class JedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    public static JedisPool jedisPool;
    public static final String encode = "UTF-8";

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(
                config,
                PropertyUtil.getProperty("spring.redis.host"),
                Integer.parseInt(PropertyUtil.getProperty("spring.redis.port")),
                Integer.parseInt(StringUtils.substringBeforeLast(PropertyUtil.getProperty("spring.redis.timeout"), "MS")),
                PropertyUtil.getProperty("spring.redis.password"));
    }

    public static boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean b = false;
        try{
            b = jedis.exists(keySerialize(key));
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return b;
    }

    public static <T> void set(String key, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            jedis.set(keySerialize(key), bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> void set(byte[] k, byte[] v){
        Jedis jedis = jedisPool.getResource();
        try{
            jedis.set(k, v);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> void setex(String key, int expireSeconds, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            jedis.setex(keySerialize(key), expireSeconds, bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> boolean setnx(String key, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            long result = jedis.setnx(keySerialize(key), bytes);
            if(result == 1){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }

        return false;
    }

    public static <T> boolean setnxWithExpireTime(String key, int expireSeconds, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            long result = jedis.setnx(keySerialize(key), bytes);
            if(result == 1){
                jedis.setex(keySerialize(key), expireSeconds, bytes);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }

        return false;
    }

    public static byte[] get(byte[] key){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.get(key);
            return bytes;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> T get(String key, Class cla){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.get(keySerialize(key));
            if(bytes == null)return null;
            return JSON.parseObject(bytes, cla);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> T get(String key, TypeReference typeReference){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.get(keySerialize(key));
            if(bytes == null)return null;
            return (T)JSON.parseObject(new String(bytes, "UTF-8"), typeReference);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static Long incr(String key){
        Jedis jedis = jedisPool.getResource();
        try{
            Long along = jedis.incr(keySerialize(key));
            if(along == null)return null;
            return along;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> T lindex(String key, long index, Class cla){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.lindex(keySerialize(key), index);
            if(bytes == null)return null;
            return JSON.parseObject(bytes, cla);
        }catch (Exception e){
            logger.error(e.getMessage() + "," + key, e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> void lpush(String key, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            jedis.lpush(keySerialize(key), bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> void rpush(String key, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = valueSerialize(v);
            jedis.rpush(keySerialize(key), bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> T lpop(String key, Class cla){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.lpop(keySerialize(key));
            if(bytes == null)return null;
            return JSON.parseObject(bytes, cla);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> T rpop(String key, Class cla){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.rpop(keySerialize(key));
            if(bytes == null)return null;
            return JSON.parseObject(bytes, cla);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static <T> long lrem(String key, long count, T value){
        Jedis jedis = jedisPool.getResource();
        try{
            long number = jedis.lrem(keySerialize(key), count, valueSerialize(value));
            return number;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return -1;
    }

    public static <T> T rpop(String key, TypeReference typeReference){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = jedis.rpop(keySerialize(key));
            if(bytes == null)return null;
            return (T)JSON.parseObject(new String(bytes, "UTF-8"), typeReference);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static Long llen(String key){
        Jedis jedis = jedisPool.getResource();
        try{
            return jedis.llen(keySerialize(key));
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static List<byte[]> lrange(String key, int start, int end){
        Jedis jedis = jedisPool.getResource();
        try{
            List<byte[]> list = jedis.lrange(keySerialize(key),start,end);
            return list;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static long del(String key){
        Jedis jedis = jedisPool.getResource();
        try{
            return jedis.del(key);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return -1;
    }

    public static long decr(String key){
        Jedis jedis = jedisPool.getResource();
        try{
            return jedis.decr(key);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return -1;
    }


    public static <T> void zSet(String key, BigDecimal bigDecimal, T v){
        Jedis jedis = jedisPool.getResource();
        try{
            byte[] bytes = serialize(v);
            jedis.zadd(keySerialize(key),bigDecimal.doubleValue(),bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static long zcard(String key){
        Jedis jedis = jedisPool.getResource();
        long num = 0;
        try{
            byte[] bytes = keySerialize(key);
            num =jedis.zcard(bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return num;
    }

    public static <T> long zrank(String key, T v){
        Jedis jedis = jedisPool.getResource();
        long num = 0;
        try{
            byte[] bytes = valueSerialize(v);
            num = jedis.zrank(keySerialize(key), bytes);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return num;
    }


    public static void zremrangeByRank(String key,long start,long end){
        Jedis jedis = jedisPool.getResource();
        long num = 0;
        try{
            byte[] bytes = keySerialize(key);
            jedis.zremrangeByRank(bytes,start,end);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
    }

    public static <T> List<T> lrange(String key, long start, long end, Class clz){
        Jedis jedis = jedisPool.getResource();
        try{
            List<byte[]> bytesList = jedis.lrange(keySerialize(key), start, end);
            if(bytesList == null || bytesList.size() == 0) return null;
            List<T> tList = new ArrayList<>();
            for(byte[] bytes : bytesList){
                T tmp = JSON.parseObject(bytes, clz);
                tList.add(tmp);
            }
            return tList;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            jedis.close();
        }
        return null;
    }

    public static byte[] keySerialize(String key) {
        try {
            return key.getBytes(encode);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private static byte[] valueSerialize(Object object){
        byte[] bytes = JSON.toJSONBytes(object);
        return bytes;
    }

    private static byte[] serialize(Object object){
        byte[] bytes = JSON.toJSONBytes(object);
        return bytes;
    }

    public static void main(String[] args) {

    }
}
