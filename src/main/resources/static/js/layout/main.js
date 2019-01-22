$(function() {
	//layout west
	$('body').layout('add', {
		region: 'west',
		title: '用户权限',
		split: true,
		width: 200,
		height: 'auto',
		collapsible: false,
		href: EossGlobal.basePath + '/system/layout/westAjax',
		tools: [{
			iconCls:'icon-reload',
        	handler:function(){
        		var $wp = $('body').layout('panel', 'west');
        		$wp.panel('refresh', $wp.panel('options').href);
    		}
		}]
	});

	$.messager.show({
		title : '登陆提示',
		msg : '用户:' + $('#userName').val() + '<br/>欢迎使用' + EossGlobal.projectName + EossGlobal.projectVersion + '权限管理系统',
		showType : 'slide',
		timeout : 4000
	});
});