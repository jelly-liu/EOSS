
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;
import com.jelly.eoss.model.ConditionDomain;

public class AdminFilterchainDefinition extends ConditionDomain {
	public static final String Insert = "AdminFilterchainDefinition.Insert";
	public static final String Update = "AdminFilterchainDefinition.Update";
	public static final String UpdateWithNull = "AdminFilterchainDefinition.UpdateWithNull";
	public static final String DeleteByPk = "AdminFilterchainDefinition.DeleteByPk";
	public static final String DeleteByPojo = "AdminFilterchainDefinition.DeleteByPojo";
	public static final String Select = "AdminFilterchainDefinition.Select";
	public static final String SelectCount = "AdminFilterchainDefinition.SelectCount";
	public static final String SelectByPk = "AdminFilterchainDefinition.SelectByPk";
	
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