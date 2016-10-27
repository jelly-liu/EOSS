
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class UserMenu {
	public static final String Insert = "com.jelly.eoss.model.UserMenu.Insert";
	public static final String Update = "com.jelly.eoss.model.UserMenu.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.UserMenu.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.UserMenu.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.UserMenu.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.UserMenu.Select";
	public static final String SelectCount = "com.jelly.eoss.model.UserMenu.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.UserMenu.SelectByPk";
	
	private Integer menuId;
	
	private Integer userId;


	public UserMenu setMenuId (Integer menuId) {
		this.menuId = menuId;
		return this;
	}
	
	public Integer getMenuId () {
		return this.menuId;
	}

	public UserMenu setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	
	public Integer getUserId() {
		return this.userId;
	}

}