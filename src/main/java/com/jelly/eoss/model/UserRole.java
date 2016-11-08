
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class UserRole {
	public static final String Insert = "com.jelly.eoss.model.UserRole.Insert";
	public static final String Update = "com.jelly.eoss.model.UserRole.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.UserRole.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.UserRole.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.UserRole.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.UserRole.Select";
	public static final String SelectCount = "com.jelly.eoss.model.UserRole.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.UserRole.SelectByPk";
	
	private Integer userId;
	
	private Integer roleId;


	public UserRole setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId() {
		return this.userId;
	}

	public UserRole setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}