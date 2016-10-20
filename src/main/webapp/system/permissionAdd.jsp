<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/system/include/head.jsp"%>
  		<script src="${BASE_PATH}/js/system/permissionAdd.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/permission/addPermission.ac" class="eossForm" method="post">
			<input type="hidden" id="menuLev" name="lev"/>
			<input type="hidden" id="menuPath" name="path"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">权限添加</td>
				</tr>
				<tr>
					<td align="right" width="100">权限名称：</td>
					<td>
						<input type="text" id="menuName" name="name"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">权限目标：</td>
					<td>
						<input type="text" id="menuTarget" name="target" value="centerFrame"/>&nbsp;&nbsp;例如：_blank, _self, northFrame, southFrame, westFrame, centerFrame
					</td>
				</tr>
				<tr>
					<td align="right" width="100">权限地址：</td>
					<td>
						<input type="text" class="inputMid" id="menuUrl" name="url"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">所属菜单：</td>
					<td>
						<input type="hidden" id="menuPid" name="pid"/>
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
