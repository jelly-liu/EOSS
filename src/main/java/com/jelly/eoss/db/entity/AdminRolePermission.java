
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminRolePermission extends ConditionDomain {

	
	private Integer permissionId;
	
	private Integer roleId;


	public AdminRolePermission setPermissionId (Integer permissionId) {
		this.permissionId = permissionId;
		return this;
	}
	
	public Integer getPermissionId () {
		return this.permissionId;
	}

	public AdminRolePermission setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}