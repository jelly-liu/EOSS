<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/roleUpdate.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/role/update.ac" class="eossForm" method="post">
			<input type="hidden" name="id" value="${role.id}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">角色更新</td>
				</tr>
				<tr>
					<td align="right" width="100">角色名称：</td>
					<td>
						<input type="text" id="roleName" name="name" value="${role.name}"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="100">选择权限：</td>
					<td>
						<input type="hidden" id="permissionIdsHide" name="permissionIds"/>
						<c:forEach var="it" items="${permissionList}" varStatus="st">
							<c:choose>
								<c:when test="${it.roleId == null}">
									<input type="checkbox" class="permission" name="permission" value="${it.id}"/>${it.name}
								</c:when>
								<c:otherwise>
									<input type="checkbox" class="permission" name="permission" value="${it.id}" checked="checked"/>${it.name}
								</c:otherwise>
							</c:choose>
						</c:forEach>
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
