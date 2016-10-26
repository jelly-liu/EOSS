package com.jelly.eoss.filter;

import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.Log;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SecurityFilter2 implements Filter {
	// 不登陆也可以访问的资源
	private static Set<String> GreenUrlSet = new HashSet<String>();

	public void init(FilterConfig arg0) throws ServletException {
		GreenUrlSet.add(Const.BASE_PATH + "/");
		GreenUrlSet.add(Const.BASE_PATH + "/index.jsp");
		GreenUrlSet.add(Const.BASE_PATH + "/icode.jpg");
		GreenUrlSet.add(Const.BASE_PATH + "/info.jsp");
		GreenUrlSet.add(Const.BASE_PATH + "/toLogin.ac");
        GreenUrlSet.add(Const.BASE_PATH + "/login.ac");
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;

		if (Const.ENABLE_SECURITY_FILTER) {
			if (request.getSession().getAttribute(Const.LOGIN_SESSION_KEY) == null) {
				// String url = request.getRequestURL().toString();
				String uri = request.getRequestURI();
				
				//不处理js, css, ico
				if (uri.endsWith(".js") 
						|| uri.endsWith(".css") 
						|| uri.endsWith(".jpg") 
						|| uri.endsWith(".gif")
						|| uri.endsWith(".ico")) {
					Log.Debug("security filter, pass, " + request.getRequestURI());
					filterChain.doFilter(srequest, sresponse);
					return;
				}

				//不处理指定的action, jsp
				if (GreenUrlSet.contains(uri)) {
					Log.Debug("security filter, pass, " + request.getRequestURI());
					filterChain.doFilter(srequest, sresponse);
					return;
				}

				//跳转到登陆页面
				Log.Debug("security filter, deney, " + request.getRequestURI());
				String html = "<script type=\"text/javascript\">top.window.location.href=\"_BP_/toLogin.ac\"</script>";
				html = html.replace("_BP_", Const.BASE_PATH);
				sresponse.getWriter().write(html);
			} else {
				filterChain.doFilter(srequest, sresponse);
			}
		} else {
			filterChain.doFilter(srequest, sresponse);
		}
	}

	public void destroy() {

	}
}
