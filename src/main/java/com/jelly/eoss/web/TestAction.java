package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.filter.MyFilter;
import com.jelly.eoss.model.User;
import com.jelly.eoss.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/test")
public class TestAction extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(TestAction.class);

	@Resource
	private BaseService baseDao;
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value = "updateSession")
	public void queryRowBounds(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		if(subject != null){
            subject.getSession().setAttribute("COUNTER", 1);
            int i = 0;
		}
	}
	
	//getter and setter
	public MenuService getMenuService() {
		return menuService;
	}
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public BaseService getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseDao = baseDao;
	}
}
