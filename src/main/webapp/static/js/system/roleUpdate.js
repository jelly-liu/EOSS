$(function(){
	var $name = $('#roleName');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');
    var $permissionIdsHide = $('#permissionIdsHide');

	var pa1 = [];
	$(':checkbox:checked.permission').each(function(){
		pa1.push($(this).val());
	})
	$permissionIdsHide.val(pa1.join(','));


	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','角色名称不能为空');
			return;
		}

        //------ collect permission data ------
        var pa = [];
        var $permissions = $(':checkbox:checked.permission');
        $permissions.each(function(){
            $this = $(this);
            pa.push($this.val());
        })
        if($permissions.size() > 0){
            $permissionIdsHide.val(pa.join(','));
        }else{
            $permissionIdsHide.val('');
        }
		
		top.$.messager.confirm('提示', '确认要提交吗？', function(r){
			if(!r){
				return;
			}
			
			$form.submit();
		});
	});
});