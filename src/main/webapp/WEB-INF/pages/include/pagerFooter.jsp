<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="eossDataTable tableXCenter">
	<tbody>
		<tr>
			<td align="right">
				<a href="###" onclick="goTo(1);">首页</a>&nbsp;&nbsp;
				<a href="###" onclick="goTo(${pager.page - 1});">上一页</a>&nbsp;&nbsp;
				[${pager.page}/${pager.totalPage}]页&nbsp;&nbsp;
				[${pager.rowStart}-${pager.rowEnd}]/${pager.totalRow}条&nbsp;&nbsp;
				<a href="###" onclick="goTo(${pager.page + 1});">下一页</a>&nbsp;&nbsp;
				<a href="###" onclick="goTo(${pager.totalPage});">尾页</a>&nbsp;&nbsp;
				<a href="###" id="jumpPageHref">跳转</a><input type="text" id="jumpPage" style="width:30px;"/>&nbsp;&nbsp;
			</td>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	var formId = '<%=request.getParameter("formId")%>';
	var pageId = '<%=request.getParameter("pageId")%>';
	var totalPage = parseInt('<%=request.getParameter("totalPage")%>');
	
	var $form = $('#' + formId);
	var $page = $('#' + pageId);
	
	function goTo(page){
		var reg = /^(\d+)$/;
		if(!reg.test(page)){
			top.$.messager.alert('提示','只能输入数字!');
			return;
		}
		
		if(page < 1){
			page = 1;
		}
		
		if(page > totalPage){
			page = totalPage;
		}
		
		$.messager.wait('正在操作，请稍候...');
		
		$page.val(page);
		$form.submit();
	}
	
	$(function(){
		$('#jumpPageHref').click(function(){
			goTo($('#jumpPage').val());
		});
	});
</script>