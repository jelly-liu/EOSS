package com.jelly.eoss.db.mapper.business.iface;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:40 PM 2019/1/8
 * @Description：${description}
 */

@Repository
public interface UserExtMapper {
    public Integer queryUserCount(Map<String, Object> param);
    public List<Map<String, Object>> queryUserPage(Map<String, Object> param);
}
