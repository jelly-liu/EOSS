
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;

public class Rule {
	public static final String Insert = "com.jelly.eoss.model.Rule.Insert";
	public static final String Update = "com.jelly.eoss.model.Rule.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.Rule.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.Rule.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.Rule.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.Rule.Select";
	public static final String SelectCount = "com.jelly.eoss.model.Rule.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.Rule.SelectByPk";
	
	private Integer id;
	
	private String path;
	
	private String rule;


	public Rule setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public Rule setPath (String path) {
		this.path = path;
		return this;
	}
	
	public String getPath () {
		return this.path;
	}

	public Rule setRule (String rule) {
		this.rule = rule;
		return this;
	}
	
	public String getRule () {
		return this.rule;
	}

}