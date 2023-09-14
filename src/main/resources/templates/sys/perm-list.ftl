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
                <td style="text-align: left;">名称:
                    <input class="easyui-textbox" type="text" id="name" name="name"></input>
                </td>

                <td style="text-align: left;">类型:
                    <select class="easyui-combobox" id="type" name="type" style="width:100px;" data-options=" panelHeight:'auto'">
                        <option value="" >请选择</option>
                        <option value="menu" >菜单</option>
                        <option value="func" >功能</option>
                    </select>
                </td>

                <td style="text-align: left;">页面地址:
                    <input class="easyui-textbox" type="text" id="url" name="url" data-options="width:'250px'"></input>
                </td>

                <td style="text-align: left;">提交地址:
                    <input class="easyui-textbox" type="text" id="urlSubmit" name="urlSubmit" data-options="width:'250px'"></input>
                </td>
            </tr>
            <tr>
                <td style="text-align: left;">叶子:
                    <select class="easyui-combobox" name="leaf" id="leaf" style="width:100px;" data-options="panelHeight:'auto'">
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
<table id="permDataGrid" style="width: 100%; height: auto;"></table>

<!-- your javascript file -->
<script src="/static/js/sys/perm-list.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
