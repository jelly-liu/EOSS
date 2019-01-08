
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import com.jelly.eoss.db.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminUserMapper {


    public List<AdminUser> select(AdminUser adminUser);
    public List<AdminUser> selectPage(AdminUser adminUser);
    public Integer selectCount(AdminUser adminUser);
    public AdminUser selectByPk(Integer id);
    public void insert(AdminUser adminUser);
    public void update(AdminUser adminUser);
    public void updateWithNull(AdminUser adminUser);
    public void deleteByPk(Integer id);
    public void deleteByPojo(AdminUser adminUser);
}