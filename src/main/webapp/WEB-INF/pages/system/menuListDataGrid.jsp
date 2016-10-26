<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/menuList.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/page/queryForPage.ac" class="eossForm">
			<input type="hidden" name="dataBaseType" value="mysql"/>
			<input type="hidden" name="statement" value="Menu_QueryMenuPage"/>
			<input type="hidden" name="forwardUrl" value="/system/menu/toList.ac"/>
			<input type="hidden" name="leaf" value="0"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td>菜单查询</td>
				</tr>
				<tr>
					<td align="left">
						菜单ID：<input type="text" id="menuId" name="id" value="${id}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						菜单名称：<input type="text" id="menuName" name="name" value="${name}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						开始时间：<input type="text" class="easyui-datebox" name="createTimeStart" value="${createTimeStart}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						结束时间：<input type="text" class="easyui-datebox" name="createTimeEnd" value="${createTimeEnd}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="submitBtn" href="###" class="easyui-linkbutton">查询</a>
					</td>
				</tr>
			</table>
		</form>
		
		<table id="dataGrid"></table>
	</body>
</html>
