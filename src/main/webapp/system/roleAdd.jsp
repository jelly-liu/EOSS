<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/system/include/head.jsp"%>
  		<script src="${BASE_PATH}/js/system/roleAdd.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/role/addRole.ac" class="eossForm" method="post">
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">角色添加</td>
				</tr>
				<tr>
					<td align="right" width="100">角色名称：</td>
					<td>
						<input type="text" id="roleName" name="name"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">选择权限：</td>
					<td>
						<input type="hidden" id="permissionIds" name="permissionIds"/>
						<ul id="zTreeUL" class="ztree"></ul>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a id="submitBtn" href="###" class="easyui-linkbutton">提交</a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
