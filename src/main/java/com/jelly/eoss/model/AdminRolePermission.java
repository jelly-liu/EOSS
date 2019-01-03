
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminRolePermission extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminRolePermission.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminRolePermission.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminRolePermission.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminRolePermission.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminRolePermission.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminRolePermission.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminRolePermission.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminRolePermission.SelectByPk";
	
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