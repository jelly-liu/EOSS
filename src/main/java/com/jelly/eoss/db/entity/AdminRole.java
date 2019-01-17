
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminRole extends ConditionDomain {
    public static final String TABLE_NAME = "admin_role";

	
	private Integer id;
	
	private String name;
	
	private String createDatetime;


	public AdminRole setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminRole setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

	public AdminRole setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}