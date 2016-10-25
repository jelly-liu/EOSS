<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/permissionUpdate.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/permission/update.ac" class="eossForm" method="post">
			<input type="hidden" id="menuId" name="id" value="${permission.id}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">权限更新</td>
				</tr>
				<tr>
					<td align="right" width="100">权限名称：</td>
					<td>
						<input type="text" id="permissionName" name="name" value="${permission.name}"/>
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
