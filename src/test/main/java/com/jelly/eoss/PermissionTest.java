package com.jelly.eoss;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Test;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 11:39 AM 2019/3/26
 * @Description：${description}
 */

public class PermissionTest {
    @Test
    public void test1(){
        //if p1 has permission of p2
        WildcardPermission p1 = null;
        WildcardPermission p2 = null;
        boolean flag = false;

        p1 = new WildcardPermission("printer:print,query");
        p2 = new WildcardPermission("printer:print");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("printer:print");
        p2 = new WildcardPermission("printer:print,query");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("*:print");
        p2 = new WildcardPermission("printer,scanner:print");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("*:print");
        p2 = new WildcardPermission("printer:print:epsoncolor");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("printer:*:epsoncolor");
        p2 = new WildcardPermission("printer:print:epsoncolor");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("printer:print:sony");
        p2 = new WildcardPermission("printer:print:epsoncolor");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("printer:*");
        p2 = new WildcardPermission("*:print");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perms");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");

        p1 = new WildcardPermission("printer:print,query");
        p2 = new WildcardPermission("printer:print,query,repair");
        flag = p1.implies(p2);
        System.out.println(String.format("%-22s", p1.toString()) + "user A has a perm");
        System.out.println(String.format("%-22s", p2.toString()) + "resource required this perm");
        System.out.println(flag);
        System.out.println("-----------------------------------");
    }
}
