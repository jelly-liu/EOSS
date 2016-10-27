<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<title>${PROJECT_NAME}</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<script src="${BASE_PATH}/static/js/layout/main.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
</head>
<body class="easyui-layout">
	<input type="hidden" id="userName" value="${EossLoginUser.username}" />

	<!-- north -->
	<div data-options="region:'north',split:true,href:'${BASE_PATH}/system/layout/northAjax.ac'" style="height:50px;">
		<!-- <iframe id="northFrame" name="northFrame" src="${BASE_PATH}/system/layout/north.jsp" frameborder=0 width="100%" height="100%"></iframe> -->
	</div>

	<!-- west -->
	<!-- 
	<div data-options="region:'west',split:true,title:'用户权限',href:'${BASE_PATH}/system/layout/westAjax.jsp'" style="width:200px;">
		<iframe id="westFrame" name="westFrame" src="${BASE_PATH}/system/layout/west.jsp" frameborder=0 width="100%" height="100%"></iframe>
	</div>
	-->

	<!-- center -->
	<div data-options="region:'center'">
		<div id="centerWindowDiv">
			<iframe id="centerFrame" name="centerFrame" src="${BASE_PATH}/system/layout/center.ac" frameborder="0" width="100%" height="100%" marginwidth="0" marginheight="0"></iframe>
		</div>
	</div>

	<!-- south -->
	<div data-options="region:'south',split:true,href:'${BASE_PATH}/system/layout/southAjax.ac'" style="height:60px;">
	</div>
</body>
</html>
