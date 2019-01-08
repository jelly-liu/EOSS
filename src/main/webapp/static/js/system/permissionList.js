$(function(){
	//删除事件
	$('table.eossDataTable').find('a[type=delete]').click(function(){
		$this = $(this);
		top.$.messager.confirm('提示','确定要执行该操作吗？', function(r){
			if(!r){
				return;
			}
			
			$.ajax({
				type: 'POST',
				dataType: 'text',
				data: 'id=' + $this.attr('value'),
				url: EossGlobal.basePath + '/system/permission/delete',
				success: function(rs){
					if(rs == 'y'){
						top.$.messager.alert('提示','操作已成功','info', function(){
							$('#submitForm').submit();
						});
					}else{
						top.$.messager.alert('提示','操作失败，请联系系统管理员');
					}
				}
			});
		});
	});
	
	//更新事件
	$('table.eossDataTable').find('a[type=update]').click(function(){
        $this = $(this);
        top.$.messager.confirm('提示','确定更新吗？', function(r){
            if(!r){
                return;
            }

            var name = $this.closest('tr').find('td[contenteditable]').text();
            name = $.trim(name);

            $.ajax({
                type: 'POST',
                dataType: 'text',
                data: 'id=' + $this.attr('value')+ '&name=' + name,
                url: EossGlobal.basePath + '/system/permission/updateAjax',
                success: function(rs){
                    if(rs == 'y'){
                        top.$.messager.alert('提示','操作已成功','info', function(){
                            $('#submitForm').submit();
                        });
                    }else{
                        top.$.messager.alert('提示','操作失败，请联系系统管理员');
                    }
                }
            });
        });
	});

    //添加事件
    $('table.eossDataTable').find('a[type=add]').click(function(){
        top.$.messager.prompt('提示', '请输入权限名称', function(r){
            r = $.trim(r);
            if (r && r != ""){
                $.ajax({
                    type: 'POST',
                    dataType: 'text',
                    data: 'name=' + r,
                    url: EossGlobal.basePath + '/system/permission/addAjax',
                    success: function(rs){
                        if(rs == 'y'){
                            top.$.messager.alert('提示','操作已成功','info', function(){
                                $('#submitForm').submit();
                            });
                        }else{
                            top.$.messager.alert('提示','操作失败，请联系系统管理员');
                        }
                    }
                });
            }
        });
    });

	/*************************************** check submit ***************************************/
	$('#submitBtn').click(function(){
		$('#page').val(1);
		$('#submitForm').submit();
	});
});