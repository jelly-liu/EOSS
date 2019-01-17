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

	@Autowired
	private EossMenuService eossMenuService;
	@Autowired
	private AdminMenuMapper adminMenuMapper;
	@Autowired
	private MenuExtMapper menuExtMapper;
	
	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		Map<String, Object> param = this.getRequestMap(request);
		param.put("leaf", "1");
		Integer totalRow = menuExtMapper.queryMenuPageCount(param);

		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		param.put("rb", rb);
		List<Map<String, Object>> dataList = menuExtMapper.queryMenuPage(param);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/resourceList.htm");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws Exception{
		return new ModelAndView("/system/resourceAdd.htm");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws Exception{
		int id = ComUtil.QueryNextID("id", AdminMenu.TABLE_NAME);
		menu.setUrl(Const.BASE_PATH + menu.getUrl());
		menu.setId(id);
		menu.setLeaf(1);
		menu.setPath(menu.getPath() + "#" + id);
		menu.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		adminMenuMapper.insert(menu);

		return new ModelAndView("redirect:/system/resource/toList?id=" + menu.getId());
	}
	
	@RequestMapping(value = "/delete")
	public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Integer id = ServletRequestUtils.getIntParameter(request, "id");
		adminMenuMapper.deleteByPk(id);
		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		AdminMenu menu = adminMenuMapper.selectByPk(id);
		
		//装饰zTreeNode
		Map<String, Object> pm = new HashMap();
		pm.put("onlyParent", "yes");
		pm.put("openAll", "yes");
		pm.put("checkedIds", String.valueOf(menu.getPid()));
		pm.put("rootNocheck", "yes");
		String zTreeNodeJson = this.eossMenuService.queryMenuSub(pm);
		
		request.setAttribute("menu", menu);
		request.setAttribute("zTreeNodeJson", zTreeNodeJson);
		return new ModelAndView("redirect:/system/resourceUpdate?id=" + menu.getId());
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminMenu menu) throws ServletException, IOException{
		AdminMenu m = adminMenuMapper.selectByPk(menu.getId());
		m.setName(menu.getName());
		m.setTarget(menu.getTarget());
		m.setLev(menu.getLev());
		m.setPath(menu.getPath());
		m.setUrl(menu.getUrl());
		m.setPid(menu.getPid());
		adminMenuMapper.update(m);

		return new ModelAndView("redirect:/system/resource/toList?id" + menu.getId());
	}
}
