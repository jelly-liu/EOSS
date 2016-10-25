$(function(){
	$('table.eossDataTable').find('a[type=delete]').click(function(){
		$this = $(this);
		top.$.messager.confirm('提示', '确定要执行该操作吗？', function(r){
			if(!r){
				return;
			}
			
        	$.ajax({
				type: 'POST',
				dataType: 'text',
				data: 'id=' + $this.attr('value'),
				url: EossGlobal.basePath + '/system/menu/delete.ac',
				success: function(rs){
					if(rs == 'y'){
						top.$.messager.alert('提示','操作已成功!','info',function(){
							$('#submitForm').submit();
						});
					}else{
						top.$.messager.alert('提示',rs);
					}
				}
			});
        });
	});
	
	$('table.eossDataTable').find('a[type=update]').click(function(){
		window.location.href = EossGlobal.basePath + '/system/menu/toUpdate.ac?id=' + $(this).attr('value');
	});

	/*************************************** check submit ***************************************/
	$('#submitBtn').click(function(){
		$('#page').val(1);
		$('#submitForm').submit();
	});
});