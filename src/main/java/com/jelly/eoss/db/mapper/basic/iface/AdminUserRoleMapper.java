
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminUserRole;

@Mapper
@Repository
public interface AdminUserRoleMapper {



    public List<AdminUserRole> select(AdminUserRole adminUserRole);
    public List<AdminUserRole> selectPage(AdminUserRole adminUserRole);
    public List<AdminUserRole> selectAll();
    public Integer selectCount(AdminUserRole adminUserRole);
    public AdminUserRole selectByPk(Integer userId, Integer roleId);
    public AdminUserRole selectOne(AdminUserRole adminUserRole);
    public void insert(AdminUserRole adminUserRole);
    public void update(AdminUserRole adminUserRole);
    public void updateWithNull(AdminUserRole adminUserRole);
    public void deleteByPk(Integer userId, Integer roleId);
    public void deleteByPojo(AdminUserRole adminUserRole);
}