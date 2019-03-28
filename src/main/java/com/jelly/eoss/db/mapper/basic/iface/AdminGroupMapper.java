
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminGroup;

@Mapper
@Repository
public interface AdminGroupMapper {



    public Integer selectCount(AdminGroup adminGroup);
    public List<AdminGroup> selectPage(AdminGroup adminGroup);

    public List<AdminGroup> select(AdminGroup adminGroup);
    public AdminGroup selectOne(AdminGroup adminGroup);
    public List<AdminGroup> selectAll();
    public AdminGroup selectByPk(Integer id);

    public int insert(AdminGroup adminGroup);
    public int update(AdminGroup adminGroup);
    public int updateWithNull(AdminGroup adminGroup);
    public int deleteByPk(Integer id);
    public int delete(AdminGroup adminGroup);
}