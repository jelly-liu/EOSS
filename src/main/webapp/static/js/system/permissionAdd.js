$(function(){
	var $name = $('#permissionName');
	$submitBtn = $('#submitBtn');
	$form = $('#submitForm');

	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','权限名称不能为空');
			return;
		}
		
		var pa = [];
		pa.push($name.val());
		
		top.$.messager.confirm('提示','确认要提交吗？', function(r){
			if(!r){
				return;
			}
			$form.submit();
		});
	});
});