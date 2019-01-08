
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminRole extends ConditionDomain {
	public static final String Insert = "AdminRole.Insert";
	public static final String Update = "AdminRole.Update";
	public static final String UpdateWithNull = "AdminRole.UpdateWithNull";
	public static final String DeleteByPk = "AdminRole.DeleteByPk";
	public static final String DeleteByPojo = "AdminRole.DeleteByPojo";
	public static final String Select = "AdminRole.Select";
	public static final String SelectCount = "AdminRole.SelectCount";
	public static final String SelectByPk = "AdminRole.SelectByPk";
	
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