package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminPermission;
import com.jelly.eoss.db.mapper.business.iface.PermissionExtMapper;
import com.jelly.eoss.service.basic.AdminPermissionService;
import com.jelly.eoss.util.ComUtil;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.Pager;
import com.jelly.eoss.web.BaseAction;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/permission")
public class AdminPermissionAction extends BaseAction {
	@Autowired
	private AdminPermissionService permissionService;
	@Autowired
	private PermissionExtMapper permissionExtMapper;

	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, Object> param = this.getRequestMap(request);
		Integer totalRow = permissionExtMapper.queryPermissionPageCount(param);

        RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
		List<Map<String, Object>> dataList = permissionExtMapper.queryPermissionPage(param);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/permissionList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminPermission permission) throws Exception{
		return new ModelAndView("/system/permissionAdd.jsp");
	}
	
	@RequestMapping(value = "/add")
	public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminPermission permission) throws Exception{
		int id = ComUtil.QueryNextID("id", "admin_permission");
		permission.setId(id);
		permissionService.insert(permission);
		request.getRequestDispatcher("/system/permission/toList.ac").forward(request, response);
		return null;
	}

    @RequestMapping(value = "/addAjax")
    public void txAddAjax(HttpServletRequest request, HttpServletResponse response, AdminPermission permission) throws Exception{
        int id = ComUtil.QueryNextID("id", "admin_permission");
        permission.setId(id);
        permissionService.insert(permission);
        response.getWriter().write("y");
    }
	
	@RequestMapping(value = "/delete")
	public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		permissionService.deleteByPk(id);
		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");

		AdminPermission permission = permissionService.selectByPk(id);

		request.setAttribute("permission", permission);
		return new ModelAndView("/system/permissionUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminPermission permission) throws ServletException, IOException{
		permissionService.update(permission);

		request.setAttribute("permission", permission);
        request.getRequestDispatcher("/system/permission/toList.ac").forward(request, response);
		return null;
	}

	@RequestMapping(value = "/updateAjax")
	public void txUpdateAjax(HttpServletRequest request, HttpServletResponse response, AdminPermission permission) throws ServletException, IOException{
		permissionService.update(permission);
		response.getWriter().write("y");
	}
}
