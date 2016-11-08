package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.Permission;
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
@RequestMapping(value = "/system/permission")
public class PermissionAction extends BaseAction{
	@Resource
	private BaseService baseService;

	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_EXT.Permission_QueryPermissionPage_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.Permission_QueryPermissionPage", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/permissionList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, Permission permission) throws Exception{
		return new ModelAndView("/system/permissionAdd.jsp");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, Permission permission) throws Exception{
		int id = ComUtil.QueryNextID("id", "permission");
		permission.setId(id);
		this.baseService.myInsert(Permission.Insert, permission);
		request.getRequestDispatcher("/system/permission/toList.ac").forward(request, response);
		return null;
	}
	
	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
//		Log.Debug("id:" + id);
		this.baseService.myDelete(Permission.DeleteByPk, id);
		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("id");

		Permission permission = this.baseService.mySelectOne(Permission.SelectByPk, id);

		request.setAttribute("permission", permission);
		return new ModelAndView("/system/permissionUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Permission permission) throws ServletException, IOException{
		this.baseService.myUpdate(Permission.Update, permission);

		request.setAttribute("permission", permission);
        request.getRequestDispatcher("/system/permission/toList.ac").forward(request, response);
		return null;
	}
	
	//getter and setter
	public BaseService getBaseDao() {
		return baseService;
	}
}
