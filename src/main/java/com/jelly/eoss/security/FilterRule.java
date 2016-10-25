package com.jelly.eoss.security;

/**
 * Created by jelly on 2016-10-25.
 */

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 /index.html = anon
 /user/create = anon
 /user/** = authc
 /admin/** = authc, roles[administrator]
 /rest/** = authc, rest
 /remoting/rpc/** = authc, perms[remote:invoke]
 */
public class FilterRule {
    private boolean anno = true;
    private boolean authc = false;
    private String pattern;
    private Set<String> roleSet = new HashSet<>();
    private Set<String> permSet = new HashSet<>();

    public FilterRule(boolean anno, boolean authc, String pattern, Set<String> roleSet, Set<String> permSet) {
        this.anno = anno;
        this.authc = authc;
        this.pattern = pattern;
        this.roleSet = roleSet;
        this.permSet = permSet;
    }

    public boolean hasRole(Set<String> roleOfUser){
        for(String role : this.roleSet){
            if(roleOfUser.contains(role)){
                return true;
            }
        }
        return false;
    }

    public boolean hasPerm(Set<String> permOfUser){
        for(String perm : this.permSet){
            if(permOfUser.contains(perm)){
                return true;
            }
        }
        return false;
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
