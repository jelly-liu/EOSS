package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:32 AM 2019/1/10
 * @Description：${description}
 */

@Controller
public class AdminTestController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AdminUserMapper userMapper;

    @RequestMapping("/test")
    public void txTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AdminUser user = new AdminUser().setId(1001).setUsername("tom").setPassword("cat").setCreateDatetime("2019");
        userMapper.insert(user);
        throw new RuntimeException("mock tx auto rollback");
    }
}
