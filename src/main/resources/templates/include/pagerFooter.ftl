<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<table class="eossDataTable tableXCenter">
    <tbody>
    <tr>
        <td align="right">
            <span>[${pager.page}/${pager.totalPage}页]</span>&nbsp
            <span>[${pager.rowStart}-${pager.rowEnd}/${pager.totalRow}条]</span>&nbsp

            <a href="###" onclick="goTo(1)">首页</a>
            <a href="###" onclick="goTo(${pager.page - 1})">&nbsp;|&nbsp;上一页</a>
            <a href="###" onclick="goTo(${pager.page + 1})">&nbsp;|&nbsp;下一页</a>
            <a href="###" onclick="goTo(${pager.totalPage})">&nbsp;|&nbsp;尾页</a>

            <a href="###" id="pagerFooterJumpPageHref">跳转&nbsp;</a><input type="text" id="pagerFooterJumpPage" style="width:30px;"/>&nbsp;
            每页大小<input type="text" id="pageSizeCustomize" value="${pager.pageSize}" style="width:30px;"/>&nbsp;
        </td>
    </tr>
    </tbody>
</table>
<script type="text/javascript">
    // console.log('formId:' + formId);
    // console.info('pageId:' + pageId);
    // console.info('totalPage:' + totalPage);

    var $form = $("#submitForm");
    var $page = $("#page");
    var $size = $("#pageSize");
    var $pageSizeCustomize = $("#pageSizeCustomize");
    var totalPage = ${pager.totalPage};

    function setPageSize(page){

    }

    function goTo(page){
        var reg = /^(\d+)$/;
        if(!reg.test(page)){
            top.$.messager.alert('提示','只能输入数字!');
            return;
        }

        if(page < 1){
            page = 1;
        }

        if(page > totalPage){
            page = totalPage;
        }

        $.messager.wait('正在操作，请稍候...');

        $page.val(page);
        $size.val($pageSizeCustomize.val());
        // alert($size.val());
        $form.submit();
    }

    $(function(){
        $('#pagerFooterJumpPageHref').click(function(){
            goTo($('#pagerFooterJumpPage').val());
        });
    });
</script>