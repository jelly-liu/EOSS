package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminMenu;
import com.jelly.eoss.db.mapper.basic.iface.AdminMenuMapper;
import com.jelly.eoss.db.mapper.business.iface.MenuExtMapper;
import com.jelly.eoss.service.EossMenuService;
import com.jelly.eoss.util.ComUtil;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.DateUtil;
import com.jelly.eoss.util.Pager;
import com.jelly.eoss.web.BaseAction;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/menu")
public class AdminMenuAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(AdminMenuAction.class);

	@Autowired
	private EossMenuService eossMenuService;
	@Autowired
	private AdminMenuMapper menuMapper;
	@Autowired
	private MenuExtMapper menuExtMapper;

	//针对登录用户查询专用
	@RequestMapping(value = "/queryByUser")
	public void queryByUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			//该登录用户所拥有的所有菜单，key=menuId
			String idsOfLoginUserMenu = request.getSession().getAttribute(Const.LOGIN_MENU_TREE_IDS_KEY).toString();
			
			//所有一级菜单
			Map<String, Object> pm = this.getRequestMap(request);
			pm.put("inIds", idsOfLoginUserMenu);
			String jsonZTree = this.eossMenuService.queryMenuSub(pm);
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
		Map<String, Object> pm = this.getRequestMap(request);
		pm.put("inIds", idsOfLoginUserMenu);
		pm.put("openAll", "y");
		String jsonZTree = eossMenuService.queryMenuSub(pm);
		log.debug(jsonZTree);
		
		response.getWriter().write(jsonZTree);
		
		return null;
	}
	
	@RequestMapping(value = "/querySub")
	public ModelAndView querySub(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> pm = this.getRequestMap(request);
		String jsonZTree = this.eossMenuService.queryMenuSub(pm);
        log.debug(jsonZTree);
		request.setAttribute("jsonZTree", jsonZTree);
		
		return new ModelAndView("/layout/west.htm");
	}
	
	@RequestMapping(value = "/querySubAjax")
	public void querySubAjax(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String, Object> pm = this.getRequestMap(request);
		String jsonZTree = this.eossMenuService.queryMenuSub(pm);
		log.debug(jsonZTree);
		response.getWriter().write(jsonZTree);
	}
	
	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);


		Map<String, Object> param = this.getRequestMap(request);
		param.put("leaf", "0");
        Integer totalRow = menuExtMapper.queryMenuPageCount(param);

        RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
		List<Map<String, Object>> dataList = menuExtMapper.queryMenuPage(param);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		ModelAndView modelAndView = new ModelAndView("/system/menuList.htm");
		return modelAndView;
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws ServletException, IOException{
		return new ModelAndView("/system/menuAdd.htm");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu, RedirectAttributes redirectAttributes) throws ServletException, IOException{
		int id = ComUtil.QueryNextID("id", AdminMenu.TABLE_NAME);
		menu.setId(id);
		menu.setLeaf(0);
		menu.setPath(menu.getPath() + "#" + id);
		menu.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		menuMapper.insert(menu);

        redirectAttributes.addAttribute("id", menu.getId());
		ModelAndView view = new ModelAndView("redirect:/system/menu/toList");
		return view;
	}
	
	@RequestMapping(value = "/delete")
	public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		
		//不能删除根菜单
		if(id.equals("1")){
			response.getWriter().write("不能删除根菜单");
			return;
		}
		
//		Log.Debug("id:" + id);
		//有子菜单，不能删除
		int total = menuMapper.selectCount(new AdminMenu().setPid(id));
		if(total == 0){
			menuMapper.deleteByPk(id);
			response.getWriter().write("y");
		}else{
			response.getWriter().write("请先删除或移动，该菜单的所有子菜单和权限");
		}
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		AdminMenu mu = menuMapper.selectByPk(id);
		
		List<Map<String, Object>> subMenuList = menuExtMapper.queryAllSubMenu(mu.getId());
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
		Map<String, Object> pm = new HashMap();
		pm.put("notInIds", sb.toString());
		pm.put("checkedIds", String.valueOf(mu.getPid()));
		pm.put("openAll", "yes");
		pm.put("onlyParent", "true");
		String jsonStr = this.eossMenuService.queryMenuSub(pm);
//		Log.Debug(jsonStr);
		
		request.setAttribute("menu", mu);
		request.setAttribute("jsonZTree", jsonStr);
		
		return new ModelAndView("/system/menuUpdate.htm");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws ServletException, IOException{
		//如果是根菜单，则只能更新菜单名
		if(menu.getId() == 1){
			menu.setPid(-1);
		}
		
		menu.setLeaf(0);
		menuMapper.update(menu);

        ModelAndView modelAndView = new ModelAndView("redirect:/system/menu/toList?id=" + menu.getId());
		return modelAndView;
	}
}
