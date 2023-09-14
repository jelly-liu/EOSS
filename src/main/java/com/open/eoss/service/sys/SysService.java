package com.open.eoss.service.sys;

import com.alibaba.fastjson.JSON;
import com.open.eoss.db.entity.*;
import com.open.eoss.db.mapper.basic.*;
import com.open.eoss.dto.SysSessionInfo;
import com.open.eoss.web.vo.EossRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SysService {
    private static final Logger logger = LoggerFactory.getLogger(SysService.class);

    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysRolePermMapper rolePermMapper;
    @Autowired
    SysSessionManager sysSessionManager;
    @Autowired
    SysPermMapper permMapper;
    @Autowired
    SysUserRoleMapper userRoleMapper;
    @Autowired
    SysUserPermMapper userPermMapper;

    public EossRes<List<SysPerm>> mainWestList(HttpServletRequest request){
        SysSessionInfo sessionInfo = sysSessionManager.getSessionInfo(request);
        SysUser user = sessionInfo.getUser();

        // 用户只能拥有 叶子类型 权限
        // 需要将 叶子类型 权限和所有父结点 遍历出来
        Map<Integer, SysPerm> hadPermMap = new TreeMap<>(Comparator.reverseOrder());
        List<SysPerm> perms = this.permOfUser(user.getId());
        if(!CollectionUtils.isEmpty(perms)){
            List<SysPerm> allPermList = permMapper.selectAll();
            Map<Integer, SysPerm> allPermMap = new HashMap<>();
            if(!CollectionUtils.isEmpty(allPermList)){
                allPermList.forEach(i -> allPermMap.put(i.getId(), i));
            }

            perms.forEach(p -> {
                //只处理 菜单类型 的权限
                if(StringUtils.equals(p.getType(), "menu")){
                    String[] permIdPaths = StringUtils.split(p.getPath(), "#");
                    for (String permIdStr : permIdPaths) {
                        Integer permId = Integer.valueOf(permIdStr);
                        hadPermMap.put(permId, allPermMap.get(permId));
                    }
                }
            });
        }

        List<SysPerm> permList = new LinkedList<>();
        if(!CollectionUtils.isEmpty(hadPermMap))permList.addAll(hadPermMap.values());
        // 测试一下 无权限 的情况
        SysPerm test = new SysPerm();
        test.setId(999);
        test.setPid(2);
        test.setLeaf(1);
        test.setType("menu");
        test.setSortNum(999);
        test.setName("测试无权限");
        test.setUrl("/sys/test");
        permList.add(test);

        // 按sortNum排序
        Collections.sort(permList, Comparator.comparing(SysPerm::getSortNum));

        return EossRes.success(permList == null ? 0 : permList.size(), permList);
    }

    public List<SysPerm> permOfUser(Integer userId){
        List<SysPerm> permList = new LinkedList<>();

        // 用户拥有的角色 -> 角色拥有的权限
        List<SysUserRole> userRoleList = userRoleMapper.select(new SysUserRole().setUserId(userId));
        if(!CollectionUtils.isEmpty(userRoleList)){
            List<Object> roleIdList = new LinkedList<>();
            for(SysUserRole userRole : userRoleList){
                roleIdList.add(userRole.getRoleId());
            }
            logger.info("user -> role, roleIds, {}", JSON.toJSONString(roleIdList));
            // 角色拥有的权限
            if(!CollectionUtils.isEmpty(roleIdList)){
                Map<String, Collection<Object>> inMap = new HashMap<>();
                inMap.put("roleId", roleIdList);
                SysRolePerm query = new SysRolePerm();
                query.setInParams(inMap);
                List<SysRolePerm> rolePermListHad = this.rolePermMapper.select(query);
                if(!CollectionUtils.isEmpty(rolePermListHad)){
                    List<Object> permIds = new LinkedList<>();
                    for(SysRolePerm rolePerm: rolePermListHad){
                        permIds.add(rolePerm.getPermId());
                    }
                    logger.info("user -> role, permIds, {}", JSON.toJSONString(permIds));
                    if(!CollectionUtils.isEmpty(permIds)){
                        inMap.clear();
                        inMap.put("id", permIds);
                        SysPerm permQuery = new SysPerm();
                        permQuery.setInParams(inMap);
                        List<SysPerm> permListHad = this.permMapper.select(permQuery);
                        if(!CollectionUtils.isEmpty(permListHad)) {
                            permList.addAll(permListHad);
                        }
                        logger.info("user -> role, perm url, {}", JSON.toJSONString(permList));
                    }
                }
            }
        }

        // 用户直接拥有的权限
        List<SysUserPerm> userPermList = userPermMapper.select(new SysUserPerm().setUserId(userId));
        if(!CollectionUtils.isEmpty(userPermList)){
            List<Object> permIds = new LinkedList<>();
            for(SysUserPerm userPerm : userPermList){
                permIds.add(userPerm.getPermId());
            }
            Map<String, Collection<Object>> inMap = new HashMap<>();
            inMap.put("id", permIds);
            SysPerm query = new SysPerm();
            query.setInParams(inMap);
            List<SysPerm> permListHad = this.permMapper.select(query);
            logger.info("user -> perms, perm url, {}", JSON.toJSONString(permListHad));
            if(!CollectionUtils.isEmpty(permListHad)){
                permList.addAll(permListHad);
            }
        }

        return permList;
    }
}
