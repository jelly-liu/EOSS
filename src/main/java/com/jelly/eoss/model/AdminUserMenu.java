
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminUserMenu extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminUserMenu.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminUserMenu.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminUserMenu.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminUserMenu.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminUserMenu.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminUserMenu.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminUserMenu.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminUserMenu.SelectByPk";
	
	private Integer menuId;
	
	private Integer userId;


	public AdminUserMenu setMenuId (Integer menuId) {
		this.menuId = menuId;
		return this;
	}
	
	public Integer getMenuId () {
		return this.menuId;
	}

	public AdminUserMenu setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId() {
		return this.userId;
	}

}