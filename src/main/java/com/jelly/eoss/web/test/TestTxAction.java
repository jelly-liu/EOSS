package com.jelly.eoss.web.test;

import com.jelly.eoss.db.entity.AdminUserRole;
import com.jelly.eoss.db.mapper.basic.iface.AdminMenuMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserMapper;
import com.jelly.eoss.db.mapper.basic.iface.AdminUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

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

    @RequestMapping("/test/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("-----------received notify");

        String mchnt_cd = request.getParameter("mchnt_cd");
        String mchnt_txn_ssn = request.getParameter("mchnt_txn_ssn");
        String mchnt_txn_dt = request.getParameter("mchnt_txn_dt");
        String amt = request.getParameter("amt");
        String resp_code = request.getParameter("resp_code");
        String signature = request.getParameter("signature");

        String allParams = "";
        allParams += "mchnt_cd=" + mchnt_cd + "&";
        allParams += "mchnt_txn_ssn=" + mchnt_txn_ssn + "&";
        allParams += "mchnt_txn_dt=" + mchnt_txn_dt + "&";
        allParams += "amt=" + amt + "&";
        allParams += "resp_code=" + resp_code + "&";
        allParams += "signature=" + signature;


        String tmp = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ap>\n" +
                "<plain>\n" +
                "<resp_code>0000</resp_code> <mchnt_cd>0003930F0074981</mchnt_cd> <mchnt_txn_ssn>#ssn#</mchnt_txn_ssn> </plain>\n" +
                "<signature>签名数据</signature> </ap>";

        StringUtils.replace(tmp, "#ssn#", mchnt_txn_ssn);

        System.out.println("----------------------------allParams\n" + allParams);
        System.out.println(tmp);

        response.getWriter().write("sss");
    }
}
