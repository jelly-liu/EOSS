
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.db.entity;

public class AdminMenu extends ConditionDomain {

	
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