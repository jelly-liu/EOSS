package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.Permission;
import com.jelly.eoss.model.Role;
import com.jelly.eoss.model.User;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.shiro.EossAuthorizingRealm;
import com.jelly.eoss.util.*;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/role")
public class RoleAction extends BaseAction{
	private static final Logger log = LoggerFactory.getLogger(RoleAction.class);

	@Resource
	private BaseService baseService;
	@Resource
	private MenuService menuService;
	@Resource
	EossAuthorizingRealm eossAuthorizingRealm;

	@RequestMapping(value = "/queryAllAjax")
	public void queryAllAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> roleList = this.baseService.mySelectList("_EXT.Role_QueryRolePage");
		for(Map<String, Object> m : roleList){
			m.put("pId", "-1");
			m.put("isParent", "false");
			m.put("iconSkin", "icon_eoss_role01");
		}
		String jsonStr = JsonUtil.toJson(roleList);
		log.debug(jsonStr);
		response.getWriter().write(jsonStr);
	}
	
	@RequestMapping(value = "/toList")
	public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_EXT.Role_QueryRolePage_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.Role_QueryRolePage", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/roleList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Permission> permissionList = this.baseService.mySelectList(Permission.Select);
		request.setAttribute("permissionList", permissionList);
		return new ModelAndView("/system/roleAdd.jsp");
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response, Role role) throws Exception{
		int id = ComUtil.QueryNextID("id", "role");
		String permissionIdsStr = request.getParameter("permissionIds");
		
		//插入角色
		role.setId(id);
		role.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		baseService.myInsert(Role.Insert, role);
		
		//插入角色对应的权限
		this.batchInsertRolePermission(role.getId(), permissionIdsStr);

		request.getRequestDispatcher("/system/role/toList.ac").forward(request, response);
		return null;
	}
	
	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		this.baseService.jdDelete("delete from role where id = ?", id);
		this.baseService.jdDelete("delete from role_permission where role_id = ?", id);
		this.baseService.jdDelete("delete from user_role where role_id = ?", id);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        User u = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");

		Role role = this.baseService.mySelectOne(Role.SelectByPk, id);
        List<Map<String, Object>> permissionList = this.baseService.mySelectList("_EXT.Role_QueryAllPermissionWithRole", role.getId());

		request.setAttribute("role", role);
        request.setAttribute("permissionList", permissionList);
		return new ModelAndView("/system/roleUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Role role) throws Exception{
		String permissionIdsStr = request.getParameter("permissionIds");
		
		//更新角色
		Role r = this.baseService.mySelectOne(Role.SelectByPk, role.getId());
		r.setName(role.getName());
		this.baseService.myUpdate(Role.Update, r);
		
		//更新角色原有权限
		this.batchInsertRolePermission(role.getId(), permissionIdsStr);
		request.getRequestDispatcher("/system/role/toList.ac").forward(request, response);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        User u = SecurityUtils.getSubject().getPrincipals().oneByType(User.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

		return null;
	}
	
	//批量插入角色对应的权限，只选择用JdbcTemplate的批量更新方法，以保证高性能
	private void batchInsertRolePermission(int roleId, String permissionIdsStr){
		String sqlDelete = "delete from role_permission where role_id = ?";
		this.baseService.jdDelete(sqlDelete, roleId);
		
		//没有选择权限，直接返回
		if(permissionIdsStr == null || permissionIdsStr.trim().equals("")){
			return;
		}
		
		//插入权限
		String[] permissionIds = permissionIdsStr.split(",");
		if(permissionIds.length > 0){
			String sqlInsert = "insert into role_permission (role_id, permission_id) values (?, ?)";
			Object[] objs = null;
			List<Object[]> batchParams = new ArrayList<Object[]>();
			for(String permissionId : permissionIds){
				objs = new Object[2];
				objs[0] = roleId;
				objs[1] = permissionId;
				batchParams.add(objs);
			}
			this.baseService.jdBatchUpdate(sqlInsert, batchParams);
		}
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
