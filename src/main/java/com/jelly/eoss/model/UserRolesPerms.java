package com.jelly.eoss.model;

import java.util.Set;

/**
 * Created by jelly on 2016-10-26.
 */
public class UserRolesPerms {
    private Users users;
    private Set<String> rolesOfUser;
    private Set<String> permsOfUser;

    public UserRolesPerms() {
    }

    public UserRolesPerms(Users users, Set<String> rolesOfUser, Set<String> permsOfUser) {
        this.users = users;
        this.rolesOfUser = rolesOfUser;
        this.permsOfUser = permsOfUser;
    }

    public Users getUsers() {
        return users;
    }

    public UserRolesPerms setUsers(Users users) {
        this.users = users;
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
