package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.Rule;
import com.jelly.eoss.util.ComUtil;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.Pager;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/rule")
public class RuleAction extends BaseAction{
	@Resource
	private BaseService baseService;

	@RequestMapping(value = "/query")
	public ModelAndView queryRulePage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_EXT.Rule_QueryRulePage_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.Rule_QueryRulePage_Body", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/ruleList.jsp");
	}
	
	@RequestMapping(value = "/Add")
	public ModelAndView addRule(HttpServletRequest request, HttpServletResponse response, Rule rule) throws Exception{
		int id = ComUtil.QueryNextID("id", "rule");
		rule.setId(id);
		this.baseService.myInsert(Rule.Insert, rule);
		return new ModelAndView("/system/ruleList.jsp");
	}
	
	@RequestMapping(value = "/delete")
	public void deleteRule(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		this.baseService.myDelete(Rule.DeleteByPk, id);
		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView updateRulePrepare(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");

		Rule rule = this.baseService.mySelectOne(Rule.SelectByPk, id);

		request.setAttribute("rule", rule);
		return new ModelAndView("/system/ruleUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView updateRule(HttpServletRequest request, HttpServletResponse response, Rule rule) throws ServletException, IOException{
		this.baseService.myUpdate(Rule.Update, rule);

		request.setAttribute("rule", rule);
		return new ModelAndView("/system/ruleList.jsp");
	}
	
	//getter and setter
	public BaseService getBaseDao() {
		return baseService;
	}
}
