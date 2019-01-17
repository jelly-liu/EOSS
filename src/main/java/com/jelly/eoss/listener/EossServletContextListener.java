package com.jelly.eoss.listener;

import com.jelly.eoss.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Properties;

@WebListener
public class EossServletContextListener implements ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(EossServletContextListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try{
			ServletContext servletContext = servletContextEvent.getServletContext();
			Const.servletContext = servletContext;

			String contextPath = servletContext.getContextPath();
            Const.BASE_PATH = contextPath;
			
			//D:/Workspaces5.5/struts2/WebRoot/
			Const.REAL_BASE_PATH = servletContext.getRealPath("/").replaceAll("\\\\", "/");
			
			//读取config.properties配置文件中的信息，并写入到CONSTANT类中
			Properties config = new Properties();
			config.load(EossServletContextListener.class.getClassLoader().getResourceAsStream("config.properties"));

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
