$(function(){
	//ztree配置
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
			chkStyle: 'radio',
			radioType: 'all'
		},
		async: {
			enable: true,
			url: EossGlobal.basePath + '/system/menu/queryTreeByUser.ac',
			autoParam: ['id'],
			otherParam: {'onlyParent':'yes'},
			dataFilter: function(treeId, parentNode, responseData){
				var dirId = $('#dirId').val();
				for(var i = 0; i < responseData.length; i++){
//					console.log(responseData[i].id);
					if(responseData[i].id == dirId){
						responseData[i].checked = true;
					}
				}
				return responseData;
			}
		},
		callback: {
			onCheck: function(){
				var tree = $.fn.zTree.getZTreeObj("zTreeUL");
				var nodes = tree.getCheckedNodes(true);
				
				var p = [];
				var pn = [];
				for(var i = 0; i < nodes.length; i++){
					p.push(nodes[i].id);
					pn.push(nodes[i].name);
				}
				
				$('#pnameText').val(pn.join('|'));
				$('#dirId').val(p.join(','));
			}
		}
	};
	var zTreeObj = $.fn.zTree.init($zTreeUL, zTreeSetting, null);
	
	//所属菜单选择弹出窗口
	$('#zTreeULWin').window({  
		title:'所属菜单选择',
	    width:$('#pnameText').width(),
	    modal:false,
	    closed:true,
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    draggable:false,
	    resizable:true
	});
	//所属菜单选择事件
	$('#pnameText').click(function(){
		var ofs = $(this).offset();
		
		$('#zTreeULWin').window('open');
		$('#zTreeULWin').window('move', {
			left: ofs.left,
			top: ofs.top + $(this).height()
		});
	});
	
	//删除事件
	$('table.eossDataTable').find('a[type=delete]').click(function(){
		$this = $(this);
		top.$.messager.confirm('提示','确定要执行该操作吗？', function(r){
			if(!r){
				return;
			}
			
			$.ajax({
				type: 'POST',
				dataType: 'text',
				data: 'id=' + $this.attr('value'),
				url: EossGlobal.basePath + '/system/resource/delete.ac',
				success: function(rs){
					if(rs == 'y'){
						top.$.messager.alert('提示','操作已成功','info', function(){
							$('#submitForm').submit();
						});
					}else{
						top.$.messager.alert('提示','操作失败，请联系系统管理员');
					}
				}
			});
		});
	});
	
	//更新事件
	$('table.eossDataTable').find('a[type=update]').click(function(){
		window.location.href = EossGlobal.basePath + '/system/resource/toUpdate.ac?id=' + $(this).attr('value');
	});

	/*************************************** check submit ***************************************/
	$('#submitBtn').click(function(){
		$('#page').val(1);
		$('#submitForm').submit();
	});
});