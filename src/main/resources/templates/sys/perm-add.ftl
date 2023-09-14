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
			<td style="text-align: right; width: 30%;">PID:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="pid" data-options="required:true, validType:['integer']"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">名称:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="name" data-options="required:true"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">类型:</td>
			<td style="text-align: left;">
				<select class="easyui-combobox" name="type" style="width:100px;" data-options="required:true, panelHeight:'auto'">
					<option value="menu" >菜单</option>
					<option value="func" >功能</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">页面地址:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="url" data-options="width:'250px'"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">提交地址:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" type="text" name="urlSubmit" data-options="width:'250px'"></input>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">叶子:</td>
			<td style="text-align: left;">
				<input class="easyui-radiobutton" name="leaf" value="1" label="是:" data-options="labelWidth:30" />
				<input class="easyui-radiobutton" name="leaf" value="0" label="否:" data-options="labelWidth:30" />&nbsp;&nbsp;
				<input class="easyui-textbox" value=" " data-options="required:true, validType:['radioBox[&quot;leaf&quot;]'], width:1, height:1" />
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">排序:</td>
			<td style="text-align: left;">
				<input class="easyui-textbox" name="sortNum" data-options="required:true, validType:['integer']"></input>
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

<script src="/static/js/sys/perm-add.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
