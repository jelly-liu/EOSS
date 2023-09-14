<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<#include "/templates/include/head.ftl"/>
  		<script src="/static/js/sys/main-west.js?time=${timestamp}" type="text/javascript"></script>
		<style type="text/css">
			#zTreeJson, #zTreeUL{
				display:none;
			}
		</style>
  </head>
  
  <body>
  	<table class="tableCenter noborder">
  		<tr>
  			<td id="loadingTd"><img src="/static/jquery/zTree/css/zTreeStyle/img/loading.gif"/><br/>用户权限加载中</td>
  		</tr>
  	</table>
  	<ul id="zTreeUL" class="ztree"></ul>
  </body>
</html>
