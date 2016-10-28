
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class Permission {
	public static final String Insert = "com.jelly.eoss.model.Permission.Insert";
	public static final String Update = "com.jelly.eoss.model.Permission.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.Permission.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.Permission.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.Permission.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.Permission.Select";
	public static final String SelectCount = "com.jelly.eoss.model.Permission.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.Permission.SelectByPk";
	
	private Integer id;
	
	private String name;


	public Permission setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public Permission setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

}