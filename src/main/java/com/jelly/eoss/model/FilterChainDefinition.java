
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class FilterChainDefinition {
	public static final String Insert = "com.jelly.eoss.model.FilterChainDefinition.Insert";
	public static final String Update = "com.jelly.eoss.model.FilterChainDefinition.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.FilterChainDefinition.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.FilterChainDefinition.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.FilterChainDefinition.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.FilterChainDefinition.Select";
	public static final String SelectCount = "com.jelly.eoss.model.FilterChainDefinition.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.FilterChainDefinition.SelectByPk";
	
	private Integer id;
	
	private String definition;


	public FilterChainDefinition setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public FilterChainDefinition setDefinition(String definition) {
		this.definition = definition;
		return this;
	}
	
	public String getDefinition() {
		return this.definition;
	}

}