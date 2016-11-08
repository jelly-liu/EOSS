var curThis = this;
$(function () {
	//注销事件
	$('#loginOutBtn').click(function(){
		top.$.messager.confirm('提示', '您真的要注销吗？', function(r){
            if(r){
            	$.ajax({
        			type:'POST',
        			dataType: 'text',
        			url: EossGlobal.basePath + '/logout.ac',
        			success: function(rs){
        				if(rs == 'y'){
        					top.window.location.href = EossGlobal.basePath + '/toLogin.ac';
        				}
        			}
        		});
            }
        });
	});
	
	/*
	//视图切换事件
	var maxView = false;
	//'north','south','east','west','center'
	$('#toggleView').click(function(){
		if(!maxView){
			var $t = top.$('body');
			$t.layout('collapse','south');
			$t.layout('collapse','west');
			$t.layout('resize');
		}else{
			var $t = top.$('body');
			$t.layout('expand', 'south');
			$t.layout('expand', 'west');
			$t.layout('resize');
		}
		maxView = !maxView;
	});
	*/
});
