
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysRole extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_role";
	// 静态，类名称
	public static final String POJO_NAME = "SysRole";


	// 静态，属性名称
	public static final String Property_id = "id";
	public static final String Property_name = "name";
	public static final String Property_createTime = "createTime";
	public static final String Property_updateTime = "updateTime";


	// 静态，列名称
	public static final String Field_id = "id";
	public static final String Field_name = "name";
	public static final String Field_create_time = "create_time";
	public static final String Field_update_time = "update_time";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_id,Property_name,Property_createTime,Property_updateTime));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_id,Field_name,Field_create_time,Field_update_time));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList());
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList());


	// 实例，属性名称
	private Integer id;
	private String name;
	private Date createTime;
	private Date updateTime;


	// 实例，Getter 和 Setter 方法
	public Integer getId () {
		return this.id;
	}
	public SysRole setId (Integer id) {
		this.id = id;
		return this;
	}
	public String getName () {
		return this.name;
	}
	public SysRole setName (String name) {
		this.name = name;
		return this;
	}
	public Date getCreateTime () {
		return this.createTime;
	}
	public SysRole setCreateTime (Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getUpdateTime () {
		return this.updateTime;
	}
	public SysRole setUpdateTime (Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}

}