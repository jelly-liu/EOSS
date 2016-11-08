<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  		<script src="${BASE_PATH}/static/js/system/resourceList.js" type="text/javascript"></script>
	</head>

	<body>
		<form id="submitForm" action="${BASE_PATH}/system/resource/toList.ac" class="eossForm" method="post">
			<input type="hidden" id="page" name="page" value="${pager.page}"/>
			<table class="eossFromTable">
				<tr class="panel-header">
					<td colspan="99">资源查询</td>
				</tr>
				<tr>
					<td align="right">资源ID：</td>
					<td><input type="text" name="id" value="${id}"/></td>
					<td align="right">所属菜单：</td>
					<td>
						<input type="text" id="pnameText" name="pnameText" value="${pnameText}" style="width:200px"/>
						<input type="hidden" id="dirId" name="dirId" value="${dirId}"/>
						<div id="zTreeULWin">
					        <ul id="zTreeUL" class="ztree"></ul>
					    </div>
					</td>
				</tr>
				<tr>
					<td align="right">开始时间：</td>
					<td><input type="text" class="bindDatePicker" name="createTimeStart" value="${createTimeStart}"/></td>
					<td align="right">结束时间：</td>
					<td><input type="text" class="bindDatePicker" name="createTimeEnd" value="${createTimeEnd}"/></td>
				</tr>
				<tr>
					<td colspan="99" align="center">
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
						<th>资源名称</th>
						<th>父目录名称</th>
						<th>资源URL</th>
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
						<td>${it.pname}</td>
						<td>${it.url}</td>
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
