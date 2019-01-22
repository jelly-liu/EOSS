$(function(){
	//ajax取用户拥有的权限
	loadPermission();
});

function loadPermission(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: EossGlobal.basePath + '/system/menu/queryTreeByUser',
		success: function(zTreeJson){
			createZTree(zTreeJson);
		}
	});
}

/*
function createZTree(zTreeJson){
	if(zTreeJson.length == 0){
		$('#loadingTd').text('您没有任何权限');
		return;
	}

	$('#loadingTd').closest('table').hide();

	var zTreeSetting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var $zTreeUL = $("#zTreeUL");
	$zTreeUL.empty();
	$.fn.zTree.init($zTreeUL, zTreeSetting, zTreeJson);
	$zTreeUL.show();
}*/

function createZTree(zTreeJson){
    if(zTreeJson.length == 0){
        $('#loadingTd').text('您没有任何权限');
        return;
    }

    $('#loadingTd').closest('table').hide();

    var zTreeSetting = {
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    var $zTreeUL = $("#zTreeUL");
    $zTreeUL.empty();
    $.fn.zTree.init($zTreeUL, zTreeSetting, zTreeJson);
    $zTreeUL.show();
}

function zTreeOnClick(event, treeId, treeNode) {
    preventDefa(event);
    // alert(treeNode.tId + ", " + treeNode.id + ',' + treeNode.name + ',' + treeNode.url + ',' + treeNode.target);

    if(treeNode.url == null){
        return;
    }

    var $mainCenterTabs = top.$('#mainCenterTabs');

    if ($mainCenterTabs.tabs('exists', treeNode.name)){
        $mainCenterTabs.tabs('select', treeNode.name);

        var $tab = $mainCenterTabs.tabs('getSelected');

        var ct = '<iframe src="#src#" frameborder=0 width="100%" height="100%"></iframe>';
        ct = ct.replace('#src#', treeNode.url);

        $mainCenterTabs.tabs('update', {
            tab: $tab,
            options: {
                content:ct,
            }
        });
    } else {
        var ct = '<iframe src="#src#" frameborder=0 width="100%" height="100%"></iframe>';
        ct = ct.replace('#src#', treeNode.url);
        $mainCenterTabs.tabs('add',{
            title: treeNode.name,
            content:ct,
            closable:true
        });
    }
}

function preventDefa(e){
    if(window.event){
        //IE中阻止函数器默认动作的方式
        window.event.returnValue = false;
    }
    else{
        //阻止默认浏览器动作(W3C)
        e.preventDefault();
    }
}