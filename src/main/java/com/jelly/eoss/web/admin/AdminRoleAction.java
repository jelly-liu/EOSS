package com.jelly.eoss.web.admin;

import com.jelly.eoss.dao.BaseDao;
import com.jelly.eoss.db.entity.*;
import com.jelly.eoss.db.mapper.ext.iface.RoleExtMapper;
import com.jelly.eoss.service.EossMenuService;
import com.jelly.eoss.service.basic.AdminPermissionService;
import com.jelly.eoss.service.basic.AdminRolePermissionService;
import com.jelly.eoss.service.basic.AdminRoleService;
import com.jelly.eoss.service.basic.AdminUserRoleService;
import com.jelly.eoss.shiro.EossAuthorizingRealm;
import com.jelly.eoss.util.*;
import com.jelly.eoss.web.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/system/role")
public class AdminRoleAction extends BaseAction {
	private static final Logger log = LoggerFactory.getLogger(AdminRoleAction.class);

	@Resource
	private BaseDao baseService;
	@Resource
	private EossMenuService eossMenuService;
	@Autowired
	private AdminRoleService roleService;
	@Autowired
	private AdminPermissionService permissionService;
	@Autowired
	private AdminRolePermissionService rolePermissionService;
	@Autowired
	private AdminUserRoleService userRoleService;
	@Autowired
	private RoleExtMapper roleExtMapper;
	@Resource
	EossAuthorizingRealm eossAuthorizingRealm;

	@RequestMapping(value = "/queryAllAjax")
	public void queryAllAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> roleList = roleExtMapper.queryRolePage(null);
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
		
		Map<String, Object> param = this.getRequestMap(request);
		Integer totalRow = roleExtMapper.queryRolePageCount(param);

        RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
		List<Map<String, Object>> dataList = roleExtMapper.queryRolePage(param);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/roleList.jsp");
	}

	@RequestMapping(value = "/toAdd")
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<AdminPermission> permissionList = permissionService.select(null);

		Map<String, List<AdminPermission>> permMap = new TreeMap<>();
		for(AdminPermission perm : permissionList){
		    String firstPart = StringUtils.substringBefore(perm.getName(), ":");

            List<AdminPermission> tmpList = permMap.get(firstPart);
            if(tmpList == null){
                tmpList = new LinkedList<>();
                permMap.put(firstPart, tmpList);
            }

            tmpList.add(perm);
        }

        for(List<AdminPermission> list : permMap.values()){
            Collections.sort(list, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        }

		request.setAttribute("permissionLList", permMap.values());
		return new ModelAndView("/system/roleAdd.jsp");
	}

	@RequestMapping(value = "/add")
	public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminRole role) throws Exception{
		int id = ComUtil.QueryNextID("id", "admin_role");
		String permissionIdsStr = request.getParameter("permissionIds");
		
		//插入角色
		role.setId(id);
		role.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		roleService.insert(role);
		
		//插入角色对应的权限
		this.batchInsertRolePermission(role.getId(), permissionIdsStr);

		request.getRequestDispatcher("/system/role/toList.ac").forward(request, response);
		return null;
	}
	
	@RequestMapping(value = "/delete")
	public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		roleService.deleteByPk(id);
		rolePermissionService.deleteByPojo(new AdminRolePermission().setRoleId(id));
		userRoleService.deleteByPojo(new AdminUserRole().setRoleId(id));

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        AdminUser u = SecurityUtils.getSubject().getPrincipals().oneByType(AdminUser.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

		response.getWriter().write("y");
	}
	
	@RequestMapping(value = "/toUpdate")
	public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer id = ServletRequestUtils.getIntParameter(request, "id");

		AdminRole role = roleService.selectByPk(id);
        List<Map<String, Object>> permissionList = roleExtMapper.queryAllPermissionWithRole(role.getId());

        Map<String, List<Map<String, Object>>> permMap = new TreeMap<>();
        for(Map<String, Object> perm : permissionList){
            String firstPart = StringUtils.substringBefore(perm.get("name").toString(), ":");

            List<Map<String, Object>> tmpList = permMap.get(firstPart);
            if(tmpList == null){
                tmpList = new LinkedList<>();
                permMap.put(firstPart, tmpList);
            }

            tmpList.add(perm);
        }

        for(List<Map<String, Object>> list : permMap.values()){
            Collections.sort(list, (o1, o2) -> o1.get("name").toString().compareTo(o2.get("name").toString()));
        }

		request.setAttribute("role", role);
        request.setAttribute("permissionLList", permMap.values());
		return new ModelAndView("/system/roleUpdate.jsp");
	}
	
	@RequestMapping(value = "/update")
	public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminRole role) throws Exception{
		String permissionIdsStr = request.getParameter("permissionIds");
		
		//更新角色
		AdminRole r = roleService.selectByPk(role.getId());
		r.setName(role.getName());
		roleService.update(r);
		
		//更新角色原有权限
		this.batchInsertRolePermission(role.getId(), permissionIdsStr);
		request.getRequestDispatcher("/system/role/toList.ac").forward(request, response);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        AdminUser u = SecurityUtils.getSubject().getPrincipals().oneByType(AdminUser.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

		return null;
	}

	//批量插入角色对应的权限，只选择用JdbcTemplate的批量更新方法，以保证高性能
	private void batchInsertRolePermission(int roleId, String permissionIdsStr){
		rolePermissionService.deleteByPojo(new AdminRolePermission().setRoleId(roleId));
		
		//没有选择权限，直接返回
		if(permissionIdsStr == null || permissionIdsStr.trim().equals("")){
			return;
		}
		
		//插入权限
		String[] permissionIds = permissionIdsStr.split(",");
		if(permissionIds.length > 0){
			String sqlInsert = "insert into admin_role_permission (role_id, permission_id) values (?, ?)";
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
	public BaseDao getBaseDao() {
		return baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseService = baseDao;
	}
	
	public EossMenuService getEossMenuService() {
		return eossMenuService;
	}

	public void setEossMenuService(EossMenuService eossMenuService) {
		this.eossMenuService = eossMenuService;
	}

}
