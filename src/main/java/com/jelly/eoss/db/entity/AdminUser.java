
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AdminUser extends ConditionDomain {
    public static final String TABLE_NAME = "admin_user";

	
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	private Integer locked;
	
	private Integer disabled;
	
	private String createDatetime;


	public AdminUser setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminUser setUsername (String username) {
		this.username = username;
		return this;
	}
	
	public String getUsername () {
		return this.username;
	}

	public AdminUser setPassword (String password) {
		this.password = password;
		return this;
	}
	
	public String getPassword () {
		return this.password;
	}

	public AdminUser setSalt (String salt) {
		this.salt = salt;
		return this;
	}
	
	public String getSalt () {
		return this.salt;
	}

	public AdminUser setLocked (Integer locked) {
		this.locked = locked;
		return this;
	}
	
	public Integer getLocked () {
		return this.locked;
	}

	public AdminUser setDisabled (Integer disabled) {
		this.disabled = disabled;
		return this;
	}
	
	public Integer getDisabled () {
		return this.disabled;
	}

	public AdminUser setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}