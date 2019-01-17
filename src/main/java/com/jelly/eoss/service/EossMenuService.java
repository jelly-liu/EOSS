package com.jelly.eoss.service;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.mapper.business.iface.LoginExtMapper;
import com.jelly.eoss.db.mapper.business.iface.MenuExtMapper;
import com.jelly.eoss.util.Const;
import com.jelly.eoss.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EossMenuService {
	@Autowired
	LoginExtMapper loginExtMapper;
	@Autowired
	MenuExtMapper menuExtMapper;

    /*
     *数据样例：
     * 1#2#4#7
     * 1#2#4#9
     * 1#2#5#16
     * 将重复的id过滤掉
     */
	public String txQueryMenuTreeIdsOfUser(AdminUser user){
	    if(user == null){
	        return "";
        }

		List<Map<String, Object>> list = loginExtMapper.queryTreePathByUserId(user.getId());
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

		return sb.toString();
	}
	
	/*
	 * 修饰一下znode节点，修改样式等
	 * checkedIds：初始化选中的id
	 * rootNocheck：root节点不能被选择
	 * onlyLeafCanCheck：只有叶子节点才可选择
	 * openAll：是否展开树
	 */
	public void decorateZnode(List<Map<String, Object>> zNodeList, Map<String, Object> pm){
		//处理要选中的checkbox或着radio的id
		Object checkedIdStr = pm.get("checkedIds");
		String[] checkedIds = null;
		Set<String> checkedIdSet = null;
		if(checkedIdStr != null){
			checkedIdSet = new HashSet<String>();
			checkedIds = checkedIdStr.toString().split(",");
			for(String id : checkedIds){
				checkedIdSet.add(id);
			}
		}
		
		for(Map<String, Object> m : zNodeList){
			String id = m.get("id").toString();
			String leaf = m.get("leaf").toString();
			String pId = m.get("pId").toString();
            Object urlObj = m.get("url");
            if(urlObj != null){
                String url = urlObj.toString();
                if(StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(Const.BASE_PATH) && !StringUtils.equals("/", Const.BASE_PATH)){
                    url = Const.BASE_PATH + url;
                    m.put("url", url);
                }
            }

			//config icon of home
			if(pId.equals("-1")){
				m.put("iconSkin", "icon_eoss_home01");
				if(pm.get("rootNocheck") != null){
					m.put("nocheck", "true");
				}
			}
			
			if(leaf.equals("0")){//parent
				m.put("isParent", "true");
				if(pm.get("onlyLeafCanCheck") != null){
					m.put("nocheck", "true");
				}
				if(pm.get("openAll") != null){
					m.put("open", "true");
				}
				if(checkedIdSet != null && checkedIdSet.contains(id)){
					m.put("checked", "true");
				}
			}else if(leaf.equals("1")){//leaf
				m.put("isParent", "false");
//				m.put("target", "centerFrame");
				m.put("iconSkin", "icon_eoss_eidt01");//config icon of leaf
				if(checkedIdSet != null && checkedIdSet.contains(id)){
					m.put("checked", "true");
				}
			}
		}
	}
	
	//根据条件查询子菜单
	public String queryMenuSub(Map<String, Object> pm){
		List<Map<String, Object>> list = menuExtMapper.queryMenuTree(pm);
		this.decorateZnode(list, pm);
		
//		jsonStr=[{"id":1,"name":"系统管理"},{"id":2,"name":"业务管理"}]
		String jsonStr = JsonUtil.toJson(list);
//		Log.Debug(jsonStr);
		return jsonStr;
	}
}
