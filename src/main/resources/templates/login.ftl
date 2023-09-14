<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
<head>
	<#include "/templates/include/head.ftl"/>
	<title>登录页面</title>
	<style type="text/css">
		body{
			background: #99cdff url('/static/images/cbg/cbg01.jpeg') repeat;
		}
		.eossFromTable td{
			border:0px;
		}
		#icodeClickImgDiv{
			position: relative;
			margin: 0px;
			padding: 0px;
		}
		.icodeClickMask{
			position: absolute;
			width: 25px;
			height: 25px;
			line-height: 25px;
			border: #00bbee 1px solid;
			text-align: center;
			vertical-align: middle;
			opacity: 0.7;
			font-weight: bold;
			font-size: 18px;
			border-radius: 22px;
		}
	</style>
</head>
<body>
<div id="loginFormDiv">
	<form id="loginForm">
		<table id="loginTable" class="eossFromTable">
			<tr>
				<td align="right" width="120">用户名：</td>
				<td>
					<input type="text" id="name" name="name" value="admin"/>
				</td>
			</tr>
			<tr>
				<td align="right">密码：</td>
				<td>
					<input type="text" id="pwd" name="pwd" value="111111"/>
				</td>
			</tr>
			<tr>
				<td align="right">验证码：</td>
				<td>
					<div id="icodeClickImgDiv">
						<img id="icodeClickImg" class="icodeClickImg" src="/static/vCodeClick.jpg" width="300" height="150"/>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
					<a id="submitBtn" href="###" class="easyui-linkbutton">登陆</a>&nbsp;&nbsp;
					<a id="icodeClickImgRefresh" href="###">刷新验证码</a>
				</td>
			</tr>
		</table>
	</form>
</div>

<script src="/static/js/login.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
