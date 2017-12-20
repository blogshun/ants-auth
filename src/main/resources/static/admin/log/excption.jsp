<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 工具栏 -->
<div class="mini-toolbar">
    <table>
        <tr>
            <td style="width:100%;">
                <a id="remove" class="mini-button" iconCls="icon-user-delete">删除</a>
            </td>
            <td style="white-space:nowrap;">
                类型 <input id="type" class="mini-combobox w120" showNullItem="true" emptyText="选择类型" valueField="val"
                          textField="name" url="${ctx}/dict/code?id=YHLX&_refresh"/>
                条件 <input id="tj" class="mini-combobox w100" showNullItem="true" emptyText="选择条件" valueField="val"
                          textField="name" url="${ctx}/dict/code?id=YHTJ&_refresh"/>
                <input id="key" class="mini-textbox w200" emptyText="请输入关键字"/>
                <a id="search" class="mini-button" iconCls="icon-search" style="margin-right:0;">查询</a>
            </td>
        </tr>
    </table>
</div>
<!-- 数据列表 -->
<div id="userGrid" class="mini-datagrid" style="width:100%;"
     url="${ctx}/user/page" idField="id" multiSelect="true" pageSize="15"
>
    <div property="columns">
        <div type="checkcolumn"></div>
        <div field="avatar" width="40" renderer="r.renderAvatar">头像</div>
        <div field="account">账号</div>
        <div field="userName" width="70">昵称</div>
        <div field="sex" width="40" align="center" headerAlign="center" renderer="r.renderSex">性别</div>
        <div field="isLock" width="50">异常描述</div>
    </div>
</div>
<script type="text/javascript">
    mini.parse();
</script>