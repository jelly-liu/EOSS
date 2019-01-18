
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminRole;

@Mapper
@Repository
public interface AdminRoleMapper {



    public Integer selectCount(AdminRole adminRole);
    public List<AdminRole> selectPage(AdminRole adminRole);

    public List<AdminRole> select(AdminRole adminRole);
    public AdminRole selectOne(AdminRole adminRole);
    public List<AdminRole> selectAll();
    public AdminRole selectByPk(Integer id);

    public int insert(AdminRole adminRole);
    public int update(AdminRole adminRole);
    public int updateWithNull(AdminRole adminRole);
    public int deleteByPk(Integer id);
    public int delete(AdminRole adminRole);
}