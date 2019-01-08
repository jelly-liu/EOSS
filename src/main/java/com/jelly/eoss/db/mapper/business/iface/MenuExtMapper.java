package com.jelly.eoss.db.mapper.business.iface;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:07 PM 2019/1/8
 * @Description：${description}
 */

@Repository
public interface MenuExtMapper {
    public List<Map<String, Object>> queryMenuTree(Map param);
    public List<Map<String, Object>> queryAllSubMenu(Integer menuId);
    public List<Map<String, Object>> queryMenuPage(Map param);
    public Integer queryMenuPageCount(Map param);
}
