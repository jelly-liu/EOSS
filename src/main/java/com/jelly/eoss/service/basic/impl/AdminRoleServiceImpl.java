
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminRole;
import com.jelly.eoss.db.mapper.basic.iface.AdminRoleMapper;
import com.jelly.eoss.service.basic.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService{


	@Autowired
    private AdminRoleMapper adminRoleMapper;

	public List<AdminRole> select(AdminRole adminRole){
		return adminRoleMapper.select(adminRole);
	}

    public List<AdminRole> selectPage(AdminRole adminRole){
        return adminRoleMapper.selectPage(adminRole);
    }

	public Integer selectCount(AdminRole adminRole){
		return adminRoleMapper.selectCount(adminRole);
	}

	@Override
	public AdminRole selectByPk(Integer id) {
        return adminRoleMapper.selectByPk(id);
	}

    public AdminRole selectOne(AdminRole adminRole){
        List<AdminRole> list = adminRoleMapper.select(adminRole);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminRole adminRole) {
            adminRoleMapper.insert(adminRole);
	}

    @Override
    public void update(AdminRole adminRole) {
        adminRoleMapper.update(adminRole);
    }

	@Override
	public void updateWithNull(AdminRole adminRole) {
        adminRoleMapper.updateWithNull(adminRole);
	}

    @Override
    public void deleteByPk(Integer id){
        adminRoleMapper.deleteByPk(id);
    }

    @Override
    public void deleteByPojo(AdminRole adminRole){
        adminRoleMapper.deleteByPojo(adminRole);
    }

}