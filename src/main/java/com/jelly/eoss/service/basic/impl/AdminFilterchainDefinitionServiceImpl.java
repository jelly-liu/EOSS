
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic.impl;


import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import com.jelly.eoss.db.mapper.basic.iface.AdminFilterchainDefinitionMapper;
import com.jelly.eoss.service.basic.AdminFilterchainDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFilterchainDefinitionServiceImpl implements AdminFilterchainDefinitionService{


	@Autowired
    private AdminFilterchainDefinitionMapper adminFilterchainDefinitionMapper;

	public List<AdminFilterchainDefinition> select(AdminFilterchainDefinition adminFilterchainDefinition){
		return adminFilterchainDefinitionMapper.select(adminFilterchainDefinition);
	}

    public List<AdminFilterchainDefinition> selectPage(AdminFilterchainDefinition adminFilterchainDefinition){
        return adminFilterchainDefinitionMapper.selectPage(adminFilterchainDefinition);
    }

	public Integer selectCount(AdminFilterchainDefinition adminFilterchainDefinition){
		return adminFilterchainDefinitionMapper.selectCount(adminFilterchainDefinition);
	}

	@Override
	public AdminFilterchainDefinition selectByPk(Integer id) {
        return adminFilterchainDefinitionMapper.selectByPk(id);
	}

    public AdminFilterchainDefinition selectOne(AdminFilterchainDefinition adminFilterchainDefinition){
        List<AdminFilterchainDefinition> list = adminFilterchainDefinitionMapper.select(adminFilterchainDefinition);
        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

	@Override
	public void insert(AdminFilterchainDefinition adminFilterchainDefinition) {
            adminFilterchainDefinitionMapper.insert(adminFilterchainDefinition);
	}

    @Override
    public void update(AdminFilterchainDefinition adminFilterchainDefinition) {
        adminFilterchainDefinitionMapper.update(adminFilterchainDefinition);
    }

	@Override
	public void updateWithNull(AdminFilterchainDefinition adminFilterchainDefinition) {
        adminFilterchainDefinitionMapper.updateWithNull(adminFilterchainDefinition);
	}

    @Override
    public void deleteByPk(Integer id){
        adminFilterchainDefinitionMapper.deleteByPk(id);
    }

    @Override
    public void deleteByPojo(AdminFilterchainDefinition adminFilterchainDefinition){
        adminFilterchainDefinitionMapper.deleteByPojo(adminFilterchainDefinition);
    }

}