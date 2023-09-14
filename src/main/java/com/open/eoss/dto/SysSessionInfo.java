package com.open.eoss.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.open.eoss.db.entity.SysUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysSessionInfo {
    private SysUser user;
    // 用户拥有的所有权限
    // 1，用户 - 角色 - 权限
    // 2，用户 - 权限
    private Set<String> perms;
    private Set<Integer> permIds;
}
