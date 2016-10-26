package com.jelly.eoss.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jelly on 2016-10-26.
 */
public class FilterCore {
    private static final Logger log = LoggerFactory.getLogger(FilterCore.class);

    PatternMatcher patternMatcher = new AntPathMatcher();
    List<FilterRule> filterRuleList = null;

    public void init(String filterDefinition){
        filterRuleList = FilterRuleFactory.initRuleByFilterDefinition(filterDefinition);
    }

    public boolean doFilter(String path, boolean authc, Collection<String> rolesOfUser, Collection<String> permsOfUser){
        if(filterRuleList == null || filterRuleList.size() == 0){
            return true;
        }

        boolean matchPass = true;
        log.debug("------will do filter path={}------", path);
        for(FilterRule filterRule : filterRuleList){
            if(patternMatcher.matches(filterRule.getPattern(), path)){
                if(filterRule.getAnno()){
                    //do nothing
                }else if(!authc && filterRule.getAuthc()){
                    matchPass = false;
                    //do nothing
                }else{
                    boolean userHasRole = filterRule.userHasRole(rolesOfUser);
                    if(!userHasRole){
                        matchPass = false;
                    }

                    boolean userHasPerm = filterRule.userHasPerm(permsOfUser);
                    if(!userHasPerm){
                        matchPass = false;
                    }
                }
                log.debug("---------access={}, path={}, pattern={}, rule={}", matchPass, path, filterRule.getPattern(), filterRule.getRule());
            }
        }
        log.debug("------after do filter matchPass={}, path={}------", matchPass, path);
        return matchPass;
    }

    public static void main(String[] args) {
        Set<String> rolesOfUser = new HashSet<>();
//        rolesOfUser.add("adminList");
//        rolesOfUser.add("adminAdd");
        rolesOfUser.add("adminSuper");

        Set<String> permsOfUser = new HashSet<>();
        permsOfUser.add("admin:*");

        FilterCore filterCore = new FilterCore();
        filterCore.init("" +
                "/ = anon\n" +
                "/static/** = anon\n" +
                "/toLogin.ac = anon\n" +
                "/system/** = auth\n" +
                "/system/menu/** = authc; roles[adminSuper, adminMenu]\n" +
                "/system/*/add = authc; roles[adminSuper]; perms[admin:add]" +
                "");

        filterCore.doFilter("/", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/toLogin.ac", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/static/images", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/static/js/jquery/jquery.js", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/system/permission/toUpdate", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/system/menu/add", true, rolesOfUser, permsOfUser);
        filterCore.doFilter("/system/role/add", false, rolesOfUser, permsOfUser);
    }
}
