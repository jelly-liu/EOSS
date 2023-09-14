$(function () {
    // 加载权限列表
    let dataGridId = 'permDataGridDiv';
    $('#' + dataGridId).datagrid({
        // title: '数据表格',  //表格名称
        idField: 'id', //指示哪个字段是标识字段
        pagination: true,//如果表格需要支持分页，必须设置该选项为true
        pageSize: 100,   //表格中每页显示的行数
        pageList: [1, 2, 3, 4, 5, 10, 20, 40, 80, 100, 200, 1000, 2000, 5000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000],
        striped: true,  //奇偶行是否使用不同的颜色
        loadMsg: 'loading...',   //加载数据时显示提示信息
        singleSelect: false, // 只能单选行
        checkbox: true,
        fitColumns: true, //自动扩大或缩小列的尺寸
        selectOnCheck: true,
        checkOnSelect: true,
        border: true,
        width: '100%',
        columns: [[
            {field:'ck',checkbox:true },
            {field: 'id', title: 'ID', width: 100, align: 'left'},
            {field: 'name', title: '名称', width: 100, align: 'left'},
            {field: 'type', title: '类型', width: 100, align: 'left'},
            {field: 'url', title: '页面地址', width: 300, align: 'left'},
            {field: 'urlSubmit', title: '提交地址', width: 300, align: 'left'},
        ]]
    });

    /* 数据表格翻页初始化 */
    $('#' + dataGridId).datagrid("getPager").pagination({
        onSelectPage: function (pageNumber, pageSize) {
            loadPermData(dataGridId, pageNumber, pageSize)
        }
    });

    // 正式加载表格数据
    let options = $('#' + dataGridId).datagrid("getPager").pagination("options");
    loadPermData(dataGridId,1, options.pageSize);

    // 提交按钮
    $('#submitBtn').click(function () {
        $('#dataForm').form('submit', {
            url: UrlConst.role.add,
            onSubmit: function(params){
                // 使用validateBox验证表单
                let valid = $(this).form('validate');
                // 收集已选中的权限id
                var rows = $('#' + dataGridId).datagrid('getChecked');
                var perm_ids = [];
                for(var i = 0; i < rows.length; i++){
                    perm_ids.push(rows[i].id)
                }
                // 增加一个向后台传递的参数
                params.permIds = perm_ids.join(',');
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
                $('#roleDataGrid').datagrid("getPager").pagination('select')
                // setTimeout(function () {
                //     $('#dataGrid').closest('.datagrid').find('.pagination-load').closest('a').click()
                // }, 1000)
            }
        })
    })

    // 取消按钮
    $('#cancelBtn').click(function () {
        console.log('cancel form')
        // 关闭当前窗口
        $('#addWindow').window('close');
    })
})

// 加载数据
function loadPermData(dataGridId, pageNumber, pageSize) {
    // console.log(pageNumber, pageSize)
    $.ajax({
        url: UrlConst.perm.list,
        type: "post",
        dataType: 'json',
        data: {
            page: pageNumber,
            size: pageSize,
            leaf : 1 // 只查询叶子节点
        },
        success: function (res) {
            if (res && res.success === true) {
                let page = {};
                page.total = res.total;
                page.rows = res.data;
                $('#' + dataGridId).datagrid("loadData", page);
            } else {
                $.messager.alert("提示", res.data.message, "info");
            }
        },
        beforeSend: function () {
            $('#' + dataGridId).datagrid("loading");
        },
        complete: function () {
            $('#' + dataGridId).datagrid("loaded");
        }
    });
}