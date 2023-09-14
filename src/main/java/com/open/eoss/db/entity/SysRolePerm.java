
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysRolePerm extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_role_perm";
	// 静态，类名称
	public static final String POJO_NAME = "SysRolePerm";


	// 静态，属性名称
	public static final String Property_roleId = "roleId";
	public static final String Property_permId = "permId";


	// 静态，列名称
	public static final String Field_role_id = "role_id";
	public static final String Field_perm_id = "perm_id";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_roleId,Property_permId));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_role_id,Field_perm_id));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList());
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList());


	// 实例，属性名称
	private Integer roleId;
	private Integer permId;


	// 实例，Getter 和 Setter 方法
	public Integer getRoleId () {
		return this.roleId;
	}
	public SysRolePerm setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	public Integer getPermId () {
		return this.permId;
	}
	public SysRolePerm setPermId (Integer permId) {
		this.permId = permId;
		return this;
	}

}