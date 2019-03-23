package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.entity.AdminUserRole;
import com.jelly.eoss.db.mapper.basic.iface.AdminMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 9:32 AM 2019/1/10
 * @Description：${description}
 */

@Controller
public class TestTxAction {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    AdminMenuMapper menuMapper;
    @Autowired
    AdminUserMapper userMapper;
    @Autowired
    AdminUserRoleMapper userRoleMapper;

    @RequestMapping("/test")
    public void txTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int effectRows = userRoleMapper.insert(new AdminUserRole().setUserId(0).setRoleId(0));
        response.getWriter().write("effectRows=" + effectRows);
    }

    @RequestMapping("/thymeleaf")
    public void greeting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("thymeleaf");
    }
}
