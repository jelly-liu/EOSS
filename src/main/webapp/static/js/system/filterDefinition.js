$(function(){
	var $rule = $('#rule');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

	$submitBtn.click(function(){
		top.$.messager.confirm('提示', '确认要提交吗s？', function(r){
			if (r){
				$form.submit();
			}
		});
	})
});