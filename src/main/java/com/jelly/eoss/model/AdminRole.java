
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminRole extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminRole.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminRole.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminRole.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminRole.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminRole.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminRole.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminRole.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminRole.SelectByPk";
	
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