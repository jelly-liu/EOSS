<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<title>${PROJECT_NAME}</title>
		<%@ include file="WEB-INF/pages/include/head.jsp"%>
		<script src="${BASE_PATH}/static/js/login.js" type="text/javascript"></script>
		<style type="text/css">
			.eossFromTable td{
				border:0px;
			}
		</style>
	</head>

	<body>
		<div id="loginFormDiv">
		    <form id="loginForm" action="${BASE_PATH}/login/loginIn.ac" method="post">
		    	<table id="loginTable" class="eossFromTable">
					<tr>
						<td align="right" width="60">用户名：</td>
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
							<input type="text" id="vCode" name="vCode"/>
						</td>
					</tr>
					<tr>
						<td align="right"></td>
						<td>
							<img id="icodeImg" class="icodeImg" src="${BASE_PATH}/static/vCode.jpg" title="点击图片刷新验证码"/>
						</td>
					</tr>
				</table>
		    </form>
		</div>
	</body>
</html>
