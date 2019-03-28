
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminUserGroup;

@Mapper
@Repository
public interface AdminUserGroupMapper {



    public Integer selectCount(AdminUserGroup adminUserGroup);
    public List<AdminUserGroup> selectPage(AdminUserGroup adminUserGroup);

    public List<AdminUserGroup> select(AdminUserGroup adminUserGroup);
    public AdminUserGroup selectOne(AdminUserGroup adminUserGroup);
    public List<AdminUserGroup> selectAll();
    public AdminUserGroup selectByPk(Integer userId);

    public int insert(AdminUserGroup adminUserGroup);
    public int update(AdminUserGroup adminUserGroup);
    public int updateWithNull(AdminUserGroup adminUserGroup);
    public int deleteByPk(Integer userId);
    public int delete(AdminUserGroup adminUserGroup);
}