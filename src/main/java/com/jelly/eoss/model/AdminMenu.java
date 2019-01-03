
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class AdminMenu extends ConditionDomain {
	public static final String Insert = "com.jelly.eoss.model.AdminMenu.Insert";
	public static final String Update = "com.jelly.eoss.model.AdminMenu.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.AdminMenu.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.AdminMenu.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.AdminMenu.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.AdminMenu.Select";
	public static final String SelectCount = "com.jelly.eoss.model.AdminMenu.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.AdminMenu.SelectByPk";
	
	private Integer id;
	
	private Integer pid;
	
	private String name;
	
	private Integer leaf;
	
	private String path;
	
	private Integer lev;
	
	private String url;
	
	private String target;
	
	private String createDatetime;


	public AdminMenu setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public AdminMenu setPid (Integer pid) {
		this.pid = pid;
		return this;
	}
	
	public Integer getPid () {
		return this.pid;
	}

	public AdminMenu setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

	public AdminMenu setLeaf (Integer leaf) {
		this.leaf = leaf;
		return this;
	}
	
	public Integer getLeaf () {
		return this.leaf;
	}

	public AdminMenu setPath (String path) {
		this.path = path;
		return this;
	}
	
	public String getPath () {
		return this.path;
	}

	public AdminMenu setLev (Integer lev) {
		this.lev = lev;
		return this;
	}
	
	public Integer getLev () {
		return this.lev;
	}

	public AdminMenu setUrl (String url) {
		this.url = url;
		return this;
	}
	
	public String getUrl () {
		return this.url;
	}

	public AdminMenu setTarget (String target) {
		this.target = target;
		return this;
	}
	
	public String getTarget () {
		return this.target;
	}

	public AdminMenu setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}