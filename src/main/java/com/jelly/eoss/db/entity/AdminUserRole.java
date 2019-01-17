
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminUserRole extends ConditionDomain {
    public static final String TABLE_NAME = "admin_user_role";

	
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