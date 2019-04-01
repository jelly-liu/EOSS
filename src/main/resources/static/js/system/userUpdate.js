$(function(){
	var $name = $('#userName');
	var $pwd = $('#userPwd');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

    $roleIds = $('#roleIds');
    $groupIds = $('#groupIds');

	/*************************************** init resource zTree ***************************************/
	var $resourceIds = $('#resourceIds');
	var $zTreeNodeJsonResource = $('#zTreeNodeJsonResource');
	var $zTreeULResource = $('#zTreeULResource');
	var zTreeSettingResource = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			dblClickExpand: false
		},
		check: {
			enable: true,
			chkStyle: 'checkbox'
		}
	};

	var zTreeObjResource = $.fn.zTree.init($zTreeULResource, zTreeSettingResource, $.parseJSON($zTreeNodeJsonResource.text()));
	
	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','用户名不能为空');
			return;
		}
		
		if($.trim($pwd.val()) == ''){
			top.$.messager.alert('提示','密码不能为空');
			return;
		}

        //------ collect role data ------
        var ra = [];
        var $roles = $(':checkbox:checked.role');
        $roles.each(function(){
            $this = $(this);
            ra.push($this.val());
        })
        if($roles.size() > 0){
            $roleIds.val(ra.join(','));
        }else{
            $roleIds.val('');
        }

        //------ collect group data ------
        var ga = [];
        var $groups = $(':checkbox:checked.group');
        $groups.each(function(){
            $this = $(this);
            ga.push($this.val());
        })
        if($groups.size() > 0){
            $groupIds.val(ga.join(','));
        }else{
            $groupIds.val('');
        }

		//------ collect resources data ------
		var paResource = [];
		var checkedNodesResource = zTreeObjResource.getCheckedNodes(true);
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodesResource.length; i++){
			paResource.push(checkedNodesResource[i].id);
		}
		$resourceIds.val(paResource.join(','));
		
		top.$.messager.confirm('提示','确认要提交吗？', function(r){
			if(r){
				$form.submit();
			}
		});
	});
});