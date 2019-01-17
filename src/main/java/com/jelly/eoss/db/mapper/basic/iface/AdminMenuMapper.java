
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminMenu;

@Mapper
@Repository
public interface AdminMenuMapper {


    public List<AdminMenu> select(AdminMenu adminMenu);
    public List<AdminMenu> selectPage(AdminMenu adminMenu);
    public List<AdminMenu> selectAll();
    public Integer selectCount(AdminMenu adminMenu);
    public AdminMenu selectByPk(Integer id);
    public AdminMenu selectOne(AdminMenu adminMenu);
    public void insert(AdminMenu adminMenu);
    public void update(AdminMenu adminMenu);
    public void updateWithNull(AdminMenu adminMenu);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminMenu adminMenu);
}