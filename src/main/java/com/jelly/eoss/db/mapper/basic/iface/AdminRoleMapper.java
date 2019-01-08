
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminRoleMapper {


    public List<AdminRole> select(AdminRole adminRole);
    public List<AdminRole> selectPage(AdminRole adminRole);
    public Integer selectCount(AdminRole adminRole);
    public AdminRole selectByPk(Integer id);
    public void insert(AdminRole adminRole);
    public void update(AdminRole adminRole);
    public void updateWithNull(AdminRole adminRole);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminRole adminRole);
}