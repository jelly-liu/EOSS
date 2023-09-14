package com.open.eoss.listener;

import com.open.eoss.util.Const;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 12:45 AM 2019/1/6
 * @Description：
 * EossServletContextListener was created first, then EossApplicationListener was created second.
 */

@Service
public class EossApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            Const.sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
        }
    }
}
