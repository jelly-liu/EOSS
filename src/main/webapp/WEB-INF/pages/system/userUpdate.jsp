<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/userUpdate.js" type="text/javascript"></script>
  		<style type="text/css">
  			#zTreeNodeJson{
  				display:none;
  			}
  		</style>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/user/update.ac" class="eossForm" method="post">
			<input type="hidden" name="id" value="${user.id}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">用户更新</td>
				</tr>
				<tr>
					<td align="right" width="100">用户名称：</td>
					<td>
						<input type="text" id="userName" name="username" value="${user.username}"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">用户密码：</td>
					<td>
						<input type="text" id="userPwd" name="password"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">密码盐值：</td>
					<td>
						<input type="text" id="pwdSalt" name="salt" value="${user.salt}" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">选择角色：</td>
					<td>
						<div id="zTreeNodeJson"  style="display:none;">${zTreeNodeJson}</div>
						<input type="hidden" id="roleIds" name="roleIds"/>
						<ul id="zTreeUL" class="ztree"></ul>
					</td>
				</tr>
				<tr>
					<td align="right">选择资源：</td>
					<td>
						<div id="zTreeNodeJsonResource"  style="display:none;">${zTreeNodeJsonResource}</div>
						<input type="hidden" id="resourceIds" name="resourceIds"/>
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
