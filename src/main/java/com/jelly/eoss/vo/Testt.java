package com.jelly.eoss.vo;

import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 7:45 PM 2019/3/22
 * @Description：${description}
 */

public class Testt {
    public static void main(String[] args) {
        //if p1 has the p2 permission or not
        WildcardPermission p1 = new WildcardPermission("*:查询");
        WildcardPermission p2 = new WildcardPermission("查询");
        System.out.println(p1.implies(p2));
    }
}
