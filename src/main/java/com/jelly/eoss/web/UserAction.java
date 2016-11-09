package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.shiro.EossAuthorizingRealm;
import com.jelly.eoss.util.*;
import com.jelly.eoss.util.security.Digest;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/system/user")
public class UserAction extends BaseAction {
    @Resource
    private BaseService baseService;
    @Resource
    private MenuService menuService;
    @Resource
    EossAuthorizingRealm eossAuthorizingRealm;

    @RequestMapping(value = "/queryUserNameAjax")
    public void queryUserNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String name = request.getParameter("name");
            int total = this.baseService.jdQueryForInt("select count(*) total from user where name = ?", name);
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

        Map<String, String> param = this.getRequestMap(request);
        RowBounds rb = new RowBounds((page - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);

        Integer totalRow = this.baseService.mySelectOne("_EXT.User_QueryUser_Count", param);
        List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_EXT.User_QueryUser_Page", param, rb);

        Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
        pager.setData(dataList);

        request.setAttribute("pager", pager);
        this.resetAllRequestParams(request);
        return new ModelAndView("/system/userList.jsp");
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        return new ModelAndView("/system/userAdd.jsp");
    }

    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        String roleIds = request.getParameter("roleIds");
        String resourcesIds = request.getParameter("resourcesIds");
        ModelAndView mv = new ModelAndView();

        //查询用户名是否存在
        int total = this.baseService.jdQueryForInt("select count(*) total from user where username = ?", user.getUsername());
        if (total != 0) {
            request.setAttribute("INFO", "该用户名已存在，请选择一个新的用户名");
            mv.setViewName("/info.jsp");
            return mv;
        }

        int id = ComUtil.QueryNextID("id", "user");

        //插入用户
        user.setId(id);
        user.setSalt(new Random().nextInt(1000) + "");
        user.setPassword(Digest.GetMD5(user.getPassword() + user.getSalt()));
        user.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
        this.baseService.myInsert(User.Insert, user);

        //插入角色
        this.batchInsertUserRole(user.getId(), roleIds);
        //插入资源
        this.batchInsertUserResource(user.getId(), resourcesIds);
        request.getRequestDispatcher("/system/user/toList.ac").forward(request, response);
        return null;
    }

    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");

        //查询自己
        User user = this.baseService.mySelectOne(User.SelectByPk, id);

        //查询该用户已拥有的角色
        String sql = "select * from user_role where user_id = ?";
        List<Map<String, Object>> roleOldList = this.baseService.jdQueryForList(sql, id);
        Set<String> roleOldSet = new HashSet<String>();
        for (Map<String, Object> m : roleOldList) {
            roleOldSet.add(m.get("role_id").toString());
        }

        //设置初始化选中的角色
        List<Map<String, Object>> roleList = this.baseService.mySelectList("_EXT.Role_QueryRolePage");
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
        String sqlMenu = "select menu_id as id from user_menu where user_id = ?";
        List<Map<String, Object>> resourceIdsOldList = this.baseService.jdQueryForList(sqlMenu, id);
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> m : resourceIdsOldList) {
            sb.append(m.get("id").toString() + ",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        //装饰zTreeNode
        Map<String, String> pm = new HashMap<String, String>();
        pm.put("onlyLeafCanCheck", "yes");
        pm.put("openAll", "yes");
        pm.put("checkedIds", sb.toString());
        pm.put("withoutUrl", "y");
        String zTreeNodeJsonResource = this.menuService.queryMenuSub(pm);

        request.setAttribute("zTreeNodeJson", zTreeNodeJson);
        request.setAttribute("zTreeNodeJsonResource", zTreeNodeJsonResource);
        request.setAttribute("user", user);
        return new ModelAndView("/system/userUpdate.jsp");
    }

    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        //更新用户信息
        User u = this.baseService.mySelectOne(User.SelectByPk, user.getId());
        u.setUsername(user.getUsername());
        user.setSalt(new Random().nextInt(1000) + "");
        user.setPassword(Digest.GetMD5(user.getPassword() + user.getSalt()));
        this.baseService.myUpdate(User.Update, u);

        //更新角色
        String roleIds = request.getParameter("roleIds");
        this.batchInsertUserRole(user.getId(), roleIds);

        //更新资源
        String resourceIds = request.getParameter("resourceIds");
        this.batchInsertUserResource(user.getId(), resourceIds);
        request.getRequestDispatcher("/system/user/toList.ac").forward(request, response);

        //更新用户所拥有的菜单缓存，一但用户更新了自己所拥有的菜单，再刷新左侧的菜单时，菜单可以及时的更新
        String menuTreeIdsOfUser = this.menuService.queryMenuTreeIdsOfUser(user);
        request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, menuTreeIdsOfUser);

        //更新shiro AuthenticationInfo and AuthorizationInfo in local cache
        eossAuthorizingRealm.refreshAuthInfo(SecurityUtils.getSubject().getPrincipals(), u);

        return null;
    }

    //批量插入用户对应的角色，只选择用JdbcTemplate的批量更新方法，以保证高性能
    private void batchInsertUserRole(Integer userId, String roleIdsStr) {
        String sqlDelete = "delete from user_role where user_id = ?";
        this.baseService.jdDelete(sqlDelete, userId);

        //没有选择角色，直接返回
        if (roleIdsStr == null || roleIdsStr.trim().equals("")) {
            return;
        }

        //插入角色
        String[] permissionIds = roleIdsStr.split(",");
        if (permissionIds.length > 0) {
            String sqlInsert = "insert into user_role (user_id, role_id) values (?, ?)";
            Object[] objs = null;
            List<Object[]> batchParams = new ArrayList<Object[]>();
            for (String permissionId : permissionIds) {
                objs = new Object[2];
                objs[0] = userId;
                objs[1] = permissionId;
                batchParams.add(objs);
            }
            this.baseService.jdBatchUpdate(sqlInsert, batchParams);
        }
    }

    //批量插入用户对应的资源，只选择用JdbcTemplate的批量更新方法，以保证高性能
    private void batchInsertUserResource(int userId, String resourceIdsStr) {
        String sqlDelete = "delete from user_menu where user_id = ?";
        this.baseService.jdDelete(sqlDelete, userId);

        //没有选择资源，直接返回
        if (resourceIdsStr == null || resourceIdsStr.trim().equals("")) {
            return;
        }

        //插入资源
        String[] resourceIds = resourceIdsStr.split(",");
        if (resourceIds.length > 0) {
            String sqlInsert = "insert into user_menu (user_id, menu_id) values (?, ?)";
            Object[] objs = null;
            List<Object[]> batchParams = new ArrayList<Object[]>();
            for (String resourceId : resourceIds) {
                objs = new Object[2];
                objs[0] = userId;
                objs[1] = resourceId;
                batchParams.add(objs);
            }
            this.baseService.jdBatchUpdate(sqlInsert, batchParams);
        }
    }

    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");

        //删除自己
        this.baseService.myDelete(User.DeleteByPk, id);

        //删除对应的角色
        this.baseService.jdDelete("delete from user_role where user_id = ?", id);

        //删除对应的资源
        this.baseService.jdDelete("delete from user_menu where user_id = ?", id);

        response.getWriter().write("y");
    }

    //getter and setter
    public BaseService getBaseDao() {
        return baseService;
    }

    public void setBaseDao(BaseService baseDao) {
        this.baseService = baseDao;
    }

}
