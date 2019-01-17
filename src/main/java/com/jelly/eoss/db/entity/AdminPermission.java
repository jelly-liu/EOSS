
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminPermission extends ConditionDomain {
    public static final String TABLE_NAME = "admin_permission";

	
	private Integer id;
	
	private String name;


	public AdminPermission setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminPermission setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

}