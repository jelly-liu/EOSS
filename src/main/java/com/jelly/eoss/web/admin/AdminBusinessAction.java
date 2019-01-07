package com.jelly.eoss.web.admin;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/business/money")
public class AdminBusinessAction extends BaseAction {
	@Resource
	private BaseService baseService;

    @RequestMapping(value = "/list")
    public void center(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("/business/money/list");
    }

    @RequestMapping(value = "/add")
    public void main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("/business/money/add");
    }

    @RequestMapping(value = "/delete")
    public void northAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("/business/money/delete");
    }
	
	//getter and setter

	public BaseService getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseService = baseDao;
	}
}
