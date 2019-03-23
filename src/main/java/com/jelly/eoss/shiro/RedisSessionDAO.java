package com.jelly.eoss.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:44 AM 2019/3/21
 * @Description：${description}
 */

public class RedisSessionDAO extends EnterpriseCacheSessionDAO {
    private static final Logger log = LoggerFactory.getLogger(RedisSessionDAO.class);

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        super.setCacheManager(cacheManager);
    }

    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        try {
            return super.readSession(sessionId);
        }catch (UnknownSessionException use){
            log.warn("readSession warn", use.getMessage());
        }catch (Exception e){
            log.error("readSession error", e.getMessage());
        }
        return null;
    }
}
