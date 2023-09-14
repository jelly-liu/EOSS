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
			<td style="text-align: right; width: 30%;">ID:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" readonly disabled name="id" value="${data.id}" data-options="required:true, validType:['integer']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">PID:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="pid" value="${data.pid}" data-options="required:true, validType:['integer']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">名称:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="name" value="${data.name}" data-options="required:true"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">类型:</td>
			<td style="text-align: left;">
				<select class="easyui-combobox" name="type" style="width:100px;" data-options="required:true, panelHeight:'auto'">
					<option value="menu" <#if data.type == "menu">selected</#if> >菜单</option>
					<option value="func" <#if data.type == "func">selected</#if> >功能</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">页面地址:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="url" value="${data.url}" data-options="width:'250px'"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">提交地址:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="urlSubmit" value="${data.urlSubmit}" data-options="width:'250px'"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">父链:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" readonly disabled name="path" data-options="required:true" value="${data.path}"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">叶子:</td>
			<td style="text-align: left;">
				<input class="easyui-radiobutton" name="leaf" value="1" label="是:" data-options="labelWidth:30" <#if data.leaf == 1>checked</#if> />
				<input class="easyui-radiobutton" name="leaf" value="0" label="否:" data-options="labelWidth:30" <#if data.leaf == 0>checked</#if> />
				<input class="easyui-textbox" value=" " data-options="required:true, validType:['radioBox[&quot;leaf&quot;]'], width:1, height:1" />
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">等级:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" name="levelNum" readonly disabled value="${data.levelNum}" data-options="required:true, validType:['integer']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">排序:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" name="sortNum" value="${data.sortNum}" data-options="required:true, validType:['integer']"></input>
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

<script src="/static/js/sys/perm-update.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
