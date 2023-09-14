package com.open.eoss.service.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.util.ComUtil;
import com.open.eoss.util.Const;
import com.open.eoss.web.config.SysLoginCheckInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;

/**
 * 用户登录后的必要信息缓存方式，meme or redis：
 *
 * mem方式:
 *  使用request.getSession().setAttribute()等方法
 *
 * redis方式:
 *  使用redis.setex()等方法
 *  需要 {@link SysLoginCheckInterceptor} 类配合，当用户有访问时，自动延长session时间
 */
@Service
public class SysSessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SysSessionManager.class);

    private String ttlDuration;
    private int sessionTimeout;//秒
    private static String KEY_PREFIX = "JSESSION_USER_";

    int session_cache_type;

    private static int session_cache_type_mem = 1;
    private static int session_cache_type_redis = 2;

    JedisPool jedisPool;

    @Autowired
    public SysSessionManager(JedisPool jedisPool,
                             @Value("${eoss.session_cache_type}") int session_cache_type,
                             @Value("${server.servlet.session.timeout}") String ttlDuration){
        this.session_cache_type = session_cache_type;
        this.jedisPool = jedisPool;
        //使用redis缓存
        if(session_cache_type == session_cache_type_redis){
            //解析出配置的session超时时间
            Duration duration = Duration.parse(ttlDuration);
            sessionTimeout = (int)duration.getSeconds();
        }
    }

    public String createKey(String sid){
        return KEY_PREFIX + sid;
    }

    public void setSessionInfo(HttpServletRequest request, SysSessionInfo sessionInfo) throws Exception {
        if (session_cache_type == session_cache_type_mem) {
            request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, sessionInfo);
        } else {
            try(Jedis jedis = jedisPool.getResource()) {
                String sid = ComUtil.parseJSessionId(request);
                jedis.setex(createKey(sid), sessionTimeout, JSON.toJSONString(sessionInfo));
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }
    }

    public SysSessionInfo getSessionInfo(HttpServletRequest request){
        SysSessionInfo sessionInfo = null;

        if(session_cache_type == session_cache_type_mem){
            sessionInfo = (SysSessionInfo) request.getSession().getAttribute(Const.LOGIN_SESSION_KEY);
        }else{
            String sid = ComUtil.parseJSessionId(request);
            if(StringUtils.isBlank(sid)){
                return null;
            }
            try(Jedis jedis = this.jedisPool.getResource()){
                String v = jedis.get(createKey(sid));
                if(StringUtils.isNotBlank(v)){
                    sessionInfo = JSON.parseObject(v, SysSessionInfo.class);
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }

        return sessionInfo;
    }

    public void removeSessionInfo(HttpServletRequest request){
        if(session_cache_type == session_cache_type_mem){
            request.getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
        }else{
            try(Jedis jedis = this.jedisPool.getResource()){
                String sid = ComUtil.parseJSessionId(request);
                if(StringUtils.isBlank(sid)){
                    return;
                }
                jedis.del(createKey(sid));
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }
    }

    //延长使用时间
    public void access(HttpServletRequest request){
        if(session_cache_type == session_cache_type_mem){
            //do nothing, 原生实现会自动access，延长使用时间s
        }else{
            String sid = ComUtil.parseJSessionId(request);
            if(StringUtils.isBlank(sid)){
                return;
            }

            try(Jedis jedis = this.jedisPool.getResource()){
                jedis.expire(createKey(sid), sessionTimeout);
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }
    }
}
