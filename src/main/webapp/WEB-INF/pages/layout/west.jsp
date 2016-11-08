<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
  	<script src="${BASE_PATH}/static/js/layout/west.js" type="text/javascript"></script>
  	<style type="text/css">
  		#zTreeJson, #zTreeUL{
  			display:none;
  		}
  	</style>
  </head>
  
  <body>
  	<table class="tableCenter noborder">
  		<tr>
  			<td id="loadingTd"><img src="${BASE_PATH}/base/jquery/zTree/css/img/loading.gif"/><br/>用户权限加载中</td>
  		</tr>
  	</table>
  	<ul id="zTreeUL" class="ztree"></ul>
  </body>
</html>
