
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;

public class UsersRole {
	public static final String Insert = "com.jelly.eoss.model.UsersRole.Insert";
	public static final String Update = "com.jelly.eoss.model.UsersRole.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.UsersRole.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.UsersRole.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.UsersRole.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.UsersRole.Select";
	public static final String SelectCount = "com.jelly.eoss.model.UsersRole.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.UsersRole.SelectByPk";
	
	private Integer usersId;
	
	private Integer roleId;


	public UsersRole setUsersId (Integer usersId) {
		this.usersId = usersId;
		return this;
	}
	
	public Integer getUsersId () {
		return this.usersId;
	}

	public UsersRole setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}