$(function(){
	var $name = $('#roleName');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');
	
	/*************************************** init zTree ***************************************/
	var $permissionIds = $('#permissionIds');
	var $zTreeNodeJson = $('#zTreeNodeJson');
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
			chkStyle: 'checkbox'
		}
	};
	
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, $.parseJSON($zTreeNodeJson.text()));

	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','角色名称不能为空');
			return;
		}
		
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		
		if(checkedNodes.length == 0){
			var text = '您没有为角色选择任何权限，你也可以在"角色更新"功能中继续分配权限，您确实要这样操作吗？';
			top.$.messager.confirm('提示', text, function(r){
				if(!r){
					return;
				}
				
				$permissionIds.val('');
				$form.submit();
			}, null);
			
			return;
		}
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		$permissionIds.val(pa.join(','));
		
		top.$.messager.confirm('提示', '确认要提交吗？', function(r){
			if(!r){
				return;
			}
			
			$form.submit();
		});
	});
});