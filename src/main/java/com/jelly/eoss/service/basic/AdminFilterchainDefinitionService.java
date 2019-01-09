
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminFilterchainDefinitionService {


    public List<AdminFilterchainDefinition> select(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectPage(AdminFilterchainDefinition adminFilterchainDefinition);
    public Integer selectCount(AdminFilterchainDefinition adminFilterchainDefinition);
    public AdminFilterchainDefinition selectByPk(Integer id);
    public AdminFilterchainDefinition selectOne(AdminFilterchainDefinition adminFilterchainDefinition);
    public void insert(AdminFilterchainDefinition adminFilterchainDefinition);
    public void update(AdminFilterchainDefinition adminFilterchainDefinition);
    public void updateWithNull(AdminFilterchainDefinition adminFilterchainDefinition);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminFilterchainDefinition adminFilterchainDefinition);
}