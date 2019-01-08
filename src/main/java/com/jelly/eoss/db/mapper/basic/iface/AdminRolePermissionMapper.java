
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminRolePermissionMapper {

    public List<AdminRolePermission> select(AdminRolePermission adminRolePermission);
    public List<AdminRolePermission> selectPage(AdminRolePermission adminRolePermission);
    public Integer selectCount(AdminRolePermission adminRolePermission);
    public AdminRolePermission selectByPk();
    public void insert(AdminRolePermission adminRolePermission);
    public void update(AdminRolePermission adminRolePermission);
    public void updateWithNull(AdminRolePermission adminRolePermission);
    public void deleteByPk();
    public void deleteByPojo(AdminRolePermission adminRolePermission);
}