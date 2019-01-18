
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminPermission;

@Mapper
@Repository
public interface AdminPermissionMapper {



    public Integer selectCount(AdminPermission adminPermission);
    public List<AdminPermission> selectPage(AdminPermission adminPermission);

    public List<AdminPermission> select(AdminPermission adminPermission);
    public AdminPermission selectOne(AdminPermission adminPermission);
    public List<AdminPermission> selectAll();
    public AdminPermission selectByPk(Integer id);

    public int insert(AdminPermission adminPermission);
    public int update(AdminPermission adminPermission);
    public int updateWithNull(AdminPermission adminPermission);
    public int deleteByPk(Integer id);
    public int delete(AdminPermission adminPermission);
}