<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/userAdd.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/user/add.ac" class="eossForm" method="post">
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">用户添加</td>
				</tr>
				<tr>
					<td align="right" width="100">用户名称：</td>
					<td>
						<input type="text" id="userName" name="username"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">用户密码：</td>
					<td>
						<input type="text" id="userPwd" name="password" value="111111"/>
					</td>
				</tr>
				<tr>
					<td align="right">选择角色：</td>
					<td>
						<input type="hidden" id="roleIds" name="roleIds"/>
						<ul id="zTreeUL" class="ztree"></ul>
					</td>
				</tr>

				<tr>
					<td align="right">选择资源：</td>
					<td>
						<input type="hidden" id="resourcesIds" name="resourcesIds"/>
						<ul id="zTreeULResource" class="ztree"></ul>
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
