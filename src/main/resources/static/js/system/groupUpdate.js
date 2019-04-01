$(function(){
	var $name = $('#name');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

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
			top.$.messager.alert('提示','组不能为空');
			return;
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