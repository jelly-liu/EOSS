$(function() {
	//layout west
	$('body').layout('add', {
		region: 'west',
		title: '用户权限',
		split: true,
		width: 200,
		height: 'auto',
		collapsible: false,
		href: EossGlobal.basePath + '/system/layout/westAjax.ac',
		tools: [{
			iconCls:'icon-reload',
        	handler:function(){
        		var $wp = $('body').layout('panel', 'west');
        		$wp.panel('refresh', $wp.panel('options').href);
    		}
		}]
	});
	
	//初始化右边主视图的操作窗口
	$('#centerWindowDiv').window(initWinConfig());
	function initWinConfig(){
		var padding = 10;
		var $win = $('#centerWindow');

		var $np = $('body').layout('panel', 'north');
		var npW = $np.panel('options').width;
		var npH = $np.panel('options').height;

		var $wp = $('body').layout('panel', 'west');
		var wpW = $wp.panel('options').width;
		var wpH = $wp.panel('options').height;

		var $cp = $('body').layout('panel', 'center');
		var cpW = $cp.panel('options').width;
		var cpH = $cp.panel('options').height;

		var bdW = $(document.body).width();
		var bdH = $(document.body).height();
		
		return {
			title: '工作窗口',
			collapsible: false,
			minimizable: false,
			closable: false,
			modal: false,
			width: cpW - padding,
			height: cpH - padding,
			top: npH + padding/2,
			left: wpW + padding/2,
			onRestore: function(){
				$('#centerWindowDiv').window(initWinConfig());
			}
		};
	}
	
	$.messager.show({
		title : '登陆提示',
		msg : '用户:' + $('#userName').val() + '<br/>欢迎使用' + EossGlobal.projectName + EossGlobal.projectVersion + '权限管理系统',
		showType : 'slide',
		timeout : 4000
	});
});