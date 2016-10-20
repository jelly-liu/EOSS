package com.jelly.eoss.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.servlet.ICodeServlet;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.security.Digest;
import com.jelly.eoss.model.Users;

@Controller
@RequestMapping(value = "/login")
public class LoginAction extends BaseAction {
	@Resource
	private BaseService baseService;
	
	@RequestMapping(value = "/loginIn")
	public void loginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try{
			String username = ServletRequestUtils.getStringParameter(request, "username");
			String password = ServletRequestUtils.getStringParameter(request, "password");
			String icode = ServletRequestUtils.getStringParameter(request, "icode");
			
			Object icodeObj = request.getSession().getAttribute(ICodeServlet.ICODE_SESSION_KEY);
			if(icodeObj == null){
				this.responseSimpleJson(response, false, "验证码已失效");
				return;
			}
			
			//检查验证码
			if(icode == null || icode.equals("")){
				this.responseSimpleJson(response, false, "请输入验证码");
				return;
			}
			if(!icode.equals(icodeObj.toString())){
				this.responseSimpleJson(response, false, "验证码输入错误");
				return;
			}
			
			//检查用户名与密码
			Map<String, String> pm = new HashMap<String, String>();
			pm.put("username", username);
			pm.put("password", Digest.GetMD5(password));
			Users user = this.baseService.mySelectOne("_User_SelectUserByNameAndPwd", pm);
			if(user == null){
				this.responseSimpleJson(response, false, "用户名与密码不匹配");
				return;
			}
			
			/*
			 *数据样例：
			 * 1#2#4#7
			 * 1#2#4#9
			 * 1#2#5#16
			 * 将重复的id过滤掉
			 */
			List<Map<String, Object>> list = this.baseService.mySelectList("_Login_QueryTreePathByUserId", user.getId());
			Set<String> treeIdSet = new HashSet<String>();
			String[] ids = null;
			for(Map<String, Object> m : list){
				ids = m.get("ids").toString().split("#");
				for(String id : ids){
					treeIdSet.add(id);
				}
			}
			
			//将treeSet中的值拼接成select xx from xx where id in (_inIds_)
			StringBuilder sb = new StringBuilder();
			for(String id : treeIdSet){
				sb.append(id + ",");
			}
			if(sb.length() > 0){
				sb.deleteCharAt(sb.length() - 1);
			}
			
			//登录成功
			request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, sb.toString());
			
			this.responseSimpleJson(response, true, "");
//			request.getRequestDispatcher("/base/main.jsp").forward(request, response);
		}catch(Exception e){
			e.printStackTrace();
			this.responseSimpleJson(response, false, "未知错误");
		}
	}
	
	@RequestMapping(value = "/layout")
	public ModelAndView layout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/system/layout/main.jsp");
		return mv;
	}
	
	@RequestMapping(value = "/loginOut")
	public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		request.getSession().removeAttribute(Const.LOGIN_MENU_TREE_IDS_KEY);
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
