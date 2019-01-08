
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminUserMenuMapper {

    public List<AdminUserMenu> select(AdminUserMenu adminUserMenu);
    public List<AdminUserMenu> selectPage(AdminUserMenu adminUserMenu);
    public Integer selectCount(AdminUserMenu adminUserMenu);
    public AdminUserMenu selectByPk();
    public void insert(AdminUserMenu adminUserMenu);
    public void update(AdminUserMenu adminUserMenu);
    public void updateWithNull(AdminUserMenu adminUserMenu);
    public void deleteByPk();
    public void deleteByPojo(AdminUserMenu adminUserMenu);
}