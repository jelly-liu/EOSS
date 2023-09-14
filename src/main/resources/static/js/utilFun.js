//当页面加载完成后的处理
var eoss_wait_obj = [];
$(function() {
	//弹出一个等待框，不可关闭
	$.messager.wait = function(msg){
		var $div = $('<div/>');
		$div.dialog({
			title : '提示',
			content : msg,
			width : 300,
			height : 100,
			closed : false,
			modal : true,
			closable : false
		});
		eoss_wait_obj.push($div);
	}
	$.messager.waitClose = function(){
		for(var i = 0; i < eoss_wait_obj.length; i++){
			eoss_wait_obj[i].dialog('close');
			eoss_wait_obj[i].splice(i, 1);
		}
	}

    //数据表格行特效事件
	$eossDataTableTrs = $('table.eossDataTable').find('tbody tr');
	$eossDataTableTrs.hover(
		function(){
			$(this).toggleClass('hover');
		},
		function(){
			$(this).toggleClass('hover');
		}
	).click(function(){
		$eossDataTableTrs.removeClass('clicked');
		$(this).addClass('clicked');
	});
});