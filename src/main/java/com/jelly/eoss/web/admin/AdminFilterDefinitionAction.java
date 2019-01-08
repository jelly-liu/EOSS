package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import com.jelly.eoss.service.basic.AdminFilterchainDefinitionService;
import com.jelly.eoss.shiro.EossShiroFilterFactoryBean;
import com.jelly.eoss.web.BaseAction;
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
public class AdminFilterDefinitionAction extends BaseAction {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
	AbstractShiroFilter abstractShiroFilter;
    @Autowired
    EossShiroFilterFactoryBean eossShiroFilterFactoryBean;
    @Autowired
    AdminFilterchainDefinitionService filterchainDefinitionService;

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
            filterchainDefinitionService.update(filterDefinition.setId(1));
        }

        request.getRequestDispatcher("/system/filterDefinition/toUpdate.ac").forward(request, response);
		return null;
	}
}
