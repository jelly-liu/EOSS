
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysPerm extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_perm";
	// 静态，类名称
	public static final String POJO_NAME = "SysPerm";


	// 静态，属性名称
	public static final String Property_id = "id";
	public static final String Property_pid = "pid";
	public static final String Property_name = "name";
	public static final String Property_type = "type";
	public static final String Property_url = "url";
	public static final String Property_urlSubmit = "urlSubmit";
	public static final String Property_path = "path";
	public static final String Property_leaf = "leaf";
	public static final String Property_levelNum = "levelNum";
	public static final String Property_sortNum = "sortNum";
	public static final String Property_createTime = "createTime";
	public static final String Property_updateTime = "updateTime";


	// 静态，列名称
	public static final String Field_id = "id";
	public static final String Field_pid = "pid";
	public static final String Field_name = "name";
	public static final String Field_type = "type";
	public static final String Field_url = "url";
	public static final String Field_url_submit = "url_submit";
	public static final String Field_path = "path";
	public static final String Field_leaf = "leaf";
	public static final String Field_level_num = "level_num";
	public static final String Field_sort_num = "sort_num";
	public static final String Field_create_time = "create_time";
	public static final String Field_update_time = "update_time";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_id,Property_pid,Property_name,Property_type,Property_url,Property_urlSubmit,Property_path,Property_leaf,Property_levelNum,Property_sortNum,Property_createTime,Property_updateTime));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_id,Field_pid,Field_name,Field_type,Field_url,Field_url_submit,Field_path,Field_leaf,Field_level_num,Field_sort_num,Field_create_time,Field_update_time));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList());
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList());


	// 实例，属性名称
	private Integer id;
	private Integer pid;
	private String name;
	private String type;
	private String url;
	private String urlSubmit;
	private String path;
	private Integer leaf;
	private Integer levelNum;
	private Integer sortNum;
	private Date createTime;
	private Date updateTime;


	// 实例，Getter 和 Setter 方法
	public Integer getId () {
		return this.id;
	}
	public SysPerm setId (Integer id) {
		this.id = id;
		return this;
	}
	public Integer getPid () {
		return this.pid;
	}
	public SysPerm setPid (Integer pid) {
		this.pid = pid;
		return this;
	}
	public String getName () {
		return this.name;
	}
	public SysPerm setName (String name) {
		this.name = name;
		return this;
	}
	public String getType () {
		return this.type;
	}
	public SysPerm setType (String type) {
		this.type = type;
		return this;
	}
	public String getUrl () {
		return this.url;
	}
	public SysPerm setUrl (String url) {
		this.url = url;
		return this;
	}
	public String getUrlSubmit () {
		return this.urlSubmit;
	}
	public SysPerm setUrlSubmit (String urlSubmit) {
		this.urlSubmit = urlSubmit;
		return this;
	}
	public String getPath () {
		return this.path;
	}
	public SysPerm setPath (String path) {
		this.path = path;
		return this;
	}
	public Integer getLeaf () {
		return this.leaf;
	}
	public SysPerm setLeaf (Integer leaf) {
		this.leaf = leaf;
		return this;
	}
	public Integer getLevelNum () {
		return this.levelNum;
	}
	public SysPerm setLevelNum (Integer levelNum) {
		this.levelNum = levelNum;
		return this;
	}
	public Integer getSortNum () {
		return this.sortNum;
	}
	public SysPerm setSortNum (Integer sortNum) {
		this.sortNum = sortNum;
		return this;
	}
	public Date getCreateTime () {
		return this.createTime;
	}
	public SysPerm setCreateTime (Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getUpdateTime () {
		return this.updateTime;
	}
	public SysPerm setUpdateTime (Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

}