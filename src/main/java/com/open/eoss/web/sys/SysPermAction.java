package com.open.eoss.web.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.db.entity.SysPerm;
import com.open.eoss.db.mapper.basic.SysPermMapper;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.service.sys.SysPermService;
import com.open.eoss.service.sys.SysSessionManager;
import com.open.eoss.util.Const;
import com.open.eoss.web.BaseAction;
import com.open.eoss.web.vo.EossRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/sys/perm/")
public class SysPermAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(SysPermAction.class);

	@Autowired
	SysPermMapper permMapper;
	@Autowired
	SysPermService permService;
	@Autowired
	SysSessionManager sysSessionManager;

	@RequestMapping(value = "/to-list")
	public ModelAndView listPage(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		v.initPageAndSize(1, Const.PAGE_SIZE);
		if(v.getPage() < 1){
			v.setPage(1);
		}
		Map<String, Boolean> permMap = new HashMap<>();
		SysSessionInfo sessionInfo = sysSessionManager.getSessionInfo(request);
		Set<String> permSet = sessionInfo.getPerms();
		for (String permUrl : permSet) {
			if(StringUtils.equals(permUrl, "/sys/perm/add")){
				permMap.put("add", true);
			}
			if(StringUtils.equals(permUrl, "/sys/perm/update")){
				permMap.put("update", true);
			}
			if(StringUtils.equals(permUrl, "/sys/perm/delete")){
				permMap.put("delete", true);
			}
		}
		ModelAndView modelAndView = new ModelAndView("/templates/sys/perm-list");
		modelAndView.addObject("permMap", JSON.toJSONString(permMap));
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public EossRes<List<SysPerm>> list(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		Thread.sleep(500);
		return permService.list(v);
	}

	@RequestMapping(value = "/to-update")
	public ModelAndView updatePage(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		SysPerm perm = permMapper.selectByPk(v.getId());
		Thread.sleep(1000);
		ModelAndView modelAndView = new ModelAndView("/templates/sys/perm-update");
		modelAndView.addObject("data", perm);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public EossRes<Boolean> update(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		permService.txUpdate(v);
		return EossRes.success();
	}

	@RequestMapping(value = "/to-add")
	public ModelAndView addPage(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		Thread.sleep(500);
		return new ModelAndView("/templates/sys/perm-add");
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public EossRes<Boolean> add(HttpServletRequest request, @ModelAttribute SysPerm v) throws Exception {
		this.permService.txAdd(v);
		return EossRes.success();
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public EossRes<Boolean> delete(HttpServletRequest request, @RequestParam(name = "ids") List<Integer> ids) throws Exception {
		permService.txDelete(ids);
		return EossRes.success();
	}
}
