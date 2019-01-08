
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminMenu;
import com.jelly.eoss.db.mapper.basic.iface.AdminMenuMapper;
import com.jelly.eoss.service.basic.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMenuServiceImpl implements AdminMenuService{


	@Autowired
    private AdminMenuMapper adminMenuMapper;

	public List<AdminMenu> select(AdminMenu adminMenu){
		return adminMenuMapper.select(adminMenu);
	}

    public List<AdminMenu> selectPage(AdminMenu adminMenu){
        return adminMenuMapper.selectPage(adminMenu);
    }

	public Integer selectCount(AdminMenu adminMenu){
		return adminMenuMapper.selectCount(adminMenu);
	}

	@Override
	public AdminMenu selectByPk(Integer id) {
        return adminMenuMapper.selectByPk(id);
	}

    public AdminMenu selectOne(AdminMenu adminMenu){
        List<AdminMenu> list = adminMenuMapper.select(adminMenu);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminMenu adminMenu) {
            adminMenuMapper.insert(adminMenu);
	}

    @Override
    public void update(AdminMenu adminMenu) {
        adminMenuMapper.update(adminMenu);
    }

	@Override
	public void updateWithNull(AdminMenu adminMenu) {
        adminMenuMapper.updateWithNull(adminMenu);
	}

    @Override
    public void deleteByPk(Integer id){
        adminMenuMapper.deleteByPk(id);
    }

    @Override
    public void deleteByPojo(AdminMenu adminMenu){
        adminMenuMapper.deleteByPojo(adminMenu);
    }

}