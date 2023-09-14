
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysSample extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_sample";
	// 静态，类名称
	public static final String POJO_NAME = "SysSample";


	// 静态，属性名称
	public static final String Property_id = "id";
	public static final String Property_name = "name";
	public static final String Property_weight = "weight";
	public static final String Property_info = "info";
	public static final String Property_version = "version";
	public static final String Property_createTime = "createTime";


	// 静态，列名称
	public static final String Field_id = "id";
	public static final String Field_name = "name";
	public static final String Field_weight = "weight";
	public static final String Field_info = "info";
	public static final String Field_version = "version";
	public static final String Field_create_time = "create_time";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_id,Property_name,Property_weight,Property_info,Property_version,Property_createTime));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_id,Field_name,Field_weight,Field_info,Field_version,Field_create_time));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList(Property_info));
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList(Field_info));


	// 实例，属性名称
	private Integer id;
	private String name;
	private Integer weight;
	private String info;
	private Integer version;
	private Date createTime;


	// 实例，Getter 和 Setter 方法
	public Integer getId () {
		return this.id;
	}
	public SysSample setId (Integer id) {
		this.id = id;
		return this;
	}
	public String getName () {
		return this.name;
	}
	public SysSample setName (String name) {
		this.name = name;
		return this;
	}
	public Integer getWeight () {
		return this.weight;
	}
	public SysSample setWeight (Integer weight) {
		this.weight = weight;
		return this;
	}
	public String getInfo () {
		return this.info;
	}
	public SysSample setInfo (String info) {
		this.info = info;
		return this;
	}
	public Integer getVersion () {
		return this.version;
	}
	public SysSample setVersion (Integer version) {
		this.version = version;
		return this;
	}
	public Date getCreateTime () {
		return this.createTime;
	}
	public SysSample setCreateTime (Date createTime) {
		this.createTime = createTime;
		return this;
	}

}