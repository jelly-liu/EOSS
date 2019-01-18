
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



    public Integer selectCount(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectPage(AdminFilterchainDefinition adminFilterchainDefinition);

    public List<AdminFilterchainDefinition> select(AdminFilterchainDefinition adminFilterchainDefinition);
    public AdminFilterchainDefinition selectOne(AdminFilterchainDefinition adminFilterchainDefinition);
    public List<AdminFilterchainDefinition> selectAll();
    public AdminFilterchainDefinition selectByPk(Integer id);

    public int insert(AdminFilterchainDefinition adminFilterchainDefinition);
    public int update(AdminFilterchainDefinition adminFilterchainDefinition);
    public int updateWithNull(AdminFilterchainDefinition adminFilterchainDefinition);
    public int deleteByPk(Integer id);
    public int delete(AdminFilterchainDefinition adminFilterchainDefinition);
}