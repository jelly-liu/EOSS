package com.open.eoss.web.vo;

import com.open.eoss.db.entity.SysRole;

import java.util.List;

public class RolePermParam extends SysRole {
    private List<Integer> permIds;

    public List<Integer> getPermIds() {
        return permIds;
    }

    public void setPermIds(List<Integer> permIds) {
        this.permIds = permIds;
    }
}
