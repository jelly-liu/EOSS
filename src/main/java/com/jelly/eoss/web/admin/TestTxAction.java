package com.jelly.eoss.web.admin;

import com.jelly.eoss.db.entity.AdminUser;
import com.jelly.eoss.db.mapper.basic.iface.AdminMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
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

    @RequestMapping("/thymeleaf")
    public ModelAndView greeting(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView("test.htm");

        List<AdminUser> userList = userMapper.select(null);
        view.addObject("userList", userList);

        return view;
    }
}
