package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.AdminFilterchainDefinition;
import com.jelly.eoss.shiro.EossShiroFilterFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/system/filterDefinition")
public class AdminFilterDefinitionAction extends BaseAction{
	@Autowired
	private BaseService baseService;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
	AbstractShiroFilter abstractShiroFilter;
    @Autowired
    EossShiroFilterFactoryBean eossShiroFilterFactoryBean;

	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AdminFilterchainDefinition filterDefinition = new AdminFilterchainDefinition();

        //try to get filterChainDefinition from shiro-spring-config
        String definition = eossShiroFilterFactoryBean.getFilterChainDefinitions();
        filterDefinition.setId(1).setDefinition(StringUtils.trimToEmpty(definition));

		request.setAttribute("filterDefinition", filterDefinition);
		return new ModelAndView("/system/filterDefinition.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminFilterchainDefinition filterDefinition) throws Exception{
		filterDefinition.setDefinition(StringUtils.trimToNull(filterDefinition.getDefinition()));

        //refresh filterChainDefinition
        if(filterDefinition.getDefinition() != null){
            eossShiroFilterFactoryBean.updateFilterChainDefinitions(filterDefinition.getDefinition(), abstractShiroFilter);
            baseService.myUpdate(AdminFilterchainDefinition.Update, filterDefinition.setId(1));
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
