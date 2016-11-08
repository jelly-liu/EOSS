$(function(){
	var dataGridConfigFun = function(){
		var configObj = {
			singleSelect: true,
			rownumbers: true,
			fitColumns: true,
			striped: true,
			pagination: true,
			pageSize:3,
			pageList:[3,5,10,15,20,30,50,100],
		    url: EossGlobal.basePath + '/menu/queryMenuPage.ac',  
		    queryParams : {
		    	id: $('#menuId').val(),
		    	name: $('#menuName').val(),
		    	createTimeStart: $('#createTimeStart').val(),
		    	createTimeEnd: $('#createTimeEnd').val()
		    },
		    columns:[[  
		        {field:'name',title:'菜单名称',width:100},  
		        {field:'createDatetime',title:'创建时间',width:100},
		        {field:'updateDatetime',title:'修改时间',width:100},  
		        {field:'id',title:'操作',width:100,
		        	formatter: function(value,row,index){
						var $a1 = $('<a/>', {
							'text': '删除',
							'menu_id': row.id,
							'class': 'delete_menu',
							'href': '###'
						});
						
						var $a2 = $('<a/>', {
							'text': '更新',
							'menu_id': row.id,
							'class': 'update_menu',
							'href': '###'
						});
						return $('<tmp/>').append($a1).append('&nbsp;&nbsp;').append($a2).html();
					}
		        }
		    ]],
		    onLoadSuccess: function(data){
		    	//给jquery easyui datagrid 500毫秒的页面渲染时间
		    	setTimeout(function(){
		    		bindEvent();
		    	}, 500);
		    }
		};
		
		return configObj;
	}
	
	function bindEvent(){
		$('a.delete_menu').click(function(){
			$this = $(this);
			top.$.messager.confirm('提示', '确定要执行该操作吗？', function(r){
				if(!r){
					return;
				}
				
	        	$.ajax({
					type: 'POST',
					dataType: 'text',
					data: 'id=' + $this.attr('menu_id'),
					url: EossGlobal.basePath + '/menu/deleteMenu.ac',
					success: function(rs){
						if(rs == 'y'){
							top.$.messager.alert('提示','操作已成功!','info',function(){
								$('#dataGrid').datagrid('reload');
							});
						}else{
							top.$.messager.alert('提示',rs);
						}
					}
				});
	        });
		});
		
		$('a.update_menu').click(function(){
			window.location.href = EossGlobal.basePath + '/menu/updateMenuPrepare.ac?id=' + $(this).attr('menu_id');
		});
	}
	
	//初始化dataGrid数据
	$('#dataGrid').datagrid(dataGridConfigFun());

	/*************************************** check submit ***************************************/
	$('#submitBtn').click(function(){
		//初始化dataGrid数据
		$('#dataGrid').datagrid(dataGridConfigFun());
	});
});