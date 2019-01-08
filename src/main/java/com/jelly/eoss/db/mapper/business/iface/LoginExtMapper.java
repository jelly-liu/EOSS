package com.jelly.eoss.db.mapper.business.iface;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 2:57 PM 2019/1/8
 * @Description：${description}
 */

@Repository
public interface LoginExtMapper {
    public List<Map<String, Object>> queryTreePathByUserId(int userId);
}
