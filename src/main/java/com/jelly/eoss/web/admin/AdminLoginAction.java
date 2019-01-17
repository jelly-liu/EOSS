package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.service.EossMenuService;
import com.jelly.eoss.servlet.ICodeServlet;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.web.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminLoginAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(AdminLoginAction.class);

	@Autowired
    EossMenuService eossMenuService;

    @RequestMapping(value = "/toLogin")
    public ModelAndView toLoginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/login.htm");
    }
	
	@RequestMapping(value = "/login")
	public void txLoginIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

			AdminUser user = (AdminUser)subject.getPrincipal();
			String menuTreeIdsOfUser = this.eossMenuService.txQueryMenuTreeIdsOfUser(user);

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
	public void txLoginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		request.getSession().removeAttribute(Const.LOGIN_MENU_TREE_IDS_KEY);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
		response.getWriter().write("y");
	}
}
