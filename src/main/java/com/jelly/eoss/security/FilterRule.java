package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FilterRule {
    private String pattern;
    private String rule;
    private boolean anno = false;
    private boolean authc = true;
    private Set<String> roleSet = new HashSet<>();
    private Set<String> permSet = new HashSet<>();

    public FilterRule() {
    }

    public boolean userHasRole(Collection<String> rolesOfUser){
        if(CollectionUtils.isEmpty(roleSet)){
            return true;
        }

        for(String role : this.roleSet){
            if(rolesOfUser.contains(role)){
                return true;
            }
        }

        return false;
    }

    public boolean userHasPerm(Collection<String> permsOfUser){
        if(CollectionUtils.isEmpty(permSet)){
            return true;
        }

        for(String perm : this.permSet){
            if(permsOfUser.contains(perm)){
                return true;
            }
        }
        return false;
    }

    public String getRule() {
        return rule;
    }

    public FilterRule setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public boolean getAnno() {
        return anno;
    }

    public FilterRule setAnno(boolean anno) {
        this.anno = anno;
        return this;
    }

    public boolean getAuthc() {
        return authc;
    }

    public FilterRule setAuthc(boolean authc) {
        this.authc = authc;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public FilterRule setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public FilterRule setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
        return this;
    }

    public Set<String> getPermSet() {
        return permSet;
    }

    public FilterRule setPermSet(Set<String> permSet) {
        this.permSet = permSet;
        return this;
    }
}
