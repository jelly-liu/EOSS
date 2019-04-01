package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.*;
import com.jelly.eoss.db.mapper.basic.iface.AdminGroupMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminGroupMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserGroupMapper;
import com.jelly.eoss.service.EossMenuService;
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
import java.util.HashMap;
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
    @Autowired
    EossMenuService eossMenuService;

    @RequestMapping(value = "/toList")
    public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
        RowBounds rb = new RowBounds((page - 1) * Const.PAGE_SIZE, Const.PAGE_SIZE);

        String name = request.getParameter("name");
        AdminGroup groupCondition = new AdminGroup().setName(name);

        //查询符合条件的总记录数
        Integer totalRow = groupMapper.selectCount(groupCondition);

        //分页查询符合条件的本页记录
        groupCondition.setOffset(rb.getOffset());
        groupCondition.setLength(rb.getLimit());
        List<AdminGroup> dataList = groupMapper.select(groupCondition);

        Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
        pager.setData(dataList);

        request.setAttribute("pager", pager);
        this.resetAllRequestParams(request);
        return new ModelAndView("/system/groupList.htm");
    }

    @RequestMapping(value = "/toAdd")
    public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response, AdminUser user) throws Exception {
        return new ModelAndView("/system/groupAdd.htm");
    }

    @RequestMapping(value = "/add")
    public ModelAndView txAdd(HttpServletRequest request, HttpServletResponse response, AdminGroup group) throws Exception {
        String menuIdsStr = request.getParameter("resourcesIds");
        ModelAndView mv = new ModelAndView();

        int count = groupMapper.selectCount(new AdminGroup().setName(group.getName()));
        if(count > 0){
            String errorMsg = "组已存在，组名为：" + group.getName();
            request.setAttribute("ERROR-MSG", errorMsg);
            throw new RuntimeException(errorMsg);
        }

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

        ModelAndView modelAndView = new ModelAndView("redirect:/system/group/toList?id=" + group.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/toUpdate")
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        AdminGroup group = groupMapper.selectByPk(id);

        //将该角色已有菜单资源用逗号连接成一个字符串，如1,2,3,4,5,6
        List<AdminGroupMenu> resourceIdsOldList = groupMenuMapper.select(new AdminGroupMenu().setGroupId(id));
        StringBuilder sb = new StringBuilder();
        for (AdminGroupMenu m : resourceIdsOldList) {
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

        request.setAttribute("zTreeNodeJsonResource", zTreeNodeJsonResource);
        request.setAttribute("group", group);
        return new ModelAndView("/system/groupUpdate.htm");
    }

    @RequestMapping(value = "/update")
    public ModelAndView txUpdate(HttpServletRequest request, HttpServletResponse response, AdminGroup group) throws Exception {
        if(group.getId() == null){
            throw new RuntimeException("组id不能为空");
        }
        if(group.getName() == null){
            throw new RuntimeException("组名不能为空");
        }

        AdminGroup group1 = groupMapper.selectByPk(group.getId());
        if(!group1.getName().equalsIgnoreCase(group.getName())){
            int count = groupMapper.selectCount(new AdminGroup().setName(group.getName()));
            if(count > 0){
                String errorMsg = "组已存在，组名为：" + group.getName();
                request.setAttribute("ERROR-MSG", errorMsg);
                throw new RuntimeException(errorMsg);
            }
            //更新组名
            if(StringUtils.isNotEmpty(group.getName())){
                groupMapper.update(group);
            }
        }

        //删除该组原来拥有的菜单
        groupMenuMapper.delete(new AdminGroupMenu().setGroupId(group.getId()));

        //插入组拥有的菜单
        String menuIdsStr = request.getParameter("resourceIds");
        if(StringUtils.isNotEmpty(menuIdsStr)){
            String[] menuIds = StringUtils.split(menuIdsStr, ",");
            if(menuIds != null && menuIds.length > 0){
                for(String menuId : menuIds){
                    groupMenuMapper.insert(new AdminGroupMenu().setGroupId(group.getId()).setMenuId(Integer.valueOf(menuId)));
                }
            }
        }

        return new ModelAndView("redirect:/system/group/toList");
    }

    @RequestMapping(value = "/delete")
    public void txDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = ServletRequestUtils.getIntParameter(request, "id");

        if(id == null){
          throw new RuntimeException("id不能为空");
        }

        //删除自己
        groupMapper.deleteByPk(id);
        //从组和用户关系表中，删除此关系
        userGroupMapper.delete(new AdminUserGroup().setGroupId(id));
        //从组和菜单关系表中，删除此关系
        groupMenuMapper.delete(new AdminGroupMenu().setGroupId(id));

        response.getWriter().write("y");
    }
}
