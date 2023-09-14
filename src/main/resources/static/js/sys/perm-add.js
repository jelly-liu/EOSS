// 由于此js文件是ajax加载的，所以所有函数和变量，最好都写在 $(function(){xxx}) 里面
// 以此避免js函数和变量 扩张和泛滥
// 当然，如果是通过iframe来加载运行此js文件的，怎么写都行
$(function () {
    $('#submitBtn').click(function () {
        $('#dataForm').form('submit', {
            url: UrlConst.perm.add,
            onSubmit: function(params){
                console.log('perm add params', params);
                let valid = $(this).form('validate');
                console.log('valid', valid);
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
                $('#permDataGrid').datagrid("getPager").pagination('select')
                // setTimeout(function () {
                //     $('#dataGrid').closest('.datagrid').find('.pagination-load').closest('a').click()
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