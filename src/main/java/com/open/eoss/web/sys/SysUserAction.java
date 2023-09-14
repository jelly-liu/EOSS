package com.open.eoss.web.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.db.entity.SysUser;
import com.open.eoss.db.entity.SysUserPerm;
import com.open.eoss.db.entity.SysUserRole;
import com.open.eoss.db.mapper.basic.SysUserMapper;
import com.open.eoss.db.mapper.basic.SysUserPermMapper;
import com.open.eoss.db.mapper.basic.SysUserRoleMapper;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.service.sys.SysSessionManager;
import com.open.eoss.service.sys.SysUserService;
import com.open.eoss.util.Const;
import com.open.eoss.web.BaseAction;
import com.open.eoss.web.vo.EossRes;
import com.open.eoss.web.vo.UserRoleParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "/sys/user/")
public class SysUserAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(SysUserAction.class);

	@Autowired
	SysUserMapper userMapper;
	@Autowired
	SysUserService userService;
	@Autowired
	SysUserRoleMapper userRoleMapper;
	@Autowired
	SysUserPermMapper userPermMapper;
	@Autowired
	SysSessionManager sysSessionManager;

	@RequestMapping(value = "/to-list")
	public ModelAndView listPage(HttpServletRequest request, @ModelAttribute SysUser v) throws Exception {
		v.initPageAndSize(1, Const.PAGE_SIZE);
		if(v.getPage() < 1){
			v.setPage(1);
		}
		Map<String, Boolean> permMap = new HashMap<>();
		SysSessionInfo sessionInfo = sysSessionManager.getSessionInfo(request);
		Set<String> permSet = sessionInfo.getPerms();
		for (String permUrl : permSet) {
			if(StringUtils.equals(permUrl, "/sys/user/add")){
				permMap.put("add", true);
			}
			if(StringUtils.equals(permUrl, "/sys/user/update")){
				permMap.put("update", true);
			}
			if(StringUtils.equals(permUrl, "/sys/user/delete")){
				permMap.put("delete", true);
			}
		}
		ModelAndView modelAndView = new ModelAndView("/templates/sys/user-list");
		modelAndView.addObject("permMap", JSON.toJSONString(permMap));
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public EossRes<List<SysUser>> list(HttpServletRequest request, @ModelAttribute SysUser v) throws Exception {
		if(v.getPage() < 1){
			v.setPage(1);
		}
		Thread.sleep(500);
		return userService.list(v);
	}

	@RequestMapping(value = "/to-update")
	public ModelAndView updatePage(HttpServletRequest request, @ModelAttribute UserRoleParam v) throws Exception {
		SysUser perm = userMapper.selectByPk(v.getId());
		Thread.sleep(1000);

		// 用户拥有的角色
		List<SysUserRole> userRoleList = this.userRoleMapper.select(new SysUserRole().setUserId(v.getId()));
		List<Integer> oldRoleIdList = new LinkedList<>();
		if(!CollectionUtils.isEmpty(userRoleList)){
			for(SysUserRole userRole : userRoleList){
				oldRoleIdList.add(userRole.getRoleId());
			}
		}

		// 用户拥有的权限
		List<SysUserPerm> userPermList = this.userPermMapper.select(new SysUserPerm().setUserId(v.getId()));
		List<Integer> oldPermIdList = new LinkedList<>();
		if(!CollectionUtils.isEmpty(userPermList)){
			for(SysUserPerm userPerm : userPermList){
				oldPermIdList.add(userPerm.getPermId());
			}
		}

		ModelAndView modelAndView = new ModelAndView("/templates/sys/user-update");
		modelAndView.addObject("data", perm);
		modelAndView.addObject("oldRoleIds", StringUtils.join(oldRoleIdList, ","));
		modelAndView.addObject("oldPermIds", StringUtils.join(oldPermIdList, ","));
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public EossRes<Boolean> update(HttpServletRequest request, @ModelAttribute UserRoleParam v) throws Exception {
		userService.txUpdate(v);
		return EossRes.success();
	}

	@RequestMapping(value = "/to-add")
	public ModelAndView addPage(HttpServletRequest request, @ModelAttribute SysUser v) throws Exception {
		Thread.sleep(500);
		return new ModelAndView("/templates/sys/user-add");
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public EossRes<Boolean> add(HttpServletRequest request, @ModelAttribute UserRoleParam v) throws Exception {
		this.userService.txAdd(v);
		return EossRes.success();
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public EossRes<Boolean> delete(HttpServletRequest request, @RequestParam(name = "ids") List<Integer> ids) throws Exception {
		userService.txDelete(ids);
		return EossRes.success();
	}
}
