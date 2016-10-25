$(function(){
	//ajax取用户拥有的权限
	loadPermission();
});

function loadPermission(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: EossGlobal.basePath + '/system/menu/queryTreeByUser.ac',
		success: function(zTreeJson){
			createZTree(zTreeJson);
		}
	});
}

function createZTree(zTreeJson){
	if(zTreeJson.length == 0){
		$('#loadingTd').text('您没有任何权限');
		return;
	}
	
	$('#loadingTd').closest('table').hide();
	
	var zTreeSetting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	var $zTreeUL = $("#zTreeUL");
	$zTreeUL.empty();
	$.fn.zTree.init($zTreeUL, zTreeSetting, zTreeJson);
	$zTreeUL.show();
}