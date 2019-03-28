
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.mapper.basic.iface;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.jelly.eoss.db.entity.AdminGroupMenu;

@Mapper
@Repository
public interface AdminGroupMenuMapper {



    public Integer selectCount(AdminGroupMenu adminGroupMenu);
    public List<AdminGroupMenu> selectPage(AdminGroupMenu adminGroupMenu);

    public List<AdminGroupMenu> select(AdminGroupMenu adminGroupMenu);
    public AdminGroupMenu selectOne(AdminGroupMenu adminGroupMenu);
    public List<AdminGroupMenu> selectAll();
    public AdminGroupMenu selectByPk(Integer groupId);

    public int insert(AdminGroupMenu adminGroupMenu);
    public int update(AdminGroupMenu adminGroupMenu);
    public int updateWithNull(AdminGroupMenu adminGroupMenu);
    public int deleteByPk(Integer groupId);
    public int delete(AdminGroupMenu adminGroupMenu);
}