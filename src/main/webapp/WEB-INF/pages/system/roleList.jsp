<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/roleList.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/role/toList.ac" class="eossForm" method="post">
			<input type="hidden" id="page" name="page" value="${pager.page}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">角色查询</td>
				</tr>
				<tr>
					<td align="left">
						角色ID：<input type="text" id="menuId" name="id" value="${id}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						角色名称：<input type="text" id="menuName" name="name" value="${name}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						开始时间：<input type="text" class="easyui-datebox" name="createTimeStart" value="${createTimeStart}"/>&nbsp;&nbsp;&nbsp;&nbsp;
						结束时间：<input type="text" class="easyui-datebox" name="createTimeEnd" value="${createTimeEnd}"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
						<th>ID</th>
						<th>角色名称</th>
						<th>创建时间</th>
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
						<td>${it.name}</td>
						<td>${it.createDatetime}</td>
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
