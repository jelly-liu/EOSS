
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminFilterchainDefinition extends ConditionDomain {

	
	private Integer id;
	
	private String definition;


	public AdminFilterchainDefinition setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminFilterchainDefinition setDefinition (String definition) {
		this.definition = definition;
		return this;
	}
	
	public String getDefinition () {
		return this.definition;
	}

}