$(function(){
	var $name = $('#userName');
	var $pwd = $('#userPwd');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

	var $roleIds = $('#roleIds');
    var $groupIds = $('#groupIds');

	/*************************************** init zTree ***************************************/
	var $resourceIds = $('#resourcesIds');
	var $zTreeULResource = $("#zTreeULResource");
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
		},
		async: {
			enable: true,
			url: EossGlobal.basePath + '/system/menu/querySubAjax?withoutUrl=y',
			autoParam: ['id'],
			otherParam: {'onlyLeafCanCheck':'yes', 'openAll':'yes'},
			dataFilter: function(treeId, parentNode, responseData){
				//filter the response data, but we do nothing at here, just return the whole data immediately
				return responseData;
			}
		}
	};
	var zTreeObjResource = $.fn.zTree.init($zTreeULResource, zTreeSettingResource, null);
	
	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','用户名称不能为空');
			return;
		}
		
		if($.trim($pwd.val()) == ''){
			top.$.messager.alert('提示','用户密码不能为空');
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
		
		//--------------------- collect resources data ---------------------
		var paResource = [];
		var checkedNodesResource = zTreeObjResource.getCheckedNodes(true);

		// if(checkedNodesResource.length == 0){
		// 	var text = '您没有为用户选择任何资源，你也可以在"用户更新"功能中继续分配资源，您确实要这样操作吗？';
		// 	top.$.messager.confirm('提示', text, function(r){
		// 		if(r){
		// 			$form.submit();
		// 		}
		// 	});
        //
		// 	return;
		// }

		for(var i = 0; i < checkedNodesResource.length; i++){
			paResource.push(checkedNodesResource[i].id);
		}
		$resourceIds.val(paResource.join(','));
		
		top.$.messager.confirm('提示', '确定要提交吗？', function(r){
            if (r){
            	$form.submit();
            }
        });
	});
});