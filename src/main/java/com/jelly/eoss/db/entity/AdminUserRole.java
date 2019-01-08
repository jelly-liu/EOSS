
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminUserRole extends ConditionDomain {
	public static final String Insert = "AdminUserRole.Insert";
	public static final String Update = "AdminUserRole.Update";
	public static final String UpdateWithNull = "AdminUserRole.UpdateWithNull";
	public static final String DeleteByPk = "AdminUserRole.DeleteByPk";
	public static final String DeleteByPojo = "AdminUserRole.DeleteByPojo";
	public static final String Select = "AdminUserRole.Select";
	public static final String SelectCount = "AdminUserRole.SelectCount";
	public static final String SelectByPk = "AdminUserRole.SelectByPk";
	
	private Integer userId;
	
	private Integer roleId;


	public AdminUserRole setUserId (Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId () {
		return this.userId;
	}

	public AdminUserRole setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}