
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class RolePermission {
	public static final String Insert = "com.jelly.eoss.model.RolePermission.Insert";
	public static final String Update = "com.jelly.eoss.model.RolePermission.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.RolePermission.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.RolePermission.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.RolePermission.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.RolePermission.Select";
	public static final String SelectCount = "com.jelly.eoss.model.RolePermission.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.RolePermission.SelectByPk";
	
	private Integer permissionId;
	
	private Integer roleId;


	public RolePermission setPermissionId (Integer permissionId) {
		this.permissionId = permissionId;
		return this;
	}
	
	public Integer getPermissionId () {
		return this.permissionId;
	}

	public RolePermission setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}