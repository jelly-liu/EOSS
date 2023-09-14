package com.open.eoss.web.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.db.entity.*;
import com.open.eoss.db.mapper.basic.*;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.exception.BusinessException;
import com.open.eoss.service.sys.SysSessionManager;
import com.open.eoss.service.sys.SysService;
import com.open.eoss.servlet.ICodeClickServlet;
import com.open.eoss.util.security.Digest;
import com.open.eoss.web.BaseAction;
import com.open.eoss.web.vo.EossRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping(value = "/sys/")
public class SysAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(SysAction.class);

	@Autowired
	SysUserMapper userMapper;
	@Autowired
	SysUserRoleMapper userRoleMapper;
	@Autowired
	SysUserPermMapper userPermMapper;
	@Autowired
	SysPermMapper permMapper;
	@Autowired
	SysRolePermMapper rolePermMapper;
	@Autowired
	SysSessionManager sysSessionManager;
	@Autowired
	SysService sysService;

	@ResponseBody
	@RequestMapping(value = "/test")
	public EossRes<List<SysPerm>> test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	@RequestMapping(value = "/to-main")
	public String toMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/templates/sys/main";
	}

	@RequestMapping(value = "/to-west")
	public String toWest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/templates/sys/main-west";
	}

	@ResponseBody
	@RequestMapping(value = "/west")
	public EossRes<List<SysPerm>> west(HttpServletRequest request, HttpServletResponse response) throws Exception {
		EossRes<List<SysPerm>> rs = sysService.mainWestList(request);
		Thread.sleep(500);
		return rs;
	}

	@RequestMapping(value = "/to401")
	public String to401(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/templates/401";
	}

    @RequestMapping(value = "/to-login")
    public String toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/templates/login";
    }

	@ResponseBody
	@RequestMapping(value = "/logout")
	public EossRes<Object> txLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		sysSessionManager.removeSessionInfo(request);
		return EossRes.success();
	}

	//依次点击图片中的文字，验证码
	@ResponseBody
	@RequestMapping(value = "/login")
	public EossRes txLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = ServletRequestUtils.getStringParameter(request, "username");
		String password = ServletRequestUtils.getStringParameter(request, "password");
		String chrPositions = ServletRequestUtils.getStringParameter(request, "chrPositions");

		Object vCodeObj = request.getSession().getAttribute(ICodeClickServlet.ICODE_CLICK_SESSION_KEY);
		if(vCodeObj == null){
			throw new BusinessException("验证码已失效");
		}

		//检查验证码
		if(chrPositions == null || chrPositions.equals("")){
			throw new BusinessException("请输入验证码");
		}
		String[] positionStringAry = StringUtils.split(chrPositions, ";");
		if(positionStringAry == null || positionStringAry.length != 3){
			throw new BusinessException("请输入正确的验证码长度");
		}
		if(!this.verifyCharPositions(request, positionStringAry)){
			throw new BusinessException("请输入正确的验证码");
		}

		//登录密码检验
		SysUser user = userMapper.selectOne(new SysUser().setUsername(username));
		if(user == null){
			throw new BusinessException("用户不存在");
		}
		if(user.getDisabled() == 1){
			throw new BusinessException("用户已禁用");
		}

		String passwordMd5WithSalt = Digest.MD5(password, user.getSalt());
		if(!StringUtils.equals(passwordMd5WithSalt, user.getPassword())){
			throw new BusinessException("用户名和密码不匹配");
		}

		// 用户拥有的角色 -> 角色拥有的权限
		// 用户直接拥有的权限
		Set<String> permUrlSet = new HashSet<>();
		Set<Integer> permIdSet = new HashSet<>();
		List<SysPerm> permOfUser = sysService.permOfUser(user.getId());
		if(!CollectionUtils.isEmpty(permOfUser)){
			permOfUser.forEach(i -> {
				if(StringUtils.isNotBlank(i.getUrl()))permUrlSet.add(i.getUrl());
				if(StringUtils.isNotBlank(i.getUrlSubmit()))permUrlSet.add(i.getUrlSubmit());
				permIdSet.add(i.getId());
			});
		}

		//登陆成功后，缓存用户必要信息
		SysSessionInfo sessionInfo = new SysSessionInfo();
		sessionInfo.setUser(user);
		sessionInfo.setPerms(permUrlSet);
		sessionInfo.setPermIds(permIdSet);
		logger.info("login, session, {}", JSON.toJSONString(sessionInfo));
		sysSessionManager.setSessionInfo(request, sessionInfo);

		return EossRes.success();
	}

	private boolean verifyCharPositions(HttpServletRequest request, String[] positionStringList){
		String pointListInCacheString = (String)request.getSession().getAttribute(ICodeClickServlet.ICODE_CLICK_SESSION_KEY);
		List<Point> pointListInCache = JSON.parseArray(pointListInCacheString, Point.class);
		if(CollectionUtils.isEmpty(pointListInCache)){
			return false;
		}

		boolean[] vb = new boolean[3];
		for(int i = 0; i < positionStringList.length; i++){
			String[] xyStr = StringUtils.split(positionStringList[i], ",");
			int x = Integer.parseInt(xyStr[0]);
			int y = Integer.parseInt(xyStr[1]);

			Point p1 = new Point(x, y);
			Point p2 = pointListInCache.get(i);

			double dis = Math.sqrt(Math.abs((p1.getX() - p2.getX())* (p1.getX() - p2.getX())+(p1.getY() - p2.getY())* (p1.getY() - p2.getY())));
			if(dis < 25/2){
				vb[i] = true;
			}
			logger.info("click char in img, diff, dis=" + dis);
		}

		for(boolean b : vb){
			if(b == false){
				return false;
			}
		}

		return true;
	}
}
