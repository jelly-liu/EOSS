<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <#include "/templates/include/head.ftl"/>
</head>

<body style="height: auto;">
<!-- 用户是否拥有 增删改 权限 -->
<input type="hidden" id="permMap" value='${permMap}'>

<!-- 表格搜索条件 -->
<div id="p" class="easyui-panel" title="查询参数" style="width:100%;height:auto;padding:1px;">
    <form id="searchForm" class="easyui-form" data-options="">
        <table class="eossFromTable tableXCenter">
            <tr>
                <td style="text-align: left; width: 200px;">名称:
                    <input class="easyui-textbox" type="text" id="username" name="username"></input>
                </td>
                <td style="text-align: left; width: 200px;">禁用:
                    <select class="easyui-combobox" id="disabled" name="disabled" style="width:100px;" data-options=" panelHeight:'auto'">
                        <option value="" >请选择</option>
                        <option value="1" >是</option>
                        <option value="0" >否</option>
                    </select>
                </td>
                <td style="text-align: left;" colspan="99">
                    <a id="searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok">查询</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- 数据显示表格 -->
<table id="userDataGrid" style="width: 100%; height: auto;"></table>

<!-- your javascript file -->
<script src="/static/js/sys/user-list.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
