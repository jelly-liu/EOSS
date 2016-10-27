package com.jelly.eoss.web;

import com.jelly.eoss.dao.BaseService;
import com.jelly.eoss.model.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(LoginAction.class);

	@Resource
	private BaseService baseService;

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
            String rememberMe = ServletRequestUtils.getStringParameter(request, "rememberMe");
			
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



			//****************************** try shiro login ******************************//
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            String msg = null;
            try {
                if(StringUtils.isNotEmpty(rememberMe)){
                    token.setRememberMe(true);
                }
                subject.login(token);
            } catch (IncorrectCredentialsException e) {
                log.error("IncorrectCredentialsException", e);
                msg = "username or password error!";
            } catch (ExcessiveAttemptsException e) {
                log.error("ExcessiveAttemptsException", e);
                msg = "try login to many times";
            } catch (LockedAccountException e) {
                log.error("LockedAccountException", e);
                msg = "LockedAccount";
            } catch (DisabledAccountException e) {
                log.error("DisabledAccountException", e);
                msg = "DisabledAccount";
            } catch (ExpiredCredentialsException e) {
                log.error("ExpiredCredentialsException", e);
                msg = "ExpiredCredentials";
            } catch (UnknownAccountException e) {
                log.error("UnknownAccountException", e);
                msg = "UnknownAccount";
            } catch (UnauthorizedException e) {
                log.error("UnauthorizedException", e);
                msg = "Unauthorized";
            } catch (Throwable e){
                msg = "Other Exception!";
            }
            token.clear();

            if(StringUtils.isNotEmpty(msg)){
                this.responseSimpleJson(response, false, msg);
                return;
            }

			/*
			 *数据样例：
			 * 1#2#4#7
			 * 1#2#4#9
			 * 1#2#5#16
			 * 将重复的id过滤掉
			 */
            User user = (User)subject.getPrincipal();
			List<Map<String, Object>> list = this.baseService.mySelectList("_EXT.Login_QueryTreePathByUserId", user.getId());
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
			

			request.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
			request.getSession().setAttribute(Const.LOGIN_MENU_TREE_IDS_KEY, sb.toString());
			
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
