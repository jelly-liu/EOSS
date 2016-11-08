<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/permissionList.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/permission/toList.ac" class="eossForm" method="post">
			<input type="hidden" id="page" name="page" value="${pager.page}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">权限查询</td>
				</tr>
				<tr>
					<td align="left">
						ID：<input type="text" name="id" value="${id}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						权限名称：<input type="text" name="name" value="${name}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						<a id="submitBtn" href="###" class="easyui-linkbutton">查询</a>
					</td>
				</tr>
			</table>
		</form>
		
		<!-- display data list -->
		<c:if test="${pager.data != null && fn:length(pager.data) > 0}">
			<table class="eossDataTable tableXCenter">
				<thead>
					<tr class="datagrid-header">
						<th>序号</th>
						<th>ID</th>
						<th>权限名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="it" items="${pager.data}" varStatus="st">
						<c:if test="${st.index % 2 == 0}">
							<tr class="even">
						</c:if>
						<c:if test="${st.index % 2 != 0}">
							<tr class="even">
						</c:if>
						<td>${pager.rowStart + st.index}</td>
						<td>${it.id}</td>
						<td>${it.name}</td>
						<td>
							<a href="###" type="delete" value="${it.id}">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="###" type="update" value="${it.id}">更新</a>
						</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- pagination -->
			<jsp:include page="/WEB-INF/pages/include/pagerFooter.jsp" flush="true">
				<jsp:param name="formId" value="submitForm"/>
				<jsp:param name="pageId" value="page"/>
				<jsp:param name="totalPage" value="${pager.totalPage}"/>
			</jsp:include>
		</c:if>
	</body>
</html>
