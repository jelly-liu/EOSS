
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;

public class UsersMenu {
	public static final String Insert = "com.jelly.eoss.model.UsersMenu.Insert";
	public static final String Update = "com.jelly.eoss.model.UsersMenu.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.UsersMenu.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.UsersMenu.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.UsersMenu.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.UsersMenu.Select";
	public static final String SelectCount = "com.jelly.eoss.model.UsersMenu.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.UsersMenu.SelectByPk";
	
	private Integer menuId;
	
	private Integer usersId;


	public UsersMenu setMenuId (Integer menuId) {
		this.menuId = menuId;
		return this;
	}
	
	public Integer getMenuId () {
		return this.menuId;
	}

	public UsersMenu setUsersId (Integer usersId) {
		this.usersId = usersId;
		return this;
	}
	
	public Integer getUsersId () {
		return this.usersId;
	}

}