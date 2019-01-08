
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminPermissionMapper {


    public List<AdminPermission> select(AdminPermission adminPermission);
    public List<AdminPermission> selectPage(AdminPermission adminPermission);
    public Integer selectCount(AdminPermission adminPermission);
    public AdminPermission selectByPk(Integer id);
    public void insert(AdminPermission adminPermission);
    public void update(AdminPermission adminPermission);
    public void updateWithNull(AdminPermission adminPermission);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminPermission adminPermission);
}