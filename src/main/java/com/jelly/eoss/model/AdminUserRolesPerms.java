package com.jelly.eoss.model;

import java.util.Set;

/**
 * Created by jelly on 2016-10-26.
 */
public class AdminUserRolesPerms extends ConditionDomain {
    private AdminUser user;
    private Set<String> rolesOfUser;
    private Set<String> permsOfUser;

    public AdminUserRolesPerms() {
    }

    public AdminUserRolesPerms(AdminUser user, Set<String> rolesOfUser, Set<String> permsOfUser) {
        this.user = user;
        this.rolesOfUser = rolesOfUser;
        this.permsOfUser = permsOfUser;
    }

    public AdminUser getUser() {
        return user;
    }

    public AdminUserRolesPerms setUser(AdminUser user) {
        this.user = user;
        return this;
    }

    public Set<String> getRolesOfUser() {
        return rolesOfUser;
    }

    public AdminUserRolesPerms setRolesOfUser(Set<String> rolesOfUser) {
        this.rolesOfUser = rolesOfUser;
        return this;
    }

    public Set<String> getPermsOfUser() {
        return permsOfUser;
    }

    public AdminUserRolesPerms setPermsOfUser(Set<String> permsOfUser) {
        this.permsOfUser = permsOfUser;
        return this;
    }
}
