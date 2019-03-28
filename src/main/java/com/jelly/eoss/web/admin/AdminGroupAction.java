package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminGroup;
import com.jelly.eoss.db.entity.AdminGroupMenu;
import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.entity.AdminUserGroup;
import com.jelly.eoss.db.mapper.basic.iface.AdminGroupMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminGroupMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserGroupMapper;
import com.jelly.eoss.util.ComUtil;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.Pager;
import com.jelly.eoss.web.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/system/group")
public class AdminGroupAction extends BaseAction {
    @Autowired
    AdminGroupMapper groupMapper;
    @Autowired
    AdminGroupMenuMapper groupMenuMapper;
    @Autowired
    AdminUserGroupMapper userGroupMapper;

    @RequestMapping(value = "/toList")
    public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);

        Map<String, Object> param = this.getRequestMap(request);
        Integer totalRow = null;

        RowBounds rb = new RowBounds((page - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
        param.put("rb", rb);
        List<Map<String, Object>> dataList = null;

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
    public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminGroup group) throws Exception {
        String menuIdsStr = request.getParameter("menuIds");
        ModelAndView mv = new ModelAndView();

        //插入组
        int id = ComUtil.QueryNextID("id", AdminGroup.TABLE_NAME);
        group.setId(id);
        groupMapper.insert(group);

        //插入组拥有的菜单
        if(StringUtils.isNotEmpty(menuIdsStr)){
            String[] menuIds = StringUtils.split(menuIdsStr, ",");
            if(menuIds != null && menuIds.length > 0){
                for(String menuId : menuIds){
                    groupMenuMapper.insert(new AdminGroupMenu().setGroupId(id).setMenuId(Integer.valueOf(menuId)));
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/system/user/toList?id=" + group.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");
        return new ModelAndView("/system/userUpdate.htm");
    }

    @RequestMapping(value = "/update")
    public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminGroup group) throws Exception {
        if(group.getId() == null){
            return null;
        }

        //更新组名
        if(StringUtils.isNotEmpty(group.getName())){
            groupMapper.update(group);
        }

        //删除该组原来拥有的菜单
        groupMenuMapper.delete(new AdminGroupMenu().setGroupId(group.getId()));

        //插入组拥有的菜单
        String menuIdsStr = request.getParameter("menuIds");
        if(StringUtils.isNotEmpty(menuIdsStr)){
            String[] menuIds = StringUtils.split(menuIdsStr, ",");
            if(menuIds != null && menuIds.length > 0){
                for(String menuId : menuIds){
                    groupMenuMapper.insert(new AdminGroupMenu().setGroupId(group.getId()).setMenuId(Integer.valueOf(menuId)));
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/delete")
    public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        //删除自己
        groupMapper.deleteByPk(id);
        //从组和用户关系表中，删除此关系
        userGroupMapper.delete(new AdminUserGroup().setGroupId(id));
        //从组和菜单关系表中，删除此关系
        groupMenuMapper.delete(new AdminGroupMenu().setGroupId(id));

        response.getWriter().write("y");
    }
}
