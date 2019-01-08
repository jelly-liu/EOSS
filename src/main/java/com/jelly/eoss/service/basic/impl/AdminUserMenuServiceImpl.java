
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminUserMenu;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMenuMapper;
import com.jelly.eoss.service.basic.AdminUserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserMenuServiceImpl implements AdminUserMenuService{

	@Autowired
    private AdminUserMenuMapper adminUserMenuMapper;

	public List<AdminUserMenu> select(AdminUserMenu adminUserMenu){
		return adminUserMenuMapper.select(adminUserMenu);
	}

    public List<AdminUserMenu> selectPage(AdminUserMenu adminUserMenu){
        return adminUserMenuMapper.selectPage(adminUserMenu);
    }

	public Integer selectCount(AdminUserMenu adminUserMenu){
		return adminUserMenuMapper.selectCount(adminUserMenu);
	}

	@Override
	public AdminUserMenu selectByPk() {
        return adminUserMenuMapper.selectByPk();
	}

    public AdminUserMenu selectOne(AdminUserMenu adminUserMenu){
        List<AdminUserMenu> list = adminUserMenuMapper.select(adminUserMenu);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminUserMenu adminUserMenu) {
            adminUserMenuMapper.insert(adminUserMenu);
	}

    @Override
    public void update(AdminUserMenu adminUserMenu) {
        adminUserMenuMapper.update(adminUserMenu);
    }

	@Override
	public void updateWithNull(AdminUserMenu adminUserMenu) {
        adminUserMenuMapper.updateWithNull(adminUserMenu);
	}

    @Override
    public void deleteByPk(){
        adminUserMenuMapper.deleteByPk();
    }

    @Override
    public void deleteByPojo(AdminUserMenu adminUserMenu){
        adminUserMenuMapper.deleteByPojo(adminUserMenu);
    }

}