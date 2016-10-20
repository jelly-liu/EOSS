$(function() {
	var dataGridConfigFun = function(){
		var configObj = {
			singleSelect: true,
			rownumbers: true,
			fitColumns: true,
			striped: true,
			pagination: true,
			pageSize:5,
			pageList:[5,10,15,20,30,50,100],
		    url: EossGlobal.basePath + '/page/queryForPage.ac',  
		    queryParams : {
		    	dataBaseType: 'mysql',
		    	statement: 'Menu_QueryMenuPage',
		    	leaf: 0,
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
						return $('<tmp/>').append($a1).html();
					}
		        }
		    ]],
		    onLoadSuccess: function(data){
		    	setTimeout(function(){
		    		bindEvent();
		    	}, 500);
		    }
		};
		
		return configObj;
	}
	
	function bindEvent(){
		$('a.delete_menu').click(function(){
			alert($(this).attr('menu_id'));
		});
	}
	
	//初始化dataGrid数据
	$('#grid').datagrid(dataGridConfigFun());
});