package com.jelly.eoss.web.admin;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.AdminMenu;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.util.*;
import com.jelly.eoss.web.BaseAction;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/resource")
public class AdminResourceAction extends BaseAction {
	private static final Logger log = LoggerFactory.getLogger(AdminResourceAction.class);

	@Resource
	private BaseService baseService;
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		param.put("leaf", "1");
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_EXT.Menu_QueryMenuPage_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.Menu_QueryMenuPage", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/resourceList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws Exception{
		return new ModelAndView("/system/resourceAdd.jsp");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws Exception{
		int id = ComUtil.QueryNextID("id", "admin_menu");
		menu.setUrl(Const.BASE_PATH + menu.getUrl());
		menu.setId(id);
		menu.setLeaf(1);
		menu.setPath(menu.getPath() + "#" + id);
		menu.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		this.baseService.myInsert(AdminMenu.Insert, menu);
		log.debug(menu.getTarget());
		return new ModelAndView("/system/resource/toList.ac");
	}
	
	@RequestMapping(value = "/delete")
	public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
//		Log.Debug("id:" + id);
		this.baseService.myDelete(AdminMenu.DeleteByPk, id);
		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		AdminMenu menu = this.baseService.mySelectOne(AdminMenu.SelectByPk, id);
		
		//装饰zTreeNode
		Map<String, String> pm = new HashMap<String, String>();
		pm.put("onlyParent", "yes");
		pm.put("openAll", "yes");
		pm.put("checkedIds", String.valueOf(menu.getPid()));
		pm.put("rootNocheck", "yes");
		String zTreeNodeJson = this.menuService.queryMenuSub(pm);
		
		request.setAttribute("menu", menu);
		request.setAttribute("zTreeNodeJson", zTreeNodeJson);
		return new ModelAndView("/system/resourceUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws ServletException, IOException{
		AdminMenu m = this.baseService.mySelectOne(AdminMenu.SelectByPk, menu.getId());
		m.setName(menu.getName());
		m.setTarget(menu.getTarget());
		m.setLev(menu.getLev());
		m.setPath(menu.getPath());
		m.setUrl(menu.getUrl());
		m.setPid(menu.getPid());
		this.baseService.myUpdate(AdminMenu.Update, m);
		return new ModelAndView("/system/resource/toList.ac");
	}
	
	//getter and setter
	public BaseService getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseService = baseDao;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}
