
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminPermission extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminPermission.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminPermission.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminPermission.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminPermission.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminPermission.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminPermission.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminPermission.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminPermission.SelectByPk";
	
	private Integer id;
	
	private String name;


	public AdminPermission setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminPermission setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

}