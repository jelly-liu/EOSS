<!DOCTYPE>
<html xmlns:th="http://www.thymeleaf.org">
<#include "/templates/include/head.ftl"/>
<title>管理页面</title>
</head>
<body class="easyui-layout">
<input type="hidden" id="userId" value="${sessionInfo.user.id}"/>
<input type="hidden" id="username" value="${sessionInfo.user.username}"/>

<!-- north -->
<#--        <div data-options="region:'north',split:true," style="height:50px;">-->
<#--            <iframe id="northFrame" name="northFrame" src="/system/layout/north" frameborder=0 width="100%" height="100%"></iframe>-->
<#--        </div>-->

<!-- west -->
<div data-options="region:'west', split:true, title:'功能菜单栏', tools:'#tt'" style="width:200px;">
    <iframe id="westFrame" name="westFrame" src="/sys/to-west" frameborder=0 width="100%" height="100%"></iframe>
    <div id="tt">
        <a href="javascript:void(0)" class="icon-logout" onclick="javascript:doLogout()"></a>
    </div>
</div>

<!-- center -->
<div data-options="region:'center',border:false">
    <div id="mainCenterTabs" class="easyui-tabs" data-options="border:false" style="width:100%;height:100%;">
        <div title="欢迎:${sessionInfo.user.username}" data-options="closable:false" style="width: 100%; height: 100%;">
            <table class="tableCenter">
                <tr>
                    <td style="font-size: 38px;">Welcome: ${sessionInfo.user.username}</td>
                </tr>
            </table>
        </div>
    </div>
</div>

<!-- south -->
<#--        <div data-options="region:'south',split:true" style="height:50px;">-->
<#--            <iframe id="southFrame" name="southFrame" src="/system/layout/south" frameborder=0 width="100%" height="100%"></iframe>-->
<#--        </div>-->

<!-- 版权提示 -->
<div id="copyrightDiv" style="display: none;">${COPY_RIGHT}</div>

<script src="/static/js/sys/main.js?time=${timestamp}" type="text/javascript"></script>
</body>
</html>
