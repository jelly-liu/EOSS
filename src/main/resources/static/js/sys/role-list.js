$(function () {
    let roleDataGridId = '#roleDataGrid';

    // 查询按钮
    $('#searchBtn').click(function () {
        let options = $(roleDataGridId).datagrid("getPager").pagination("options");
        loadData(1, options.pageSize, roleDataGridId);
    })

    // dataGrid配置
    $(roleDataGridId).datagrid({
        title: '角色列表',  //表格名称
        idField: 'id', //指示哪个字段是标识字段
        pagination: true,//如果表格需要支持分页，必须设置该选项为true
        pageSize: 20,   //表格中每页显示的行数
        pageList: [1, 2, 3, 4, 5, 10, 20, 40, 80, 100, 200, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000],
        striped: true,  //奇偶行是否使用不同的颜色
        loadMsg: 'loading...',   //加载数据时显示提示信息
        singleSelect: false, // 只能单选行
        selectOnCheck: true,
        checkOnSelect: true,
        fitColumns: true, //自动扩大或缩小列的尺寸
        columns: [[
            {field:'ck',checkbox:true },
            {field: 'id', title: 'ID', width: 50, align: 'left'},
            {field: 'name', title: '名称', width: 100, align: 'left'},
            {field: 'createTime', title: '创建时间', width: 200, align: 'left'},
            {field: 'updateTime', title: '修改时间', width: 200, align: 'left'},
            {field: '_operate', title: '操作', width: 150, align: 'center', formatter: formatOpt},
        ]],
        // toolbar: buttonsFunction(roleDataGridId),
        tools: buttonsFunction(roleDataGridId)
    });

    /* 数据表格翻页初始化 */
    $(roleDataGridId).datagrid("getPager").pagination({
        onSelectPage: function (pageNumber, pageSize) {
            loadData(pageNumber, pageSize, roleDataGridId)
        }
    });

    // 正式加载表格数据
    // let options = $(roleDataGridId).datagrid("getPager").pagination("options");
    // loadData(1, options.pageSize, roleDataGridId);
    $('#searchBtn').click();
})


function buttonsFunction(roleDataGridId){
    let permMap = JSON.parse($('#permMap').val());
    // $('#permMap').remove();
    console.log(permMap);

    var buttons = [];
    if(permMap['add']){
        buttons.push({
            text: '添加',
            iconCls: 'icon-add',
            handler: function () {
                openAddDataDivIFrame();
            }
        });
        buttons.push('-');
    }
    if(permMap['update']){
        buttons.push({
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                // 收集选中的数据
                var rows = $(roleDataGridId).datagrid('getChecked');
                if(rows.length !== 1){
                    $.messager.alert('提示', '请选择1个数据');
                    return;
                }

                // 单选
                let row = $(roleDataGridId).datagrid('getSelected');
                if (!row) {
                    top.$.messager.alert('提示', '请选择您想要操作的数据');
                    return;
                }
                // console.log(row)
                let rowIndex = $(roleDataGridId).datagrid('getRowIndex', row);
                openUpdateDataDivIFrame(row.id, rowIndex);
                console.log('select1 row, id', row.id, 'rowIndex', rowIndex)
            }
        })
        buttons.push('-')
    }
    if(permMap['delete']){
        buttons.push({
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                // 单选
                // let row = $(roleDataGridId).datagrid('getSelected');
                // if (!row) {
                //     top.$.messager.alert('提示', '请选择您想要操作的数据');
                //     return;
                // }
                // let rowIndex = $(roleDataGridId).datagrid('getRowIndex', row);
                // deleteData(row.id, rowIndex, roleDataGridId);

                // 收集选中的数据
                var rows = $(roleDataGridId).datagrid('getChecked');
                var ids = [];
                for(var i = 0; i < rows.length; i++){
                    ids.push(rows[i].id)
                }

                deleteData(ids.join(','), roleDataGridId);
            }
        })
        buttons.push('-')
    }
    return buttons;
}


// 加载数据
function loadData(pageNumber, pageSize, roleDataGridId) {
    // 收集查询参数
    let params = {
        page: pageNumber,
        size: pageSize
    }
    // params.name = $('#name').val();
    var t = $('#searchForm').serializeArray();
    $.each(t, function() {
        params[this.name] = this.value;
    });

    $.ajax({
        url: UrlConst.role.list,
        type: "post",
        dataType: 'json',
        data: params,
        success: function (res) {
            if (res && res.success === true) {
                let page = {};
                page.total = res.total;
                page.rows = res.data;
                $(roleDataGridId).datagrid("loadData", page);
            } else {
                $.messager.alert("提示", res.data.message, "info");
            }
        },
        beforeSend: function () {
            $(roleDataGridId).datagrid("loading");
        },
        complete: function () {
            $(roleDataGridId).datagrid("loaded");
        }
    });
}

// 自定义列 操作列
function formatOpt(value, rowData, rowIndex) {
    // console.log(value, rowData, rowIndex)

    $t = $('<div/>');

    $u = $('<a>更新</a>').attr({
        'href': '###',
        'onclick': 'openUpdateDataDivIFrame(' + rowData.id + ',' + rowIndex + ')'
    })

    $d = $('<a>删除</a>').attr({
        'href': '###',
        'onclick': 'deleteData(' + rowData.id + ',' + '"#roleDataGrid"' + ')'
    })

    let permMap = JSON.parse($('#permMap').val());
    if(permMap['update']){
        $t.append($u); $t.append('    ');
    }
    if(permMap['delete']){
        $t.append($d); $t.append('    ');
    }

    return $t.html();
}

// 自定义列 操作列
function formatYesNo(value, rowData, rowIndex) {
    return value === 0 ? '否' : '是';
}

// 删除数据
function deleteData(ids, roleDataGridId) {
    console.log('delete', ids)
    if(!ids || ids === ''){
        $.messager.alert('提示', '请选择数据')
        return;
    }

    $.messager.confirm('提示', '确定要执行该操作吗？', function (r) {
        if (r) {
            // console.log('delete, yes')
            $.ajax({
                type: 'POST',
                dataType: 'json',
                data: 'ids=' + ids,
                url: UrlConst.role.delete,
                success: function(rs){
                    if(rs.success){
                        $(roleDataGridId).datagrid("getPager").pagination('select')
                        // $.messager.alert('提示','操作成功!','info',function(){});
                    }else{
                        top.$.messager.alert('提示',rs.errMsg);
                    }
                }
            });
        }
    });
}

// 打开编辑窗口，远程加载
function openUpdateDataDivIFrame(id, rowIndex) {
    let href = UrlConst.role["to-update"] + '?id=' + id;
    $('<div/>').window({
        id: 'updateWindow',
        title: '角色更新',
        width: $(window).width() / 1.5,
        height: 'auto',
        top: '100',
        modal: true,
        minimizable: false,
        maximized: true,
        href: href,
        'loadingMessage': 'loading...',
        onOpen: function () {
            console.log('on open')
        },
        onClose: function () {
            console.log('on close')
            $(this).window('destroy');
        },
        onDestroy: function () {
            console.log('on destroy')
        }
    });
}

// 打开添加窗口，远程加载
function openAddDataDivIFrame(rowIndex) {
    let href = UrlConst.role["to-add"];
    $('<div/>').window({
        id: 'addWindow',
        title: '角色添加',
        width: $(window).width() / 1.5,
        height: 'auto',
        top: '100',
        modal: true,
        minimizable: false,
        maximized: true,
        href: href,
        'loadingMessage': 'loading...',
        onOpen: function () {
            console.log('on open')
        },
        onClose: function () {
            console.log('on close')
            $(this).window('destroy');
        },
        onDestroy: function () {
            console.log('on destroy')
        }
    });
}

function hasAddPerm(){

}