
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic;

import com.jelly.eoss.db.entity.AdminPermission;

import java.util.List;

public interface AdminPermissionService {


    public List<AdminPermission> select(AdminPermission adminPermission);
    public List<AdminPermission> selectPage(AdminPermission adminPermission);
    public Integer selectCount(AdminPermission adminPermission);
    public AdminPermission selectByPk(Integer id);
    public AdminPermission selectOne(AdminPermission adminPermission);
    public void insert(AdminPermission adminPermission);
    public void update(AdminPermission adminPermission);
    public void updateWithNull(AdminPermission adminPermission);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminPermission adminPermission);
}