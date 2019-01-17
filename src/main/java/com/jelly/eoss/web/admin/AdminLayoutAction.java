package com.jelly.eoss.web.admin;

import com.jelly.eoss.web.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/system/layout")
public class AdminLayoutAction extends BaseAction {
    @RequestMapping(value = "/main")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/layout/main.htm";
    }

    @RequestMapping(value = "/northAjax")
    public String northAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/layout/northAjax.htm";
    }
	
	@RequestMapping(value = "/center")
	public String center(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/layout/center.htm";
	}

    @RequestMapping(value = "/southAjax")
    public String southAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/layout/southAjax.htm";
    }

    @RequestMapping(value = "/westAjax")
    public String westAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/layout/westAjax.htm";
    }
}
