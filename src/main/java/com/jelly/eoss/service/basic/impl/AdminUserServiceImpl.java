
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
import com.jelly.eoss.service.basic.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService{


	@Autowired
    private AdminUserMapper adminUserMapper;

	public List<AdminUser> select(AdminUser adminUser){
		return adminUserMapper.select(adminUser);
	}

    public List<AdminUser> selectPage(AdminUser adminUser){
        return adminUserMapper.selectPage(adminUser);
    }

	public Integer selectCount(AdminUser adminUser){
		return adminUserMapper.selectCount(adminUser);
	}

	@Override
	public AdminUser selectByPk(Integer id) {
        return adminUserMapper.selectByPk(id);
	}

    public AdminUser selectOne(AdminUser adminUser){
        List<AdminUser> list = adminUserMapper.select(adminUser);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminUser adminUser) {
            adminUserMapper.insert(adminUser);
	}

    @Override
    public void update(AdminUser adminUser) {
        adminUserMapper.update(adminUser);
    }

	@Override
	public void updateWithNull(AdminUser adminUser) {
        adminUserMapper.updateWithNull(adminUser);
	}

    @Override
    public void deleteByPk(Integer id){
        adminUserMapper.deleteByPk(id);
    }

    @Override
    public void deleteByPojo(AdminUser adminUser){
        adminUserMapper.deleteByPojo(adminUser);
    }

}