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
				<input class="easyui-textbox" type="text" name="username" data-options="required:true, validType:['minLength[3]']" value="${data.username}"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">密码:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="password" name="password" data-options=""></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">确认密码:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="password" name="password-confirm" data-options=""></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">禁用:</td>
			<td style="text-align: left;">
				<select class="easyui-combobox" name="disabled" style="width:100px;" data-options="required:true, panelHeight:'auto'">
					<option value="0" <#if data.disabled == 0>selected</#if> >否</option>
					<option value="1" <#if data.disabled == 1>selected</#if> >是</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">角色:</td>
			<td style="text-align: left;">
				<table id="roleDataGrid" style="width: 100%; height: auto;"></table>
				<input type="hidden" id="oldRoleIds" value="${oldRoleIds}"/>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">权限:</td>
			<td style="text-align: left;">
				<table id="permDataGrid" style="width: 100%; height: auto;"></table>
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

<script src="/static/js/sys/user-update.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
