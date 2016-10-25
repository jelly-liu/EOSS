package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.FilterDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/system/filterDefinition")
public class FilterDefinitionAction extends BaseAction{
	@Resource
	private BaseService baseService;

	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		FilterDefinition filterDefinition = this.baseService.mySelectOne(FilterDefinition.SelectByPk, 1);
		request.setAttribute("filterDefinition", filterDefinition);
		return new ModelAndView("/system/filterDefinition.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, FilterDefinition filterDefinition) throws Exception{
		filterDefinition.setRule(StringUtils.trimToNull(filterDefinition.getRule()));

		this.baseService.myUpdate(FilterDefinition.UpdateWithNull, filterDefinition);
		return new ModelAndView("/system/filterDefinition/toUpdate.ac");
	}
	
	//getter and setter
	public BaseService getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseService = baseDao;
	}
}
