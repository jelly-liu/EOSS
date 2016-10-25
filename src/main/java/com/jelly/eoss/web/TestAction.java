package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/test")
public class TestAction extends BaseAction{
	@Resource
	private BaseService baseDao;
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value = "abc")
	public void queryRowBounds(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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
