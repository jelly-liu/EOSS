
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;
import com.jelly.eoss.model.ConditionDomain;

public class AdminUserMenu extends ConditionDomain {
	public static final String Insert = "AdminUserMenu.Insert";
	public static final String Update = "AdminUserMenu.Update";
	public static final String UpdateWithNull = "AdminUserMenu.UpdateWithNull";
	public static final String DeleteByPk = "AdminUserMenu.DeleteByPk";
	public static final String DeleteByPojo = "AdminUserMenu.DeleteByPojo";
	public static final String Select = "AdminUserMenu.Select";
	public static final String SelectCount = "AdminUserMenu.SelectCount";
	public static final String SelectByPk = "AdminUserMenu.SelectByPk";
	
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