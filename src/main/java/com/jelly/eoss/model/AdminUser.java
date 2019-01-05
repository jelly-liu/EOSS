
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;
import com.jelly.eoss.model.ConditionDomain;

public class AdminUser extends ConditionDomain {
	public static final String Insert = "AdminUser.Insert";
	public static final String Update = "AdminUser.Update";
	public static final String UpdateWithNull = "AdminUser.UpdateWithNull";
	public static final String DeleteByPk = "AdminUser.DeleteByPk";
	public static final String DeleteByPojo = "AdminUser.DeleteByPojo";
	public static final String Select = "AdminUser.Select";
	public static final String SelectCount = "AdminUser.SelectCount";
	public static final String SelectByPk = "AdminUser.SelectByPk";
	
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