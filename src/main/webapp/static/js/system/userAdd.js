$(function(){
	var $name = $('#userName');
	var $pwd = $('#userPwd');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

	/*************************************** init zTree ***************************************/
	var $roleIds = $('#roleIds');
	var $zTreeUL = $("#zTreeUL");
	var zTreeSetting = {
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
			chkStyle: "checkbox"
		},
		async: {
			enable: true,
			url: EossGlobal.basePath + "/system/role/queryAllAjax.ac",
			dataFilter: function(treeId, parentNode, responseData){
				//filter the response data, but we do nothing at here, just return the whole data immediately
				return responseData;
			}
		}
	};
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, null);

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
			url: EossGlobal.basePath + '/system/menu/querySubAjax.ac?withoutUrl=y',
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

		//--------------------- collect roles data ---------------------
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		// if(checkedNodes.length == 0){
		// 	top.$.messager.confirm('提示', '您没有选择该用户的角色，您确实要这样操作吗？', function(r){
	     //        if (r){
	     //        	$roleIds.val('');
		// 			$form.submit();
	     //        }
	     //    });
		// 	return;
		// }
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		$roleIds.val(pa.join(','));

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