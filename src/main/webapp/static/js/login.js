$(function() {
	//登陆对话框
	$('#loginFormDiv').dialog({
		title : '欢迎使用EOSS',
		width : 400,
		closed : false,
		modal : true,
		closable : false,
		buttons : [{
			text : '登录',
			handler : function() {
				login();
			}
		}]
	});
	
	//登陆验证
	$name = $('#name');
	$pwd = $('#pwd');
	$vCode = $('#vCode');
	function login(){
		if($.trim($name.val()) == ''){
			$.messager.alert('提示','用户名不能为空!');
			return;
		}
		if($.trim($pwd.val()) == ''){
			$.messager.alert('提示','密码不能为空!');
			return;
		}
		if($.trim($vCode.val()) == ''){
			$.messager.alert('提示','验证码不能为空!');
			return;
		}
		
		$.messager.wait('正在登陆，请稍候...');
		
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: EossGlobal.basePath + '/login.ac',
			data: {
				username:$name.val(),
				password:$pwd.val(),
				vCode:$vCode.val()},
			success: function(rs){
				if(rs.flag){
					window.location.href = EossGlobal.basePath + '/system/layout/main.ac';
				}else{
					$.messager.waitClose();
					$.messager.alert('提示', rs.msg);
				}
			}
		});
	}
	
	//验证码点击后，更换新验证码
	$('#icodeImg').click(function(){
		$(this).attr({
			src: EossGlobal.basePath + '/static/vCode.jpg?' + new Date().getMilliseconds()
		});
	});

	//当按下回车键后，自动登陆
	$(window).keydown(function(event) {
		var keyCode = event.keyCode;
//		console.log(keyCode);
		switch (keyCode) {
			case 13 : {
				login();
				break;
			}
		}
	});
});