package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.Menu;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.util.*;
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
@RequestMapping(value = "/system/menu")
public class MenuAction extends BaseAction{
    private static final Logger log = LoggerFactory.getLogger(MenuAction.class);

	@Resource
	private BaseService baseService;
	@Resource
	private MenuService menuService;
	
	//针对登录用户查询专用
	@RequestMapping(value = "/queryByUser")
	public void queryByUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			//该登录用户所拥有的所有菜单，key=menuId
			String idsOfLoginUserMenu = request.getSession().getAttribute(Const.LOGIN_MENU_TREE_IDS_KEY).toString();
			
			//所有一级菜单
			Map<String, String> pm = this.getRequestMap(request);
			pm.put("inIds", idsOfLoginUserMenu);
			String jsonZTree = this.menuService.queryMenuSub(pm);
			response.getWriter().write(jsonZTree);
		}catch(Exception e){
			response.getWriter().write("null");
			e.printStackTrace();
		}
	}
	
	//针对登录用户查询专用
	@RequestMapping(value = "/queryTreeByUser")
	public ModelAndView queryTreeByUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//该登录用户所拥有的所有菜单，key=menuId
		String idsOfLoginUserMenu = request.getSession().getAttribute(Const.LOGIN_MENU_TREE_IDS_KEY).toString();
		//该用户所捅有的菜单树
		Map<String, String> pm = this.getRequestMap(request);
		pm.put("inIds", idsOfLoginUserMenu);
		pm.put("openAll", "y");
		String jsonZTree = this.menuService.queryMenuSub(pm);
		log.debug(jsonZTree);
		
		response.getWriter().write(jsonZTree);
		
		return null;
	}
	
	@RequestMapping(value = "/querySub")
	public ModelAndView querySub(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> pm = this.getRequestMap(request);
		String jsonZTree = this.menuService.queryMenuSub(pm);
        log.debug(jsonZTree);
		request.setAttribute("jsonZTree", jsonZTree);
		
		return new ModelAndView("/layout/west.jsp");
	}
	
	@RequestMapping(value = "/querySubAjax")
	public void querySubAjax(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, String> pm = this.getRequestMap(request);
		String jsonZTree = this.menuService.queryMenuSub(pm);
		log.debug(jsonZTree);
		response.getWriter().write(jsonZTree);
	}
	
	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		param.put("leaf", "0");
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_EXT.Menu_QueryMenuPage_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.Menu_QueryMenuPage", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/menuList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, Menu menu) throws ServletException, IOException{
		return new ModelAndView("/system/menuAdd.jsp");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, Menu menu) throws ServletException, IOException{
		int id = ComUtil.QueryNextID("id", "menu");
		menu.setId(id);
		menu.setLeaf(0);
		menu.setPath(menu.getPath() + "#" + id);
		menu.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		this.baseService.myInsert(Menu.Insert, menu);

		request.getRequestDispatcher("/system/menu/toList.ac").forward(request, response);
		
		return null;
	}
	
	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = ServletRequestUtils.getStringParameter(request, "id");
		
		//不能删除根菜单
		if(id.equals("1")){
			response.getWriter().write("不能删除根菜单");
			return;
		}
		
//		Log.Debug("id:" + id);
		//有子菜单，不能删除
		String sql = "select count(*) total from menu where pid = ?";
		int total = this.baseService.getJdbcTemplate().queryForObject(sql, Integer.class, id);
		if(total == 0){
			this.baseService.getSqlSessionTemplate().delete(Menu.DeleteByPk, id);
			response.getWriter().write("y");
		}else{
			response.getWriter().write("请先删除或移动，该菜单的所有子菜单和权限");
		}
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");
		Menu mu = this.baseService.getSqlSessionTemplate().selectOne(Menu.SelectByPk, id);
		
		List<Map<String, Object>> subMenuList = this.baseService.mySelectList("_EXT.Menu_QueryAllSubMenu", mu.getId());
		//将所有id值拼接成1,2,3,4,5,6的形式
		StringBuilder sb = new StringBuilder();
		sb.append(id + ",");
		for(Map<String, Object> m : subMenuList){
			sb.append(m.get("id").toString() + ",");
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length() - 1);
		}
		
		//查询菜单
		Map<String, String> pm = new HashMap<String, String>();
		pm.put("notInIds", sb.toString());
		pm.put("checkedIds", String.valueOf(mu.getPid()));
		pm.put("openAll", "yes");
		pm.put("onlyParent", "true");
		String jsonStr = this.menuService.queryMenuSub(pm);
//		Log.Debug(jsonStr);
		
		request.setAttribute("menu", mu);
		request.setAttribute("jsonZTree", jsonStr);
		
		return new ModelAndView("/system/menuUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Menu menu) throws ServletException, IOException{
		//如果是根菜单，则只能更新菜单名
		if(menu.getId() == 1){
			menu.setPid(-1);
		}
		
		menu.setLeaf(0);
		this.baseService.getSqlSessionTemplate().update(Menu.Update, menu);

        request.getRequestDispatcher("/system/menu/toList.ac").forward(request, response);

		return null;
	}

	//getter and setter
	public MenuService getMenuService() {
		return menuService;
	}
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public BaseService getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseService baseDao) {
		this.baseService = baseDao;
	}
}
