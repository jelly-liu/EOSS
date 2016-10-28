package com.jelly.eoss.security;

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
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
    private String loginUrl;

    public void init(String main, String filterDefinition){
        initMain(main);
        filterRuleList = FilterRuleFactory.initRuleByFilterDefinition(filterDefinition);
    }

    private void initMain(String main){
        if(StringUtils.isEmpty(main)){
            return;
        }

        LineReader lineReader = null;
        try {
            lineReader = new LineReader(new StringReader(main));
            String line = null;
            while((line = lineReader.readLine()) != null){
                line = StringUtils.trimToNull(line);
                if(line == null){
                    continue;
                }

                String[] nameValue = StringUtils.split(main, FilterRuleFactory.EQUAL);
                String name = StringUtils.trimToEmpty(nameValue[0]);
                String value = StringUtils.trimToEmpty(nameValue[1]);

                switch (name){
                    case "loginUrl":{
                        this.loginUrl = value;
                        log.debug("loginUrl={}", loginUrl);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean doFilter(String path, boolean authc, Collection<String> rolesOfUser, Collection<String> permsOfUser){
        if(filterRuleList == null || filterRuleList.size() == 0){
            return true;
        }

        boolean matchPass = false;
        log.debug("------will do filter path={}------", path);
        for(FilterRule filterRule : filterRuleList){
            if(patternMatcher.matches(filterRule.getPattern(), path)){
                if(filterRule.getAnno()){
                    matchPass = true;
                }else if(!authc && filterRule.getAuthc()){
                    matchPass = false;
                }else {
                    boolean userHasRole = filterRule.userHasRole(rolesOfUser);
                    boolean userHasPerm = filterRule.userHasPerm(permsOfUser);

                    if(filterRule.getRoleSet().size() > 0 && filterRule.getPermSet().size() > 0){
                        if(userHasRole || userHasPerm) matchPass = true;
                    }else if(filterRule.getRoleSet().size() > 0 && filterRule.getPermSet().size() == 0){
                        if(userHasRole) matchPass = true;
                    }else if(filterRule.getRoleSet().size() == 0 && filterRule.getPermSet().size() > 0){
                        if(userHasPerm) matchPass = true;
                    }else if(filterRule.getRoleSet().size() == 0 && filterRule.getPermSet().size() == 0){
                        matchPass = true;
                    }
                }
                log.debug("---------access={}, path={}, pattern={}, rule={}", matchPass, path, filterRule.getPattern(), filterRule.getRule());
                break;
            }
        }
        log.debug("------after do filter matchPass={}, path={}------", matchPass, path);
        return matchPass;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public FilterCore setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
        return this;
    }

    public static void main(String[] args) {
        Set<String> rolesOfUser = new HashSet<>();
//        rolesOfUser.add("adminList");
//        rolesOfUser.add("adminAdd");
        rolesOfUser.add("adminSuper");

        Set<String> permsOfUser = new HashSet<>();
        permsOfUser.add("admin:*");

        FilterCore filterCore = new FilterCore();
        filterCore.init(null, "" +
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
