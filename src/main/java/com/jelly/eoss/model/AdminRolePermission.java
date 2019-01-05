
/**
* Author:Collonn, Email:collonn@126.com, QQ:195358385
*/
package com.jelly.eoss.model;

import java.math.BigDecimal;
import java.util.Date;
import com.jelly.eoss.model.ConditionDomain;

public class AdminRolePermission extends ConditionDomain {
	public static final String Insert = "AdminRolePermission.Insert";
	public static final String Update = "AdminRolePermission.Update";
	public static final String UpdateWithNull = "AdminRolePermission.UpdateWithNull";
	public static final String DeleteByPk = "AdminRolePermission.DeleteByPk";
	public static final String DeleteByPojo = "AdminRolePermission.DeleteByPojo";
	public static final String Select = "AdminRolePermission.Select";
	public static final String SelectCount = "AdminRolePermission.SelectCount";
	public static final String SelectByPk = "AdminRolePermission.SelectByPk";
	
	private Integer permissionId;
	
	private Integer roleId;


	public AdminRolePermission setPermissionId (Integer permissionId) {
		this.permissionId = permissionId;
		return this;
	}
	
	public Integer getPermissionId () {
		return this.permissionId;
	}

	public AdminRolePermission setRoleId (Integer roleId) {
		this.roleId = roleId;
		return this;
	}
	
	public Integer getRoleId () {
		return this.roleId;
	}

}