
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminUserMenu;

@Mapper
@Repository
public interface AdminUserMenuMapper {


    public Integer selectCount(AdminUserMenu adminUserMenu);
    public List<AdminUserMenu> selectPage(AdminUserMenu adminUserMenu);

    public List<AdminUserMenu> select(AdminUserMenu adminUserMenu);
    public AdminUserMenu selectOne(AdminUserMenu adminUserMenu);
    public List<AdminUserMenu> selectAll();
    public AdminUserMenu selectByPk();

    public int insert(AdminUserMenu adminUserMenu);
    public int update(AdminUserMenu adminUserMenu);
    public int updateWithNull(AdminUserMenu adminUserMenu);
    public int deleteByPk();
    public int delete(AdminUserMenu adminUserMenu);
}