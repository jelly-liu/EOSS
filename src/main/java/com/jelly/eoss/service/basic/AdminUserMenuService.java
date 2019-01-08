
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic;

import com.jelly.eoss.db.entity.AdminUserMenu;

import java.util.List;

public interface AdminUserMenuService {

    public List<AdminUserMenu> select(AdminUserMenu adminUserMenu);
    public List<AdminUserMenu> selectPage(AdminUserMenu adminUserMenu);
    public Integer selectCount(AdminUserMenu adminUserMenu);
    public AdminUserMenu selectByPk();
    public AdminUserMenu selectOne(AdminUserMenu adminUserMenu);
    public void insert(AdminUserMenu adminUserMenu);
    public void update(AdminUserMenu adminUserMenu);
    public void updateWithNull(AdminUserMenu adminUserMenu);
    public void deleteByPk();
    public void deleteByPojo(AdminUserMenu adminUserMenu);
}