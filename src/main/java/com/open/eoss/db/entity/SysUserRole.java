
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.open.eoss.db.entity;

import java.math.BigDecimal;
import java.util.*;
import com.open.eoss.db.entity.PojoBase;

public class SysUserRole extends PojoBase {
	// 静态，表名称
    public static final String TABLE_NAME = "sys_user_role";
	// 静态，类名称
	public static final String POJO_NAME = "SysUserRole";


	// 静态，属性名称
	public static final String Property_userId = "userId";
	public static final String Property_roleId = "roleId";


	// 静态，列名称
	public static final String Field_user_id = "user_id";
	public static final String Field_role_id = "role_id";


	// 静态，所有属性名称
	public static final Set<String> Property_Names_Set = new HashSet(Arrays.asList(Property_userId,Property_roleId));
	// 静态，所有列名称
	public static final Set<String> Field_Names_Set = new HashSet(Arrays.asList(Field_user_id,Field_role_id));
	// 静态，表的列类型是json类型的所有列名称
	public static final Set<String> Json_Property_Names_Set = new HashSet(Arrays.asList());
	// 静态，类的属性是json类型的所有属性名称
	public static final Set<String> Json_Field_Names_Set = new HashSet(Arrays.asList());


	// 实例，属性名称
	private Integer userId;
	private Integer roleId;


	// 实例，Getter 和 Setter 方法
	public Integer getUserId () {
		return this.userId;
	}
	public SysUserRole setUserId (Integer userId) {
		this.userId = userId;
		return this;
	}
	public Integer getRoleId () {
		return this.roleId;
	}
	public SysUserRole setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}

}