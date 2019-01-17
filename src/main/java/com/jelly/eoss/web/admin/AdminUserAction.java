package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.entity.AdminUserMenu;
import com.jelly.eoss.db.entity.AdminUserRole;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserRoleMapper;
import com.jelly.eoss.db.mapper.business.iface.RoleExtMapper;
import com.jelly.eoss.db.mapper.business.iface.UserExtMapper;
import com.jelly.eoss.service.EossMenuService;
import com.jelly.eoss.shiro.EossAuthorizingRealm;
import com.jelly.eoss.util.*;
import com.jelly.eoss.web.BaseAction;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/system/user")
public class AdminUserAction extends BaseAction {
    @Autowired
    EossMenuService eossMenuService;
    @Autowired
    AdminUserMapper userMapper;
    @Autowired
    private RoleExtMapper roleExtMapper;
    @Autowired
    UserExtMapper userExtMapper;
    @Autowired
    AdminUserRoleMapper userRoleMapper;
    @Autowired
    AdminUserMenuMapper userMenuMapper;
    @Autowired
    EossAuthorizingRealm eossAuthorizingRealm;
    @Autowired
    SecureRandomNumberGenerator secureRandomNumberGenerator;

    @RequestMapping(value = "/queryUserNameAjax")
    public void queryUserNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String name = request.getParameter("name");
            int total = userMapper.selectCount(new AdminUser().setUsername(name));
            if (total == 0) {
                response.getWriter().write("y");
            } else {
                response.getWriter().write("该用户名已被占用");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @RequestMapping(value = "/toList")
    public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);

        Map<String, Object> param = this.getRequestMap(request);
        Integer totalRow = userExtMapper.queryUserCount(param);

        RowBounds rb = new RowBounds((page - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
        List<Map<String, Object>> dataList = userExtMapper.queryUserPage(param);

        Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
        pager.setData(dataList);

        request.setAttribute("pager", pager);
        this.resetAllRequestParams(request);
        return new ModelAndView("/system/userList.htm");
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminUser user) throws Exception {
        return new ModelAndView("/system/userAdd.htm");
    }

    @RequestMapping(value = "/add")
    public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminUser user) throws Exception {
        String roleIds = request.getParameter("roleIds");
        String resourcesIds = request.getParameter("resourcesIds");
        ModelAndView mv = new ModelAndView();

        //查询用户名是否存在
        int total = userMapper.selectCount(new AdminUser().setUsername(user.getUsername()));
        if (total != 0) {
            request.setAttribute("INFO", "该用户名已存在，请选择一个新的用户名");
            mv.setViewName("/info.htm");
            return mv;
        }

        int id = ComUtil.QueryNextID("id", AdminUser.TABLE_NAME);

        //加密密码
        String salt = secureRandomNumberGenerator.nextBytes(32).toString();
        String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1).toString();

        //插入用户
        user.setId(id);
        user.setSalt(salt);
        user.setPassword(passwordMd5);
        user.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
        userMapper.insert(user);

        //插入角色
        this.batchInsertUserRole(user.getId(), roleIds);
        //插入资源
        this.batchInsertUserResource(user.getId(), resourcesIds);

        ModelAndView modelAndView = new ModelAndView("redirect:/system/user/toList?id=" + user.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        //查询自己
        AdminUser user = userMapper.selectByPk(id);

        //查询该用户已拥有的角色
        List<AdminUserRole> roleOldList = userRoleMapper.select(new AdminUserRole().setUserId(id));
        Set<String> roleOldSet = new HashSet<String>();
        for (AdminUserRole m : roleOldList) {
            roleOldSet.add(m.getRoleId()+"");
        }

        //设置初始化选中的角色
        List<Map<String, Object>> roleList = roleExtMapper.queryRolePage(null);
        for (Map<String, Object> m : roleList) {
            m.put("pId", "-1");
            m.put("isParent", "false");
            m.put("iconSkin", "icon_eoss_role01");
            if (roleOldSet.contains(m.get("id").toString())) {
                m.put("checked", "true");
            }
        }
        String zTreeNodeJson = JsonUtil.toJson(roleList);

        //将该角色已有菜单资源用逗号连接成一个字符串，如1,2,3,4,5,6
        List<AdminUserMenu> resourceIdsOldList = userMenuMapper.select(new AdminUserMenu().setUserId(id));
        StringBuilder sb = new StringBuilder();
        for (AdminUserMenu m : resourceIdsOldList) {
            sb.append(m.getMenuId()+",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        //装饰zTreeNode
        Map<String, Object> pm = new HashMap();
        pm.put("onlyLeafCanCheck", "yes");
        pm.put("openAll", "yes");
        pm.put("checkedIds", sb.toString());
        pm.put("withoutUrl", "y");
        String zTreeNodeJsonResource = this.eossMenuService.queryMenuSub(pm);

        request.setAttribute("zTreeNodeJson", zTreeNodeJson);
        request.setAttribute("zTreeNodeJsonResource", zTreeNodeJsonResource);
        request.setAttribute("user", user);
        return new ModelAndView("/system/userUpdate.htm");
    }

    @RequestMapping(value = "/update")
    public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminUser user) throws Exception {
        //加密密码
        String salt = secureRandomNumberGenerator.nextBytes(32).toString();
        String passwordMd5 = new Md5Hash(user.getPassword(), salt, 1).toString();

        //更新用户信息
        AdminUser adminUser = userMapper.selectByPk(user.getId());
        adminUser.setUsername(user.getUsername());
        adminUser.setSalt(salt);
        adminUser.setPassword(passwordMd5);
        userMapper.update(adminUser);

        //更新角色
        String roleIds = request.getParameter("roleIds");
        this.batchInsertUserRole(adminUser.getId(), roleIds);

        //更新资源
        String resourceIds = request.getParameter("resourceIds");
        this.batchInsertUserResource(adminUser.getId(), resourceIds);
        request.getRequestDispatcher("/system/user/toList").forward(request, response);

        //更新用户所拥有的菜单缓存，一但用户更新了自己所拥有的菜单，再刷新左侧的菜单时，菜单可以及时的更新
        String menuTreeIdsOfUser = this.eossMenuService.txQueryMenuTreeIdsOfUser(adminUser);
        request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, menuTreeIdsOfUser);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), adminUser);

        return null;
    }

    //批量插入用户对应的角色，只选择用JdbcTemplate的批量更新方法，以保证高性能
    private void batchInsertUserRole(Integer userId, String roleIdsStr) {
        userRoleMapper.deleteByPojo(new AdminUserRole().setUserId(userId));

        //没有选择角色，直接返回
        if (roleIdsStr == null || roleIdsStr.trim().equals("")) {
            return;
        }

        //插入角色
        String[] permissionIds = roleIdsStr.split(",");
        if (permissionIds.length > 0) {
            for (String roleIdStr : permissionIds) {
                Integer roleId = Integer.valueOf(roleIdStr);
                userRoleMapper.insert(new AdminUserRole().setUserId(userId).setRoleId(roleId));
            }
        }
    }

    //批量插入用户对应的资源，只选择用JdbcTemplate的批量更新方法，以保证高性能
    private void batchInsertUserResource(int userId, String resourceIdsStr) {
        userMenuMapper.deleteByPojo(new AdminUserMenu().setUserId(userId));

        //没有选择资源，直接返回
        if (resourceIdsStr == null || resourceIdsStr.trim().equals("")) {
            return;
        }

        //插入资源
        String[] resourceIds = resourceIdsStr.split(",");
        if (resourceIds.length > 0) {
            for (String resourceIdStr : resourceIds) {
                Integer resourceId = Integer.valueOf(resourceIdStr);
                userMenuMapper.insert(new AdminUserMenu().setUserId(userId).setMenuId(resourceId));
            }
        }
    }

    @RequestMapping(value = "/delete")
    public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        //删除自己
        userMapper.deleteByPk(id);

        //删除对应的角色
        userRoleMapper.deleteByPojo(new AdminUserRole().setUserId(id));

        //删除对应的资源
        userMenuMapper.deleteByPojo(new AdminUserMenu().setUserId(id));

        response.getWriter().write("y");
    }

    public static void main(String[] args) {
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String salt = secureRandomNumberGenerator.nextBytes(32).toString();
        String password = "111111";

        Md5Hash md5Hash = new Md5Hash(password, salt, 1);
        String passwordHashed = md5Hash.toString();

        System.out.println("password=" + password + ", salt=" + salt.length() + ", passwordHashed=" + passwordHashed);
    }
}
