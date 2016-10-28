package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */

import com.google.common.io.LineReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class FilterRuleFactory {
    private static final Logger log = LoggerFactory.getLogger(FilterRuleFactory.class);

    public static final String EQUAL = "=";
    public static final String COMMA = ",";
    public static final String MID_BRACE_START = "[";
    public static final String SEMICOLON = ";";
    public static final String COLON = ":";
    public static final String MID_BRACE_END = "]";

    public static List<FilterRule> initRuleByFilterDefinition(String filterDefinition){
        List<FilterRule> filterRuleList = new LinkedList<>();

        LineReader lineReader = null;
        try {
            lineReader = new LineReader(new StringReader(filterDefinition));
            String line = null;
            while((line = lineReader.readLine()) != null){
                line = StringUtils.trimToNull(line);
                if(line == null){
                    continue;
                }

                if(StringUtils.startsWith(line, "#")){
                    continue;
                }

                FilterRule filterRule = parseLine(line);
                if(filterRule == null){
                    continue;
                }

                filterRuleList.add(filterRule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filterRuleList;
    }

    private static FilterRule parseLine(String line){
        FilterRule filterRule = null;
        String[] lineSegment = StringUtils.split(line, EQUAL);
        if(lineSegment == null || lineSegment.length != 2){
            log.debug("can not find = in line={}", line);
            return null;
        }

        String pattern = StringUtils.trimToNull(lineSegment[0]);
        String ruleSegment = StringUtils.trimToNull(lineSegment[1]);

        if(StringUtils.isEmpty(pattern)){
            log.debug("pattern of line is empty, line={}", line);
            return null;
        }

        if(StringUtils.isEmpty(ruleSegment)){
            log.debug("rule of line is empty, line={}", line);
            return null;
        }

        filterRule = new FilterRule();
        filterRule.setPattern(pattern);
        filterRule.setRule(ruleSegment);

        String[] rules = StringUtils.split(ruleSegment, SEMICOLON);
        for(String rule : rules){
            rule = StringUtils.trimToNull(rule);
            if(StringUtils.isEmpty(rule)){
                continue;
            }

            if(StringUtils.equals(rule, RuleKey.anon.toString())){
                filterRule.setAnno(true);
            }else if(StringUtils.startsWith(rule, RuleKey.roles.toString())){
                parseRoles(filterRule, rule);
            }else if(StringUtils.startsWith(rule, RuleKey.perms.toString())){
                parsePerms(filterRule, rule);
            }
        }

        return filterRule;
    }

    /**
     * /admin*//** = authc, roles[administrator]
     */
    private static void parseRoles(FilterRule filterRule, String rule){
        String roleStr = StringUtils.substringBetween(rule, MID_BRACE_START, MID_BRACE_END);
        String[] roles = StringUtils.split(roleStr, COMMA);
        for(String role : roles){
            role = StringUtils.trimToEmpty(role);
            if(StringUtils.isNotEmpty(role)){
                filterRule.getRoleSet().add(role);
                log.debug("filter rule init, add role={}", role);
            }
        }
    }

    /**
     * /remoting/rpc*//** = authc, perms[remote:invoke]
     */
    private static void parsePerms(FilterRule filterRule, String rule){
        String permStr = StringUtils.substringBetween(rule, MID_BRACE_START, MID_BRACE_END);
        String[] perms = StringUtils.split(permStr, COMMA);
        for(int i = 0; i < perms.length; i++){
            String perm = StringUtils.trimToEmpty(perms[i]);

            if(StringUtils.isNotEmpty(perm)){
                filterRule.getPermSet().add(perm);
                log.debug("filter rule init, add perm={}", perm);
            }

            if(i == 0){
                String permAll = StringUtils.substringBefore(perm, COLON);
                permAll += ":*";
                filterRule.getPermSet().add(permAll);
                log.debug("filter rule init, add permAll={}", permAll);
            }
        }
    }
}
