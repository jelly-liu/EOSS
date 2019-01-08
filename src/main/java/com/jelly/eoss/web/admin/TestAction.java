package com.jelly.eoss.web.admin;

import com.jelly.eoss.dao.BaseDao;
import com.jelly.eoss.web.BaseAction;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/test")
public class TestAction extends BaseAction {
	private static final Logger log = LoggerFactory.getLogger(TestAction.class);

	@Autowired
	private BaseDao baseDao;

	@RequestMapping(value = "updateSession")
	public void queryRowBounds(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		if(subject != null){
            subject.getSession().setAttribute("COUNTER", 1);
            int i = 0;
		}
	}
}
