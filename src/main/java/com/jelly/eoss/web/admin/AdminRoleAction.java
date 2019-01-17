package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.*;
import com.jelly.eoss.db.mapper.basic.iface.AdminPermissionMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminRoleMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminRolePermissionMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserRoleMapper;
import com.jelly.eoss.db.mapper.business.iface.RoleExtMapper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/system/role")
public class AdminRoleAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(AdminRoleAction.class);

    @Autowired
    private AdminRoleMapper roleMapper;
    @Autowired
    private AdminPermissionMapper permissionMapper;
    @Autowired
    private AdminRolePermissionMapper rolePermissionMapper;
    @Autowired
    private AdminUserRoleMapper userRoleMapper;
    @Autowired
    private RoleExtMapper roleExtMapper;
    @Autowired
    EossAuthorizingRealm eossAuthorizingRealm;

    @RequestMapping(value = "/queryAllAjax")
    public void queryAllAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> roleList = roleExtMapper.queryRolePage(null);
        for (Map<String, Object> m : roleList) {
            m.put("pId", "-1");
            m.put("isParent", "false");
            m.put("iconSkin", "icon_eoss_role01");
        }
        String jsonStr = JsonUtil.toJson(roleList);
        log.debug(jsonStr);
        response.getWriter().write(jsonStr);
    }

    @RequestMapping(value = "/toList")
    public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);

        Map<String, Object> param = this.getRequestMap(request);
        Integer totalRow = roleExtMapper.queryRolePageCount(param);

        RowBounds rb = new RowBounds((page - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
        List<Map<String, Object>> dataList = roleExtMapper.queryRolePage(param);

        Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
        pager.setData(dataList);

        request.setAttribute("pager", pager);
        this.resetAllRequestParams(request);
        return new ModelAndView("/system/roleList.htm");
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<AdminPermission> permissionList = permissionMapper.select(null);

        Map<String, List<AdminPermission>> permMap = new TreeMap<>();
        for (AdminPermission perm : permissionList) {
            String firstPart = StringUtils.substringBefore(perm.getName(), ":");

            List<AdminPermission> tmpList = permMap.get(firstPart);
            if (tmpList == null) {
                tmpList = new LinkedList<>();
                permMap.put(firstPart, tmpList);
            }

            tmpList.add(perm);
        }

        for (List<AdminPermission> list : permMap.values()) {
            Collections.sort(list, Comparator.comparing(AdminPermission::getName));
        }

        request.setAttribute("permissionLList", permMap.values());
        return new ModelAndView("/system/roleAdd.htm");
    }

    @RequestMapping(value = "/add")
    public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminRole role) throws Exception {
        int id = ComUtil.QueryNextID("id", AdminRole.TABLE_NAME);
        String permissionIdsStr = request.getParameter("permissionIds");

        //插入角色
        role.setId(id);
        role.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
        roleMapper.insert(role);

        //插入角色对应的权限
        this.batchInsertRolePermission(role.getId(), permissionIdsStr);

        ModelAndView modelAndView = new ModelAndView("redirect:/system/role/toList?id=" + role.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/delete")
    public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");
        roleMapper.deleteByPk(id);
        rolePermissionMapper.deleteByPojo(new AdminRolePermission().setRoleId(id));
        userRoleMapper.deleteByPojo(new AdminUserRole().setRoleId(id));

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        AdminUser u = SecurityUtils.getSubject().getPrincipals().oneByType(AdminUser.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

        response.getWriter().write("y");
    }

    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        AdminRole role = roleMapper.selectByPk(id);
        List<Map<String, Object>> permissionList = roleExtMapper.queryAllPermissionWithRole(role.getId());

        Map<String, List<Map<String, Object>>> permMap = new TreeMap<>();
        for (Map<String, Object> perm : permissionList) {
            String firstPart = StringUtils.substringBefore(perm.get("name").toString(), ":");

            List<Map<String, Object>> tmpList = permMap.get(firstPart);
            if (tmpList == null) {
                tmpList = new LinkedList<>();
                permMap.put(firstPart, tmpList);
            }

            tmpList.add(perm);
        }

        for (List<Map<String, Object>> list : permMap.values()) {
            Collections.sort(list, Comparator.comparing(o -> o.get("name").toString()));
        }

        request.setAttribute("role", role);
        request.setAttribute("permissionLList", permMap.values());
        return new ModelAndView("/system/roleUpdate.htm");
    }

    @RequestMapping(value = "/update")
    public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminRole role) throws Exception {
        String permissionIdsStr = request.getParameter("permissionIds");

        //更新角色
        AdminRole r = roleMapper.selectByPk(role.getId());
        r.setName(role.getName());
        roleMapper.update(r);

        //更新角色原有权限
        this.batchInsertRolePermission(role.getId(), permissionIdsStr);
        request.getRequestDispatcher("/system/role/toList").forward(request, response);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        AdminUser u = SecurityUtils.getSubject().getPrincipals().oneByType(AdminUser.class);
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

        return new ModelAndView("redirect:/system/role/toList?id=" + role.getId());
    }

    //批量插入角色对应的权限，只选择用JdbcTemplate的批量更新方法，以保证高性能
    private void batchInsertRolePermission(int roleId, String permissionIdsStr) {
        rolePermissionMapper.deleteByPojo(new AdminRolePermission().setRoleId(roleId));

        //没有选择权限，直接返回
        if (permissionIdsStr == null || permissionIdsStr.trim().equals("")) {
            return;
        }

        //插入权限
        String[] permissionIds = permissionIdsStr.split(",");
        if (permissionIds.length > 0) {
            for (String permIdStr : permissionIds) {
                Integer permId = Integer.valueOf(permIdStr);
                rolePermissionMapper.insert(new AdminRolePermission().setRoleId(roleId).setPermissionId(permId));
            }
        }
    }
}
