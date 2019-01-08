
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminUserRole;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserRoleMapper;
import com.jelly.eoss.service.basic.AdminUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserRoleServiceImpl implements AdminUserRoleService{



	@Autowired
    private AdminUserRoleMapper adminUserRoleMapper;

	public List<AdminUserRole> select(AdminUserRole adminUserRole){
		return adminUserRoleMapper.select(adminUserRole);
	}

    public List<AdminUserRole> selectPage(AdminUserRole adminUserRole){
        return adminUserRoleMapper.selectPage(adminUserRole);
    }

	public Integer selectCount(AdminUserRole adminUserRole){
		return adminUserRoleMapper.selectCount(adminUserRole);
	}

	@Override
	public AdminUserRole selectByPk(Integer userId, Integer roleId) {
        return adminUserRoleMapper.selectByPk(userId, roleId);
	}

    public AdminUserRole selectOne(AdminUserRole adminUserRole){
        List<AdminUserRole> list = adminUserRoleMapper.select(adminUserRole);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminUserRole adminUserRole) {
            adminUserRoleMapper.insert(adminUserRole);
	}

    @Override
    public void update(AdminUserRole adminUserRole) {
        adminUserRoleMapper.update(adminUserRole);
    }

	@Override
	public void updateWithNull(AdminUserRole adminUserRole) {
        adminUserRoleMapper.updateWithNull(adminUserRole);
	}

    @Override
    public void deleteByPk(Integer userId, Integer roleId){
        adminUserRoleMapper.deleteByPk(userId, roleId);
    }

    @Override
    public void deleteByPojo(AdminUserRole adminUserRole){
        adminUserRoleMapper.deleteByPojo(adminUserRole);
    }

}