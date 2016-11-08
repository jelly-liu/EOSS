package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.FilterChainDefinition;
import com.jelly.eoss.shiro.EossShiroFilterFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/filterDefinition")
public class FilterDefinitionAction extends BaseAction{
	@Resource
	private BaseService baseService;
    @Resource
    EossShiroFilterFactoryBean eossShiroFilterFactoryBean;
    @Resource
    AbstractShiroFilter springShiroFilter;

	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		FilterChainDefinition filterDefinition = new FilterChainDefinition();

        //try to get filterChainDefinition from shiro-spring-config
        Map<String, String> filterChainDefinitionMap = eossShiroFilterFactoryBean.getFilterChainDefinitionMap();
        StringBuilder rule = new StringBuilder();
        for(Map.Entry<String, String> filterChainEntry : filterChainDefinitionMap.entrySet()){
            rule.append(filterChainEntry.getKey()).append("=").append(filterChainEntry.getValue()).append("\r\n");
        }
        filterDefinition.setId(1).setDefinition(StringUtils.trimToEmpty(rule.toString()));

		request.setAttribute("filterDefinition", filterDefinition);
		return new ModelAndView("/system/filterDefinition.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, FilterChainDefinition filterDefinition) throws Exception{
		filterDefinition.setDefinition(StringUtils.trimToNull(filterDefinition.getDefinition()));

        //refresh filterChainDefinition
        if(filterDefinition.getDefinition() != null){
            eossShiroFilterFactoryBean.refreshFilterChainDefinition(filterDefinition.getDefinition(), springShiroFilter);
        }

        request.getRequestDispatcher("/system/filterDefinition/toUpdate.ac").forward(request, response);
		return null;
	}
	
	//getter and setter
	public BaseService getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseService = baseDao;
	}
}
