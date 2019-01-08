package com.jelly.eoss.db.mapper.business.iface;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:27 PM 2019/1/8
 * @Description：${description}
 */

@Repository
public interface PermissionExtMapper {
    public Integer queryPermissionPageCount(Map param);
    public List<Map<String, Object>> queryPermissionPage(Map param);
    public List<String> queryByUserId(Integer userId);
}
