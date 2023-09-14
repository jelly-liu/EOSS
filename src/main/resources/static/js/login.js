let positionChars = [];
let charMaskDiv = "<div class='icodeClickMask'></div>";

function randomColor(){
	var red = Math.round(Math.random()*255);
	var green = Math.round(Math.random()*255);
	var blue = Math.round(Math.random()*255);
	// 获取三个rgb的值
	var color= "rgb(" + red + "," + green + "," + blue + ")";
	// 吧rhb值拼接在一起给变量color
	return color;
}

function randomWithRange(min, max) { // min最小值，max最大值
	return Math.floor(Math.random() * (max - min)) + min;
}

//移除已点击的文字坐标遮罩层
function clearClicked(){
	$('.icodeClickMask').remove();
	positionChars = [];
}

$(function() {
	//随机背景图片
	// var imgNum = randomWithRange(0, 2);
	// var imgStr = "#99cdff url('/static/images/cbg1/cbg0" + imgNum + ".jpeg";
	// $(document.body).css({
	// 	background: imgStr
	// })

	$('#submitBtn').click(function () {
		login();
	})

	//登陆对话框
	$('#loginFormDiv').dialog({
		title : '欢迎使用EOSS',
		width : 400,
		closed : false,
		modal : true,
		closable : false
	});
	
	//登陆验证
	let $name = $('#name');
	let $pwd = $('#pwd');
	let $vCode = $('#vCode');
	function login(){
		if($.trim($name.val()) == ''){
			$.messager.alert('提示','用户名不能为空!');
			return;
		}
		if($.trim($pwd.val()) == ''){
			$.messager.alert('提示','密码不能为空!');
			return;
		}
		if(positionChars.length != 3){
			$.messager.alert('提示','请点击验证码!');
			clearClicked();
			return;
		}
		
		$.messager.wait('正在登陆，请稍候...');
		
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: EossGlobal.basePath + '/sys/login',
			data: {
				username:$name.val(),
				password:$pwd.val(),
				chrPositions: positionChars.join(';')},
			success: function(rs){
				if(rs.success){
					window.location.href = EossGlobal.basePath + '/sys/to-main';
				}else{
					$.messager.waitClose();
					$.messager.alert('提示', rs.errMsg);
					clearClicked();
				}
			}
		});
	}

	//点击图片时，获取鼠标位置
	$('#icodeClickImg').click(function (e) {
		var x = e.pageX;
		var y = e.pageY;

		if (x == undefined) {
			x = e.clientX;
		}
		if (y == undefined) {
			y = e.clientY;
		}

		var offset = $(this).offset();
		var xr = x - offset.left;
		var yr = y - offset.top;

		var xrInt = parseInt(xr);
		var yrInt = parseInt(yr);
		console.info("xrInt=" + xrInt + ", yrInt=" + yrInt);

		if(positionChars.length >= 3){
			clearClicked();
		}

		//遮罩层的 中心
		positionChars.push(xrInt + "," + yrInt);

		//添加 被点击文字 遮罩层
		$(charMaskDiv).css({
			top: yrInt - 25/2,
			left: xrInt - 25/2,
			backgroundColor: randomColor(),
			fontcolor: randomColor()
		}).text(positionChars.length).appendTo($('#icodeClickImgDiv'));
	});
	
	//验证码点击后，更换新验证码
	$('#icodeClickImgRefresh').click(function(){
		$("#icodeClickImg").attr({
			src: EossGlobal.basePath + '/static/vCodeClick.jpg?' + new Date().getMilliseconds()
		});
		clearClicked();
	});

	//当按下回车键后，自动登陆
	$(window).keydown(function(event) {
		var keyCode = event.keyCode;
//		console.logger.keyCode);
		switch (keyCode) {
			case 13 : {
				logger.n();
				break;
			}
		}
	});
});