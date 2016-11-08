package com.jelly.eoss.model;

import java.util.Set;

/**
 * Created by jelly on 2016-10-26.
 */
public class UserRolesPerms {
    private User user;
    private Set<String> rolesOfUser;
    private Set<String> permsOfUser;

    public UserRolesPerms() {
    }

    public UserRolesPerms(User user, Set<String> rolesOfUser, Set<String> permsOfUser) {
        this.user = user;
        this.rolesOfUser = rolesOfUser;
        this.permsOfUser = permsOfUser;
    }

    public User getUser() {
        return user;
    }

    public UserRolesPerms setUser(User user) {
        this.user = user;
        return this;
    }

    public Set<String> getRolesOfUser() {
        return rolesOfUser;
    }

    public UserRolesPerms setRolesOfUser(Set<String> rolesOfUser) {
        this.rolesOfUser = rolesOfUser;
        return this;
    }

    public Set<String> getPermsOfUser() {
        return permsOfUser;
    }

    public UserRolesPerms setPermsOfUser(Set<String> permsOfUser) {
        this.permsOfUser = permsOfUser;
        return this;
    }
}
