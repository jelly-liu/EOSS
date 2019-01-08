
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMenuMapper {


    public List<AdminMenu> select(AdminMenu adminMenu);
    public List<AdminMenu> selectPage(AdminMenu adminMenu);
    public Integer selectCount(AdminMenu adminMenu);
    public AdminMenu selectByPk(Integer id);
    public void insert(AdminMenu adminMenu);
    public void update(AdminMenu adminMenu);
    public void updateWithNull(AdminMenu adminMenu);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminMenu adminMenu);
}