
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;

public class Users {
	public static final String Insert = "com.jelly.eoss.model.Users.Insert";
	public static final String Update = "com.jelly.eoss.model.Users.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.Users.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.Users.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.Users.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.Users.Select";
	public static final String SelectCount = "com.jelly.eoss.model.Users.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.Users.SelectByPk";
	
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	private String createDatetime;


	public Users setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public Users setUsername (String username) {
		this.username = username;
		return this;
	}
	
	public String getUsername () {
		return this.username;
	}

	public Users setPassword (String password) {
		this.password = password;
		return this;
	}
	
	public String getPassword () {
		return this.password;
	}

	public Users setSalt (String salt) {
		this.salt = salt;
		return this;
	}
	
	public String getSalt () {
		return this.salt;
	}

	public Users setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}