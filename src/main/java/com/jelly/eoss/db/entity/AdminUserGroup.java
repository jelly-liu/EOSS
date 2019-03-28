
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminUserGroup extends ConditionDomain {
    public static final String TABLE_NAME = "admin_user_group";

	
	private Integer userId;
	
	private Integer groupId;


	public AdminUserGroup setUserId (Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId () {
		return this.userId;
	}

	public AdminUserGroup setGroupId (Integer groupId) {
		this.groupId = groupId;
		return this;
	}
	
	public Integer getGroupId () {
		return this.groupId;
	}

}