
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminUser;

@Mapper
@Repository
public interface AdminUserMapper {



    public Integer selectCount(AdminUser adminUser);
    public List<AdminUser> selectPage(AdminUser adminUser);

    public List<AdminUser> select(AdminUser adminUser);
    public AdminUser selectOne(AdminUser adminUser);
    public List<AdminUser> selectAll();
    public AdminUser selectByPk(Integer id);

    public int insert(AdminUser adminUser);
    public int update(AdminUser adminUser);
    public int updateWithNull(AdminUser adminUser);
    public int deleteByPk(Integer id);
    public int delete(AdminUser adminUser);
}