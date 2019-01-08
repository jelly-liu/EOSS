
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic;

import com.jelly.eoss.db.entity.AdminRolePermission;

import java.util.List;

public interface AdminRolePermissionService {

    public List<AdminRolePermission> select(AdminRolePermission adminRolePermission);
    public List<AdminRolePermission> selectPage(AdminRolePermission adminRolePermission);
    public Integer selectCount(AdminRolePermission adminRolePermission);
    public AdminRolePermission selectByPk();
    public AdminRolePermission selectOne(AdminRolePermission adminRolePermission);
    public void insert(AdminRolePermission adminRolePermission);
    public void update(AdminRolePermission adminRolePermission);
    public void updateWithNull(AdminRolePermission adminRolePermission);
    public void deleteByPk();
    public void deleteByPojo(AdminRolePermission adminRolePermission);
}