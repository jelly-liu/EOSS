package com.jelly.eoss.web;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.util.ComUtil;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.DateUtil;
import com.jelly.eoss.util.Log;
import com.jelly.eoss.util.Pager;
import com.jelly.eoss.util.security.Digest;
import com.jelly.eoss.model.Users;

@Controller
@RequestMapping(value = "/user")
public class UserAction extends BaseAction{
	
	@Resource
	private BaseService baseService;
	
	@RequestMapping(value = "/queryUserNameAjax")
	public void queryUserNameAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			String name = request.getParameter("name");
			int total = this.baseService.jdQueryForInt("select count(*) total from users where name = ?", name);
			if(total == 0){
				response.getWriter().write("y");
			}else{
				response.getWriter().write("该用户名已被占用");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value = "/queryUserPage")
	public ModelAndView queryUserPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Integer page = ServletRequestUtils.getIntParameter(request, "page", 1);
		
		Map<String, String> param = this.getRequestMap(request);
		RowBounds rb = new RowBounds((page -1) * Const.PAGE_SIZE, Const.PAGE_SIZE);
		
		Integer totalRow = this.baseService.mySelectOne("_User_QueryUser_Count", param);
		List<Map<String, Object>> dataList = this.baseService.getSqlSessionTemplate().selectList("_User_QueryUser_Page", param, rb);
		
		Pager pager = new Pager(page.intValue(), Const.PAGE_SIZE, totalRow.intValue());
		pager.setData(dataList);
		
		request.setAttribute("pager", pager);
		this.resetAllRequestParams(request);
		return new ModelAndView("/system/userList.jsp");
	}

	@RequestMapping(value = "/addUser")
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, Users user) throws Exception{
		String roleIds = request.getParameter("roleIds");
		ModelAndView mv = new ModelAndView();
		
		//查询用户名是否存在
		int total = this.baseService.jdQueryForInt("select count(*) total from users where username = ?", user.getUsername());
		if(total != 0){
			request.setAttribute("INFO", "该用户名已存在，请选择一个新的用户名");
			mv.setViewName("/info.jsp");
			return mv;
		}
		
		int id = ComUtil.QueryNextID("id", "users");
		
		//插入用户
		user.setId(id);
		user.setPassword(Digest.GetMD5(user.getPassword()));
		user.setSalt(new Random().nextInt(1000) + "");
		user.setCreateDatetime(DateUtil.GetCurrentDateTime(true));
		this.baseService.myInsert(Users.Insert, user);
		
		//插入角色
		this.batchInsertUserRole(user.getId(), roleIds);
		
		return new ModelAndView("/system/userList.jsp");
	}
	
	@RequestMapping(value = "/updateUserPrepare")
	public ModelAndView updateUserPrepare(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		
		//查询自己
		Users u = this.baseService.mySelectOne(Users.SelectByPk, id);
		
		//查询该用户已拥有的角色
		String sql = "select * from users_role where users_id = ?";
		List<Map<String, Object>> roleOldList = this.baseService.jdQueryForList(sql, id);
		Set<String> roleOldSet = new HashSet<String>();
		for(Map<String, Object> m : roleOldList){
			roleOldSet.add(m.get("role_id").toString());
		}
		
		//设置初始化选中的角色
		List<Map<String, Object>> roleList = this.baseService.mySelectList("_Role_QueryRolePage");
		for(Map<String, Object> m : roleList){
			m.put("pId", "-1");
			m.put("isParent", "false");
			m.put("iconSkin", "icon_eoss_role01");
			if(roleOldSet.contains(m.get("id").toString())){
				m.put("checked", "true");
			}
		}
		String jsonStr = JSONArray.fromObject(roleList).toString();
		Log.Debug(jsonStr);
		
		request.setAttribute("zTreeNodeJson", jsonStr);
		request.setAttribute("user", u);
		return new ModelAndView("/system/userUpdate.jsp");
	}
	
	@RequestMapping(value = "/updateUser")
	public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response, Users user) throws Exception{
		//更新用户信息
		Users u = this.baseService.mySelectOne(Users.SelectByPk, user.getId());
		u.setUsername(user.getUsername());
		u.setPassword(Digest.GetMD5(user.getPassword()));
		this.baseService.myUpdate(Users.Update, u);
		
		//更新角色
		String roleIds = request.getParameter("roleIds");
		this.batchInsertUserRole(user.getId(), roleIds);
		
		return new ModelAndView("/system/userList.jsp");
	}
	
	//批量插入用户对应的角色，只选择用JdbcTemplate的批量更新方法，以保证高性能
	@RequestMapping(value = "/batchInsertUserRole")
	private void batchInsertUserRole(Integer userId, String roleIdsStr){
		String sqlDelete = "delete from users_role where users_id = ?";
		this.baseService.jdDelete(sqlDelete, userId);
		
		//没有选择角色，直接返回
		if(roleIdsStr == null || roleIdsStr.trim().equals("")){
			return;
		}
		
		//插入角色
		String[] permissionIds = roleIdsStr.split(",");
		if(permissionIds.length > 0){
			String sqlInsert = "insert into users_role (users_id, role_id) values (?, ?)";
			Object[] objs = null;
			List<Object[]> batchParams = new ArrayList<Object[]>();
			for(String permissionId : permissionIds){
				objs = new Object[2];
				objs[0] = userId;
				objs[1] = permissionId;
				batchParams.add(objs);
			}
			this.baseService.jdBatchUpdate(sqlInsert, batchParams);
		}
	}
	
	@RequestMapping(value = "/deleteUser")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		
		//删除自己
		this.baseService.myDelete(Users.DeleteByPk, id);
		
		//删除对应的角色
		this.baseService.jdDelete("delete from users_role where users_id = ?", id);

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
