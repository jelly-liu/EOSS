$(function(){
	var $name = $('#userName');
	var $pwd = $('#userPwd');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');

	/*************************************** init zTree ***************************************/
	var $zTreeNodeJson = $('#zTreeNodeJson');
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
		}
	};
	
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, $.parseJSON($zTreeNodeJson.text()));
	
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
		
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		if(checkedNodes.length == 0){
			top.$.messager.confirm('提示','您没有选择该用户的角色，确定要提交吗？', function(r){
				if(r){
					$roleIds.val('');
					$form.submit();
				}
			}, null);
			
			return;
		}
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		$roleIds.val(pa.join(','));
		
		top.$.messager.confirm('提示','确认要提交吗？', function(r){
			if(r){
				$form.submit();
			}
		});
	});
});