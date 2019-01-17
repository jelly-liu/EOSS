
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminFilterchainDefinition;

@Mapper
@Repository
public interface AdminFilterchainDefinitionMapper {


    public List<AdminFilterchainDefinition> select(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectPage(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectAll();
    public Integer selectCount(AdminFilterchainDefinition adminFilterchainDefinition);
    public AdminFilterchainDefinition selectByPk(Integer id);
    public AdminFilterchainDefinition selectOne(AdminFilterchainDefinition adminFilterchainDefinition);
    public void insert(AdminFilterchainDefinition adminFilterchainDefinition);
    public void update(AdminFilterchainDefinition adminFilterchainDefinition);
    public void updateWithNull(AdminFilterchainDefinition adminFilterchainDefinition);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminFilterchainDefinition adminFilterchainDefinition);
}