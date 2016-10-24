$(function(){
	var $name = $('#menuName');
	var $target = $('#menuTarget');
	var $url = $('#menuUrl');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');
	
	var $lev = $('#menuLev');
	var $path = $('#menuPath');
	var $permissionId = $('#menuId');
	
	/*************************************** init zTree ***************************************/
	var $pid = $('#menuPid');
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
			chkStyle: "radio",
			radioType: "all"
		}
	};
	
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, $.parseJSON($zTreeNodeJson.text()));

	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
//		alert(EossGlobal.basePath);
//		return;
		
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','权限名称不能为空');
			return;
		}
		
		if($.trim($target.val()) == ''){
			top.$.messager.alert('提示','权限目标不能为空');
			return;
		}
		
		var url = $.trim($url.val());
		if(url == ''){
			top.$.messager.alert('提示','权限地址不能为空');
			return;
		}else{
			var reg = '^\/';
			if(url.search(reg) == -1){
				top.$.messager.alert('提示','权限地址必须从服务器根目录开始，例如/system/usr/add.ac');
				return;
			}
		}
		
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		if(checkedNodes.length == 0){
			top.$.messager.alert('提示','必须选择一个所属菜单');
			return;
		}
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		
		//收集表单数据
		$pid.val(pa.join(','));
		$lev.val(checkedNodes[0].lev + 1);
		$path.val(getTreePath(checkedNodes[0]) + '#' + $permissionId.val());
//		alert($lev.val() + ',' + $path.val());
		
		top.$.messager.confirm('提示','确认要提交吗？', function(r){
			if(r){
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