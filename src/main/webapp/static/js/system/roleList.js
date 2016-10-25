$(function(){
	var $form = $('#submitForm');
	var $submitButton = $('#submitBtn');
	
	$('table.eossDataTable').find('a[type=delete]').click(function(){
		$this = $(this);
		top.$.messager.confirm('提示','确定要执行该操作吗，所有用户将不再拥有此角色？', function(r){
			if(!r){
				return;
			}
			
			$.ajax({
				type: 'POST',
				dataType: 'text',
				data: 'id=' + $this.attr('value'),
				url: EossGlobal.basePath + '/system/role/delete.ac',
				success: function(rs){
					if(rs == 'y'){
						top.$.messager.alert('提示','操作已成功', 'info', function(){
							$('#submitForm').submit();
						});
					}else{
						top.$.messager.alert('提示','操作失败，请联系系统管理员');
					}
				}
			});
		});
	});
	
	$('table.eossDataTable').find('a[type=update]').click(function(){
		window.location.href = EossGlobal.basePath + '/system/role/toUpdate.ac?id=' + $(this).attr('value');
	});

	/*************************************** check submit ***************************************/
	$submitButton.click(function(){
		$('#page').val(1);
		$form.submit();
	});
});