
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class Role {
	public static final String Insert = "com.jelly.eoss.model.Role.Insert";
	public static final String Update = "com.jelly.eoss.model.Role.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.Role.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.Role.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.Role.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.Role.Select";
	public static final String SelectCount = "com.jelly.eoss.model.Role.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.Role.SelectByPk";
	
	private Integer id;
	
	private String name;
	
	private String createDatetime;


	public Role setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public Role setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

	public Role setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}