function doLogout(){
	top.$.messager.confirm('提示', '您真的要注销吗？', function(r){
		if(r){
			$.ajax({
				type:'POST',
				dataType: 'json',
				url: EossGlobal.basePath + '/sys/logout',
				success: function(rs){
					if(rs.success){
						top.window.location.href = EossGlobal.basePath + '/sys/to-login';
					}
				}
			});
		}
	});
}

$(function() {
	//登陆信息提示
	$.messager.show({
		title : '登陆提示',
		msg : '用户:' + $('#username').val() + '<br/> 欢迎使用' + EossGlobal.projectName + EossGlobal.projectVersion + ' 权限管理系统',
		showType : 'slide',
		timeout : 9000
	});
});