
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

public class Menu {
	public static final String Insert = "com.jelly.eoss.model.Menu.Insert";
	public static final String Update = "com.jelly.eoss.model.Menu.Update";
	public static final String UpdateWithNull = "com.jelly.eoss.model.Menu.UpdateWithNull";
	public static final String DeleteByPk = "com.jelly.eoss.model.Menu.DeleteByPk";
	public static final String DeleteByPojo = "com.jelly.eoss.model.Menu.DeleteByPojo";
	public static final String Select = "com.jelly.eoss.model.Menu.Select";
	public static final String SelectCount = "com.jelly.eoss.model.Menu.SelectCount";
	public static final String SelectByPk = "com.jelly.eoss.model.Menu.SelectByPk";
	
	private Integer id;
	
	private Integer pid;
	
	private String name;
	
	private Integer leaf;
	
	private String path;
	
	private Integer lev;
	
	private String url;
	
	private String target;
	
	private String createDatetime;


	public Menu setId (Integer id) {
		this.id = id;
		return this;
	}
	
	public Integer getId () {
		return this.id;
	}

	public Menu setPid (Integer pid) {
		this.pid = pid;
		return this;
	}
	
	public Integer getPid () {
		return this.pid;
	}

	public Menu setName (String name) {
		this.name = name;
		return this;
	}
	
	public String getName () {
		return this.name;
	}

	public Menu setLeaf (Integer leaf) {
		this.leaf = leaf;
		return this;
	}
	
	public Integer getLeaf () {
		return this.leaf;
	}

	public Menu setPath (String path) {
		this.path = path;
		return this;
	}
	
	public String getPath () {
		return this.path;
	}

	public Menu setLev (Integer lev) {
		this.lev = lev;
		return this;
	}
	
	public Integer getLev () {
		return this.lev;
	}

	public Menu setUrl (String url) {
		this.url = url;
		return this;
	}
	
	public String getUrl () {
		return this.url;
	}

	public Menu setTarget (String target) {
		this.target = target;
		return this;
	}
	
	public String getTarget () {
		return this.target;
	}

	public Menu setCreateDatetime (String createDatetime) {
		this.createDatetime = createDatetime;
		return this;
	}
	
	public String getCreateDatetime () {
		return this.createDatetime;
	}

}