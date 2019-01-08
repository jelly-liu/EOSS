
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.service.basic;

import com.jelly.eoss.db.entity.AdminMenu;

import java.util.List;

public interface AdminMenuService {


    public List<AdminMenu> select(AdminMenu adminMenu);
    public List<AdminMenu> selectPage(AdminMenu adminMenu);
    public Integer selectCount(AdminMenu adminMenu);
    public AdminMenu selectByPk(Integer id);
    public AdminMenu selectOne(AdminMenu adminMenu);
    public void insert(AdminMenu adminMenu);
    public void update(AdminMenu adminMenu);
    public void updateWithNull(AdminMenu adminMenu);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminMenu adminMenu);
}