$(function(){
	var $name = $('#menuName');
	var $form = $('#submitForm');
	var $submitBtn = $('#submitBtn');
	
	var $lev = $('#menuLev');
	var $path = $('#menuPath');

	/*************************************** init zTree ***************************************/
	var $pid = $('#menuPid');
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
		},
		async: {
			enable: true,
			url: EossGlobal.basePath + "/system/menu/querySubAjax.ac",
			autoParam: ["id"],
			otherParam: {"onlyParent":"yes"},
			dataFilter: function(treeId, parentNode, responseData){
				return responseData;
			}
		}
	};
	
	var zNodes =[
		{id:1, pId:-1, name:"菜单根目录", open:false, isParent:true, iconSkin:"icon_eoss_home01"}
	];
	
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, zNodes);
	
	/*************************************** check submit ***************************************/
	$submitBtn.click(function(){
//		alert(EossGlobal.basePath);
//		return;
		
		if($.trim($name.val()) == ''){
			top.$.messager.alert('提示','菜单名称不能为空!');
			return;
		}
		
		var pa = [];
		var checkedNodes = zTreeObj.getCheckedNodes(true);
		if(checkedNodes.length == 0){
			top.$.messager.alert('提示','请选择至少一个父菜单!');
			return;
		}
		
		//收集已选中的checkbox值
		for(var i = 0; i < checkedNodes.length; i++){
			pa.push(checkedNodes[i].id);
		}
		
		//收集表单数据
		$pid.val(pa.join(','));
		$lev.val(checkedNodes[0].level + 1);
		$path.val(getTreePath(checkedNodes[0]));
//		alert($lev.val());
//		return;
		
		top.$.messager.confirm('提示', '确认要提交吗s？', function(r){
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