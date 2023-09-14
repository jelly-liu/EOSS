package com.open.eoss.web.vo;

import com.open.eoss.db.entity.SysUser;

import java.util.List;

public class UserRoleParam extends SysUser {
    private List<Integer> roleIds;
    private List<Integer> permIds;

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Integer> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<Integer> permIds) {
        this.permIds = permIds;
    }
}
