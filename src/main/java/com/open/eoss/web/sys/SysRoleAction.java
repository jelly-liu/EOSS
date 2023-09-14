package com.open.eoss.web.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.db.entity.SysRole;
import com.open.eoss.db.entity.SysRolePerm;
import com.open.eoss.db.mapper.basic.SysRoleMapper;
import com.open.eoss.db.mapper.basic.SysRolePermMapper;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.service.sys.SysRoleService;
import com.open.eoss.service.sys.SysSessionManager;
import com.open.eoss.util.Const;
import com.open.eoss.web.BaseAction;
import com.open.eoss.web.vo.EossRes;
import com.open.eoss.web.vo.RolePermParam;
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
@RequestMapping(value = "/sys/role/")
public class SysRoleAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(SysRoleAction.class);

	@Autowired
	SysRolePermMapper rolePermMapper;
	@Autowired
	SysRoleMapper roleMapper;
	@Autowired
	SysRoleService roleService;
	@Autowired
	SysSessionManager sysSessionManager;

	@RequestMapping(value = "/to-list")
	public ModelAndView listPage(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		v.initPageAndSize(1, Const.PAGE_SIZE);
		if(v.getPage() < 1){
			v.setPage(1);
		}
		Map<String, Boolean> permMap = new HashMap<>();
		SysSessionInfo sessionInfo = sysSessionManager.getSessionInfo(request);
		Set<String> permSet = sessionInfo.getPerms();
		for (String permUrl : permSet) {
			if(StringUtils.equals(permUrl, "/sys/role/add")){
				permMap.put("add", true);
			}
			if(StringUtils.equals(permUrl, "/sys/role/update")){
				permMap.put("update", true);
			}
			if(StringUtils.equals(permUrl, "/sys/role/delete")){
				permMap.put("delete", true);
			}
		}
		ModelAndView modelAndView = new ModelAndView("/templates/sys/role-list");
		modelAndView.addObject("permMap", JSON.toJSONString(permMap));
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public EossRes<List<SysRole>> list(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		if(v.getPage() < 1){
			v.setPage(1);
		}
		Thread.sleep(500);
		return roleService.list(v);
	}

	@RequestMapping(value = "/to-update")
	public ModelAndView updatePage(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		SysRole role = roleMapper.selectByPk(v.getId());
		List<SysRolePerm> rolePermList = rolePermMapper.select(new SysRolePerm().setRoleId(role.getId()));
		List<Integer> oldPermIds = new LinkedList<>();
		if(!CollectionUtils.isEmpty(rolePermList)){
			for(SysRolePerm rolePerm : rolePermList){
				oldPermIds.add(rolePerm.getPermId());
			}
		}
		Thread.sleep(1000);
		ModelAndView modelAndView = new ModelAndView("/templates/sys/role-update");
		modelAndView.addObject("data", role);
		modelAndView.addObject("oldPermIds", StringUtils.join(oldPermIds, ","));
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public EossRes<Boolean> update(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		roleService.txUpdate(v);
		return EossRes.success();
	}

	@RequestMapping(value = "/to-add")
	public ModelAndView addPage(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		Thread.sleep(500);
		return new ModelAndView("/templates/sys/role-add");
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public EossRes<Boolean> add(HttpServletRequest request, @ModelAttribute RolePermParam v) throws Exception {
		this.roleService.txAdd(v);
		return EossRes.success();
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public EossRes<Boolean> delete(HttpServletRequest request, @RequestParam(name = "ids") List<Integer> ids) throws Exception {
		roleService.txDelete(ids);
		return EossRes.success();
	}
}
