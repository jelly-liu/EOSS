
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminFilterchainDefinition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminFilterchainDefinitionMapper {


    public List<AdminFilterchainDefinition> select(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectPage(AdminFilterchainDefinition adminFilterchainDefinition);
    public Integer selectCount(AdminFilterchainDefinition adminFilterchainDefinition);
    public AdminFilterchainDefinition selectByPk(Integer id);
    public void insert(AdminFilterchainDefinition adminFilterchainDefinition);
    public void update(AdminFilterchainDefinition adminFilterchainDefinition);
    public void updateWithNull(AdminFilterchainDefinition adminFilterchainDefinition);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminFilterchainDefinition adminFilterchainDefinition);
}