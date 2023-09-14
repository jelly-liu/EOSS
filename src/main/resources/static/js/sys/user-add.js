$(function () {
    // 角色列表
    loadRoleData(1, 100, '#roleDataGrid');

    // 权限列表
    loadPermData(1, 1000, '#permDataGrid');

    // 提交按钮
    $('#submitBtn').click(function () {
        $('#dataForm').form('submit', {
            url: UrlConst.user.add,
            onSubmit: function(params){
                let valid = $(this).form('validate');
                // 收集选中的角色
                var roleRows = $('#roleDataGrid').datagrid('getChecked');
                var roleIds = [];
                for(var i = 0; i < roleRows.length; i++){
                    roleIds.push(roleRows[i].id)
                }
                params.roleIds = roleIds.join(',');
                // 收集选中的权限
                var permRows = $('#permDataGrid').datagrid('getChecked');
                var permIds = [];
                for(var i = 0; i < permRows.length; i++){
                    permIds.push(permRows[i].id)
                }
                params.permIds = permIds.join(',');
                return valid;
            },
            success: function (dataText) {
                let data = $.parseJSON(dataText);
                // 失败后，提示失败信息
                console.log(data)
                if(data.success === false){
                    $.messager.alert('提示', data.errMsg)
                    return;
                }
                // 成功后，关闭窗口，刷新数据
                $('#addWindow').window('close');
                $('#userDataGrid').datagrid("getPager").pagination('select');
                // $('#userDataGrid').datagrid('reload');
                // setTimeout(function () {
                //     $('#userDataGrid').closest('.datagrid').find('.pagination-load').closest('a').click()
                // }, 1000)
            }
        })
    })

    $('#cancelBtn').click(function () {
        console.log('cancel form')
        // 关闭当前窗口
        $('#addWindow').window('close');
    })
})

function loadRoleData(pageNumber, pageSize, roleDataGridId) {
    // 角色列表 配置
    $(roleDataGridId).datagrid({
        title: '角色列表',  //表格名称
        idField: 'id', //指示哪个字段是标识字段
        // pagination: true,//如果表格需要支持分页，必须设置该选项为true
        pageSize: 5,   //表格中每页显示的行数
        // pageList: [1, 2, 3, 4, 5, 10, 20, 40, 80, 100, 200, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000],
        striped: true,  //奇偶行是否使用不同的颜色
        loadMsg: 'loading...',   //加载数据时显示提示信息
        singleSelect: false, // 只能单选行
        fitColumns: true, //自动扩大或缩小列的尺寸
        selectOnCheck: true,
        checkOnSelect: true,
        columns: [[
            {field:'ck',checkbox:true },
            {field: 'id', title: 'ID', width: 50, align: 'left'},
            {field: 'name', title: '名称', width: 100, align: 'left'}
        ]]
    });

    // 角色数据 远程加载
    $.ajax({
        url: UrlConst.role.list,
        type: "post",
        dataType: 'json',
        data: {
            page: pageNumber,
            size: pageSize
        },
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

function loadPermData(pageNumber, pageSize, permDataGridId) {
    // 角色列表 配置
    $(permDataGridId).datagrid({
        title: '权限列表',  //表格名称
        idField: 'id', //指示哪个字段是标识字段
        // pagination: true,//如果表格需要支持分页，必须设置该选项为true
        pageSize: 5,   //表格中每页显示的行数
        // pageList: [1, 2, 3, 4, 5, 10, 20, 40, 80, 100, 200, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000],
        striped: true,  //奇偶行是否使用不同的颜色
        loadMsg: 'loading...',   //加载数据时显示提示信息
        singleSelect: false, // 只能单选行
        fitColumns: true, //自动扩大或缩小列的尺寸
        selectOnCheck: true,
        checkOnSelect: true,
        columns: [[
            {field:'ck',checkbox:true },
            {field: 'id', title: 'ID', width: 100, align: 'left'},
            {field: 'name', title: '名称', width: 100, align: 'left'},
            {field: 'type', title: '类型', width: 100, align: 'left'},
            {field: 'url', title: '地址', width: 300, align: 'left'},
        ]]
    });

    // 角色数据 远程加载
    $.ajax({
        url: UrlConst.perm.list,
        type: "post",
        dataType: 'json',
        data: {
            page: pageNumber,
            size: pageSize,
            leaf: 1
        },
        success: function (res) {
            if (res && res.success === true) {
                let page = {};
                page.total = res.total;
                page.rows = res.data;
                $(permDataGridId).datagrid("loadData", page);
            } else {
                $.messager.alert("提示", res.data.message, "info");
            }
        },
        beforeSend: function () {
            $(permDataGridId).datagrid("loading");
        },
        complete: function () {
            $(permDataGridId).datagrid("loaded");
        }
    });
}