package com.open.eoss.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class EossHttpSessionListener implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(EossHttpSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("session create, sid={}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("session destroyed, sid={}", se.getSession().getId());
    }
}
