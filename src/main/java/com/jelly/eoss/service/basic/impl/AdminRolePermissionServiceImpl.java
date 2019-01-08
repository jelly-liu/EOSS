
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminRolePermission;
import com.jelly.eoss.db.mapper.basic.iface.AdminRolePermissionMapper;
import com.jelly.eoss.service.basic.AdminRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRolePermissionServiceImpl implements AdminRolePermissionService{

	@Autowired
    private AdminRolePermissionMapper adminRolePermissionMapper;

	public List<AdminRolePermission> select(AdminRolePermission adminRolePermission){
		return adminRolePermissionMapper.select(adminRolePermission);
	}

    public List<AdminRolePermission> selectPage(AdminRolePermission adminRolePermission){
        return adminRolePermissionMapper.selectPage(adminRolePermission);
    }

	public Integer selectCount(AdminRolePermission adminRolePermission){
		return adminRolePermissionMapper.selectCount(adminRolePermission);
	}

	@Override
	public AdminRolePermission selectByPk() {
        return adminRolePermissionMapper.selectByPk();
	}

    public AdminRolePermission selectOne(AdminRolePermission adminRolePermission){
        List<AdminRolePermission> list = adminRolePermissionMapper.select(adminRolePermission);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminRolePermission adminRolePermission) {
            adminRolePermissionMapper.insert(adminRolePermission);
	}

    @Override
    public void update(AdminRolePermission adminRolePermission) {
        adminRolePermissionMapper.update(adminRolePermission);
    }

	@Override
	public void updateWithNull(AdminRolePermission adminRolePermission) {
        adminRolePermissionMapper.updateWithNull(adminRolePermission);
	}

    @Override
    public void deleteByPk(){
        adminRolePermissionMapper.deleteByPk();
    }

    @Override
    public void deleteByPojo(AdminRolePermission adminRolePermission){
        adminRolePermissionMapper.deleteByPojo(adminRolePermission);
    }

}