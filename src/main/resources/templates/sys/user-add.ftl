<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<#include "/templates/include/head.ftl"/>
</head>

<body>
<form id="dataForm" class="easyui-form" data-options="">
	<input type="hidden"/>
	<table class="eossFromTable tableXCenter">
		<tr>
			<td style="text-align: right;">名称:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="username" data-options="required:true, validType:['minLength[3]']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">密码:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="password" name="password" data-options="required:true, validType:['minLength[6]']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">确认密码:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="password" name="password-confirm" data-options="required:true, validType:['minLength[6]', 'sameAs[&quot;password&quot;]']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">禁用:</td>
			<td style="text-align: left;">
				<select class="easyui-combobox" name="disabled" style="width:100px;" data-options="required:true, panelHeight:'auto'">
					<option value="0" >否</option>
					<option value="1" >是</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">角色:</td>
			<td style="text-align: left;">
				<table id="roleDataGrid" style="width: 100%; height: auto;"></table>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">权限:</td>
			<td style="text-align: left;">
				<table id="permDataGrid" style="width: 100%; height: auto;"></table>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;"></td>
			<td style="text-align: left;">
				<a id="submitBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
				<a id="cancelBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</td>
		</tr>
	</table>
</form>

<script src="/static/js/sys/user-add.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
