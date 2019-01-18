
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminUserRole;

@Mapper
@Repository
public interface AdminUserRoleMapper {




    public Integer selectCount(AdminUserRole adminUserRole);
    public List<AdminUserRole> selectPage(AdminUserRole adminUserRole);

    public List<AdminUserRole> select(AdminUserRole adminUserRole);
    public AdminUserRole selectOne(AdminUserRole adminUserRole);
    public List<AdminUserRole> selectAll();
    public AdminUserRole selectByPk(Integer userId, Integer roleId);

    public int insert(AdminUserRole adminUserRole);
    public int update(AdminUserRole adminUserRole);
    public int updateWithNull(AdminUserRole adminUserRole);
    public int deleteByPk(Integer userId, Integer roleId);
    public int delete(AdminUserRole adminUserRole);
}