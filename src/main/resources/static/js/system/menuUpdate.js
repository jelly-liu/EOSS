$(function(){
	var $name = $('#menuName');
	var $pid = $('#menuPid');
	var $form = $('#submitForm');
	var $submitButton = $('#submitBtn');
	
	var $lev = $('#menuLev');
	var $path = $('#menuPath');

	/*************************************** init zTree ***************************************/
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
			chkStyle: "radio",
			radioType: "all"
		}
	};
	
	var zTreeJson = $.parseJSON($('#zTreeJson').text());
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, zTreeJson);
	
	/*************************************** check submit ***************************************/
	var $menuId = $('#menuId');
	
	$submitButton.click(function(){
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','菜单名称不能为空');
			return;
		}
		
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		if(checkedNodes.length == 0){
			top.$.messager.alert('提示','请选择至少一个父菜单');
			return;
		}
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		
		//收集表单信息
		$pid.val(pa.join(','));
		$lev.val(checkedNodes[0].lev + 1);
		$path.val(getTreePath(checkedNodes[0]) + '#' + $menuId.val());
		
		top.$.messager.confirm('提示', '确定要执行该操作吗？', function(r){
            if (r){
            	$form.submit();
            }
        });
	});
	
	/*************************************** get tree path ***************************************/
	function getTreePath(treeNode){
		var paths = [];
		
		while(treeNode != null){
			paths.push(treeNode.id);
			treeNode = treeNode.getParentNode();
		}
		
		return paths.reverse().join('#');
	}
});