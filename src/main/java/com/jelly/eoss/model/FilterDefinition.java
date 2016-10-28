
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class FilterDefinition {
	public static final String Insert = "com.jelly.eoss.model.FilterDefinition.Insert";
	public static final String Update = "com.jelly.eoss.model.FilterDefinition.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.FilterDefinition.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.FilterDefinition.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.FilterDefinition.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.FilterDefinition.Select";
	public static final String SelectCount = "com.jelly.eoss.model.FilterDefinition.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.FilterDefinition.SelectByPk";
	
	private Integer id;
	
	private String rule;


	public FilterDefinition setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public FilterDefinition setRule (String rule) {
		this.rule = rule;
		return this;
	}
	
	public String getRule () {
		return this.rule;
	}

}