
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminGroupMenu extends ConditionDomain {
    public static final String TABLE_NAME = "admin_group_menu";

	
	private Integer groupId;
	
	private Integer menuId;


	public AdminGroupMenu setGroupId (Integer groupId) {
		this.groupId = groupId;
		return this;
	}
	
	public Integer getGroupId () {
		return this.groupId;
	}

	public AdminGroupMenu setMenuId (Integer menuId) {
		this.menuId = menuId;
		return this;
	}
	
	public Integer getMenuId () {
		return this.menuId;
	}

}