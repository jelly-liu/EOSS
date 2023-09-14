<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<#include "/templates/include/head.ftl"/>
</head>

<body>
<form id="dataForm" class="easyui-form" data-options="" method="post">
	<input type="hidden" id="id" name="id" value="${data.id}"/>
	<table class="eossFromTable tableXCenter">
		<tr>
			<td style="text-align: right;">名称:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="name" data-options="required:true, validType:['minLength[2]']" value="${data.name}"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">权限:</td>
			<td style="padding: 3px;">
				<div id="permDataGridDiv"></div>
				<input type="hidden" id="oldPermIds" value="${oldPermIds}"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;"></td>
			<td style="text-align: left;">
				<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" _id="${data.id}">提交</a>
				<a id="cancelBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" _id="${data.id}">取消</a>
			</td>
		</tr>
	</table>
</form>

<script src="/static/js/sys/role-update.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
