package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
import com.jelly.eoss.model.UserRolesPerms;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.servlet.ICodeServlet;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.security.Digest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class LoginAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(LoginAction.class);

	@Resource
	private BaseService baseService;

	@Resource
	MenuService menuService;

    @RequestMapping(value = "/toLogin")
    public void toLoginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
	
	@RequestMapping(value = "/login")
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
			Map<String, String> pm = new HashMap<>();
			pm.put("username", username);
			pm.put("password", Digest.GetMD5(password));
			User user = this.baseService.mySelectOne("_EXT.SelectUserByNameAndPwd", pm);
			if(user == null){
				this.responseSimpleJson(response, false, "用户名与密码不匹配");
				return;
			}

			String menuTreeIdsOfUser = this.menuService.queryMenuTreeIdsOfUser(user);

			//登录成功
            UserRolesPerms userRolesPerms = new UserRolesPerms();
            List<String> roleList = this.baseService.mySelectList("_EXT.Role_QueryByUserId", user.getId());
            List<String> permList = this.baseService.mySelectList("_EXT.Permission_QueryByUserId", user.getId());

            Set<String> roleSet = new HashSet<>();
            Set<String> permSet = new HashSet<>();

            if(roleList != null && roleList.size() > 0){
                roleSet.addAll(roleList);
            }

            if(permList != null && permList.size() > 0){
                permSet.addAll(permList);
            }

            userRolesPerms.setUser(user).setRolesOfUser(roleSet).setPermsOfUser(permSet);

			request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, userRolesPerms);
			request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, menuTreeIdsOfUser);
			
			this.responseSimpleJson(response, true, "");
		}catch(Exception e){
			e.printStackTrace();
			this.responseSimpleJson(response, false, "未知错误");
		}
	}
	
	@RequestMapping(value = "/logout")
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
