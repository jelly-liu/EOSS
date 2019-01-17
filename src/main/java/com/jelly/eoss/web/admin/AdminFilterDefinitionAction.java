package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import com.jelly.eoss.db.mapper.basic.iface.AdminFilterchainDefinitionMapper;
import com.jelly.eoss.shiro.EossShiroFilterFactoryBean;
import com.jelly.eoss.web.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
    AdminFilterchainDefinitionMapper filterchainDefinitionMapper;

	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AdminFilterchainDefinition filterDefinition = new AdminFilterchainDefinition();

        Integer id = 1;

        //try to get filterChainDefinition from shiro-spring-config
        String definition = eossShiroFilterFactoryBean.getFilterChainDefinitions();
        filterDefinition.setId(id).setDefinition(StringUtils.trimToEmpty(definition));

		request.setAttribute("filterDefinition", filterDefinition);
		return new ModelAndView("/system/filterDefinition.htm");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminFilterchainDefinition filterDefinition) throws Exception{
		filterDefinition.setDefinition(StringUtils.trimToNull(filterDefinition.getDefinition()));

        Integer id = 1;

        //refresh filterChainDefinition
        if(filterDefinition.getDefinition() != null){
            eossShiroFilterFactoryBean.updateFilterChainDefinitions(filterDefinition.getDefinition(), abstractShiroFilter);
            filterchainDefinitionMapper.update(filterDefinition.setId(1));
        }

        return new ModelAndView("redirect:/system/filterDefinition/toUpdate?id=" + id);
	}
}
