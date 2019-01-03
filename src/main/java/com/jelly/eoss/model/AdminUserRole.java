
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminUserRole extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminUserRole.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminUserRole.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminUserRole.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminUserRole.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminUserRole.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminUserRole.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminUserRole.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminUserRole.SelectByPk";
	
	private Integer userId;
	
	private Integer roleId;


	public AdminUserRole setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId() {
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