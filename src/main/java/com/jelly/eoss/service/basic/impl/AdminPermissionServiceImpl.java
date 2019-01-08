
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminPermission;
import com.jelly.eoss.db.mapper.basic.iface.AdminPermissionMapper;
import com.jelly.eoss.service.basic.AdminPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminPermissionServiceImpl implements AdminPermissionService{


	@Autowired
    private AdminPermissionMapper adminPermissionMapper;

	public List<AdminPermission> select(AdminPermission adminPermission){
		return adminPermissionMapper.select(adminPermission);
	}

    public List<AdminPermission> selectPage(AdminPermission adminPermission){
        return adminPermissionMapper.selectPage(adminPermission);
    }

	public Integer selectCount(AdminPermission adminPermission){
		return adminPermissionMapper.selectCount(adminPermission);
	}

	@Override
	public AdminPermission selectByPk(Integer id) {
        return adminPermissionMapper.selectByPk(id);
	}

    public AdminPermission selectOne(AdminPermission adminPermission){
        List<AdminPermission> list = adminPermissionMapper.select(adminPermission);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminPermission adminPermission) {
            adminPermissionMapper.insert(adminPermission);
	}

    @Override
    public void update(AdminPermission adminPermission) {
        adminPermissionMapper.update(adminPermission);
    }

	@Override
	public void updateWithNull(AdminPermission adminPermission) {
        adminPermissionMapper.updateWithNull(adminPermission);
	}

    @Override
    public void deleteByPk(Integer id){
        adminPermissionMapper.deleteByPk(id);
    }

    @Override
    public void deleteByPojo(AdminPermission adminPermission){
        adminPermissionMapper.deleteByPojo(adminPermission);
    }

}