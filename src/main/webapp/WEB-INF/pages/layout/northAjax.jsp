<%@ page language="java" pageEncoding="UTF-8"%>
<!-- head -->
<script src="${BASE_PATH}/static/js/layout/north.js" type="text/javascript"></script>
<style type="text/css">
	#EOSS{
		position:absolute;
		top:1px;
		left:1px;
		font-size:30px;
		font-weight:bold;
	}
</style>

<!-- body -->
<div id="EOSS">
	${PROJECT_NAME}${PROJECT_VERSION}
</div>
<table class="tableRight noborder">
	<tr>
		<td id="menuLevelOneTd">
			<!-- <a id="toggleView" href="###" class="easyui-linkbutton" toggle="true">视图切换</a>  -->
			<a id="loginOutBtn" href="###" class="easyui-linkbutton">注销系统</a>
		</td>
	</tr>
</table>
