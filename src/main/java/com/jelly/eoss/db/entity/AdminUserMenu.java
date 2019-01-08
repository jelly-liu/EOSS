
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminUserMenu extends ConditionDomain {

	
	private Integer menuId;
	
	private Integer userId;


	public AdminUserMenu setMenuId (Integer menuId) {
		this.menuId = menuId;
		return this;
	}
	
	public Integer getMenuId () {
		return this.menuId;
	}

	public AdminUserMenu setUserId (Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId () {
		return this.userId;
	}

}