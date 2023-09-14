package com.open.eoss.listener;

import com.open.eoss.util.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EossServletContextListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(EossServletContextListener.class);

	@Autowired
	Environment env;

	public void contextInitialized(ServletContextEvent event) {
		try{
			// 手动Autowire
			WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext())
					.getAutowireCapableBeanFactory().autowireBean(this);

			ServletContext servletContext = event.getServletContext();

			Const.BASE_PATH = servletContext.getContextPath();
			logger.info("init, base path={}", Const.BASE_PATH);
			
			//D:/Workspaces5.5/struts2/WebRoot/
			Const.REAL_BASE_PATH = servletContext.getRealPath("/").replaceAll("\\\\", "/");
			logger.info("init, real base path={}", Const.REAL_BASE_PATH);
			
			//读取config.properties配置文件中的信息，并写入到CONSTANT类中
			servletContext.setAttribute("BASE_PATH", Const.BASE_PATH);
			Const.PROJECT_NAME = env.getProperty("eoss.project_name");
			servletContext.setAttribute("PROJECT_NAME", Const.PROJECT_NAME);
			Const.PROJECT_VERSION = env.getProperty("eoss.project_version");
			servletContext.setAttribute("PROJECT_VERSION", Const.PROJECT_VERSION);
			Const.COPY_RIGHT = env.getProperty("eoss.copy_right");
			servletContext.setAttribute("COPY_RIGHT", Const.COPY_RIGHT);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
}
