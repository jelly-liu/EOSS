
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;

public class User {
	public static final String Insert = "com.jelly.eoss.model.User.Insert";
	public static final String Update = "com.jelly.eoss.model.User.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.User.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.User.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.User.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.User.Select";
	public static final String SelectCount = "com.jelly.eoss.model.User.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.User.SelectByPk";
	
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	private Integer locked;
	
	private Integer disabled;
	
	private String createDatetime;


	public User setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public User setUsername (String username) {
		this.username = username;
		return this;
	}
	
	public String getUsername () {
		return this.username;
	}

	public User setPassword (String password) {
		this.password = password;
		return this;
	}
	
	public String getPassword () {
		return this.password;
	}

	public User setSalt (String salt) {
		this.salt = salt;
		return this;
	}
	
	public String getSalt () {
		return this.salt;
	}

	public User setLocked (Integer locked) {
		this.locked = locked;
		return this;
	}
	
	public Integer getLocked () {
		return this.locked;
	}

	public User setDisabled (Integer disabled) {
		this.disabled = disabled;
		return this;
	}
	
	public Integer getDisabled () {
		return this.disabled;
	}

	public User setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}