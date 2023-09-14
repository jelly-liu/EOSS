
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysUser extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_user";
	// 静态，类名称
	public static final String POJO_NAME = "SysUser";


	// 静态，属性名称
	public static final String Property_id = "id";
	public static final String Property_username = "username";
	public static final String Property_password = "password";
	public static final String Property_salt = "salt";
	public static final String Property_disabled = "disabled";
	public static final String Property_createTime = "createTime";
	public static final String Property_updateTime = "updateTime";


	// 静态，列名称
	public static final String Field_id = "id";
	public static final String Field_username = "username";
	public static final String Field_password = "password";
	public static final String Field_salt = "salt";
	public static final String Field_disabled = "disabled";
	public static final String Field_create_time = "create_time";
	public static final String Field_update_time = "update_time";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_id,Property_username,Property_password,Property_salt,Property_disabled,Property_createTime,Property_updateTime));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_id,Field_username,Field_password,Field_salt,Field_disabled,Field_create_time,Field_update_time));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList());
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList());


	// 实例，属性名称
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private Integer disabled;
	private Date createTime;
	private Date updateTime;


	// 实例，Getter 和 Setter 方法
	public Integer getId () {
		return this.id;
	}
	public SysUser setId (Integer id) {
		this.id = id;
		return this;
	}
	public String getUsername () {
		return this.username;
	}
	public SysUser setUsername (String username) {
		this.username = username;
		return this;
	}
	public String getPassword () {
		return this.password;
	}
	public SysUser setPassword (String password) {
		this.password = password;
		return this;
	}
	public String getSalt () {
		return this.salt;
	}
	public SysUser setSalt (String salt) {
		this.salt = salt;
		return this;
	}
	public Integer getDisabled () {
		return this.disabled;
	}
	public SysUser setDisabled (Integer disabled) {
		this.disabled = disabled;
		return this;
	}
	public Date getCreateTime () {
		return this.createTime;
	}
	public SysUser setCreateTime (Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getUpdateTime () {
		return this.updateTime;
	}
	public SysUser setUpdateTime (Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

}