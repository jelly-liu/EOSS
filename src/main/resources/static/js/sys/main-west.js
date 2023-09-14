$(function(){
	//ajax取用户拥有的权限
	loadPermission();
});

function loadPermission(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: EossGlobal.basePath + '/sys/west',
		success: function(res){
			createZTree(res.data);
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

    // 排序
    zTreeJson = zTreeJson.sort(function (a, b) {
        let param1 = a.sortNum;
        let param2 = b.sortNum;
        return param1 > param2;
    });

    $('#loadingTd').closest('table').hide();

    var zTreeSetting = {
        expandAll: true,
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    var $zTreeUL = $("#zTreeUL");
    $zTreeUL.empty();
    let $tree = $.fn.zTree.init($zTreeUL, zTreeSetting, zTreeJson);
    $tree.expandAll(true);
    $zTreeUL.show();
}

function zTreeOnClick(event, treeId, treeNode) {
    preventData(event);
    // alert(treeNode.tId + ", " + treeNode.id + ',' + treeNode.name + ',' + treeNode.url);

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

function preventData(e){
    if(window.event){
        //IE中阻止函数器默认动作的方式
        window.event.returnValue = false;
    }
    else{
        //阻止默认浏览器动作(W3C)
        e.preventDefault();
    }
}