package com.jelly.eoss.listener;

import com.jelly.eoss.util.Const;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public class StartupApplicationContextListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(StartupApplicationContextListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try{
			ServletContext servletContext = servletContextEvent.getServletContext();
			ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

			String contextPath = servletContext.getContextPath();
            Const.BASE_PATH = contextPath;
			
			Const.jdbcTemplate = applicationContext.getBean("jdbcTemplate", JdbcTemplate.class);
			Const.sqlSessionTemplate = applicationContext.getBean("sqlSessionTemplate", SqlSessionTemplate.class);
			
			//D:/Workspaces5.5/struts2/WebRoot/
			Const.REAL_BASE_PATH = servletContext.getRealPath("/").replaceAll("\\\\", "/");
			
			//读取config.properties配置文件中的信息，并写入到CONSTANT类中
			Properties config = new Properties();
			config.load(servletContext.getResourceAsStream("/WEB-INF/classes/config.properties"));

			servletContext.setAttribute("BASE_PATH", Const.BASE_PATH);
			Const.PROJECT_NAME = config.getProperty("PROJECT_NAME").trim();
			servletContext.setAttribute("PROJECT_NAME", Const.PROJECT_NAME);
			Const.PROJECT_VERSION = config.getProperty("PROJECT_VERSION").trim();
			servletContext.setAttribute("PROJECT_VERSION", Const.PROJECT_VERSION);
			Const.ENABLE_SECURITY_FILTER = Boolean.parseBoolean(config.getProperty("ENABLE_SECURITY_FILTER").trim());
			servletContext.setAttribute("ENABLE_SECURITY_FILTER", Const.ENABLE_SECURITY_FILTER);
			Const.COPY_RIGHT = config.getProperty("COPY_RIGHT").trim();
			servletContext.setAttribute("COPY_RIGHT", Const.COPY_RIGHT);
			Const.PAGE_SIZE = Integer.parseInt(config.getProperty("PAGE_SIZE").trim());
			servletContext.setAttribute("COPY_RIGHT", Const.COPY_RIGHT);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
}
