package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
import com.jelly.eoss.service.MenuService;
import com.jelly.eoss.servlet.ICodeServlet;
import com.jelly.eoss.util.Const;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			String vCode = ServletRequestUtils.getStringParameter(request, "vCode");
            String rememberMe = ServletRequestUtils.getStringParameter(request, "rememberMe");
			
			Object vCodeObj = request.getSession().getAttribute(ICodeServlet.ICODE_SESSION_KEY);
			if(vCodeObj == null){
				this.responseSimpleJson(response, false, "验证码已失效");
				return;
			}
			
			//检查验证码
			if(vCode == null || vCode.equals("")){
				this.responseSimpleJson(response, false, "请输入验证码");
				return;
			}
			if(!vCode.equals(vCodeObj.toString())){
				this.responseSimpleJson(response, false, "验证码输入错误");
				return;
			}


			//****************************** try shiro login ******************************//
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            String msg = null;
            try {
                if(StringUtils.isNotEmpty(rememberMe)){
                    token.setRememberMe(true);
                }
                subject.login(token);
            } catch (Throwable e){
                msg = e.getMessage();
            }
            token.clear();

            if(StringUtils.isNotEmpty(msg)){
                this.responseSimpleJson(response, false, msg);
                return;
            }

			User user = (User)subject.getPrincipal();
			String menuTreeIdsOfUser = this.menuService.queryMenuTreeIdsOfUser(user);

			request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, menuTreeIdsOfUser);

			subject.getSession().setAttribute("USER_INFO", user);
			
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
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
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
