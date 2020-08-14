<%--
  Created by IntelliJ IDEA.
  User: lxr69
  Date: 2020/8/13
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my-role.js"></script>
<script type="text/javascript">
    $(function () {
        //1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        //2.调用执行分页的函数，显示分页的效果
        generatePage();

        //3.给查询按钮绑定单击响应函数
        $("#searchBtn").click(function () {
            //1.获取关键词数据赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();

            //2.调用分页函数，刷新页面

            generatePage();

        });

        //4.点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal("show");
        });

        //5.给新增模态框中的保存按钮绑定单击相应函数
        $("#saveRoleBtn").click(function () {
            //1.获取用户在文本框中输入的角色名称
            /**
             * #addModal表示找到整个模态框，
             * 空格表示在后代元素中继续查找
             * [name=roleName]表示在后代元素中匹配name属性等于roleName的元素
             */

            var roleName = $.trim($("#addModal [name=roleName]").val());

            //2.发送Ajax请求
            $.ajax({
                "url": "role/save.json",
                "type": "post",
                "data": {
                    "name": roleName
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！")

                        //将页码定位到最后一页
                        window.pageNum = 999999;
                        generatePage();
                    } else if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            //关闭模态框
            $("#addModal").modal("hide");

            //清理模态框
            $("#addModal [name=roleName]").val("");


        });
        //6.给页面上的“铅笔”按钮绑定绑定单击响应函数，目的是打开模态框
        //传统的事件绑定方式只能在第一个页面有效，翻页后失效了

        // $(".pencilBtn").click(function () {
        //     alert("hahahha");
        // })

        //使用jQuery对象的on()函数可以解决上面的问题

        //1.首先找到所有动态生成的元素所附着的静态元素
        //2.on()函数的第一个参数是事件类型，
        //3.on()函数的第二个参数是找到真正要绑定事件的元素的选择器
        //4.on()函数的第三个参数是事件的响应函数
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            //打开模态框
            $("#editModal").modal("show");

            //获取表格中当前行中的角色名称
            //找他大爷
            var roleName = $(this).parent().prev().text();
            // alert(roleName);
            //获取当前角色的id
            window.roleId = this.id;

            //使用roleName的值设置模态框中的文本框
            $("#editModal [name=roleName]").val(roleName);
        });

        //7.给模态框中的更新按钮绑定单击响应函数
        $("#updateRoleBtn").click(function () {
            //1.从文本框中获取新的角色名称
            var roleName = $("#editModal [name=roleName]").val();
            //2.发送ajax请求执行更新
            $.ajax({
                "url": "role/update.json",
                "type": "post",
                "data": {
                    "id": window.roleId,
                    "name": roleName

                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！")

                        generatePage();
                    } else if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                "error": function () {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            //3.关闭模态框
            $("#editModal").modal("hide");
        });


        //8.点击确认模态框中的确认删除按钮执行删除

        $("#removeRoleBtn").click(function () {
            //从全局变量范围获取roleIdArray，转换为JSON字符串
            var requestBody = JSON.stringify(window.roleIdArray);
            $.ajax({
                "url": "role/remove/by/role/id/array.json",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS") {
                        layer.msg("操作成功！")
                        generatePage();
                    } else if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }

                },
                "error": function () {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            //关闭模态框
            $("#confirmModal").modal("hide");
        })

        //9.给单条删除的按钮绑定单击响应函数
        $("#rolePageBody").on("click", ".removeBtn", function () {
            //从当前按钮触发，获取当前角色名称
            var roleName = $(this).parent().prev().text();
            //创建role对象存入到数组中
            var roleArray = [{
                roleId: this.id,
                roleName: roleName
            }];
            console.log(roleArray)
            //打开模态框
            showConfirmModal(roleArray)
        });

        //10.给总的checkbox绑定单击响应函数
        $("#summaryBox").click(function () {
            //1.获取当前多选框自身的状态
            var currentStatus = this.checked;

            //2.用当前多选框的状态设置其他多选框
            $(".itemBox").prop("checked", currentStatus);
        });

        //11.全选、全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {
            //获取当前已经选中的itemBox的数量
            var checkedBoxCount = $(".itemBox:checked").length;

            //获取全部.itemBox的数量

            var totalBoxCount = $(".itemBox").length;

            //使用二者的比较结果设置总的checkBox
            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        });

        //12.给批量删除的按钮绑定单击响应函数

        $("#batchRemoveBtn").click(function () {
            //创建一个数组对象，用来存放后面获取到的角色对象
            var roleArray = [];
            // 遍历当前选中的多选框
            $(".itemBox:checked").each(function () {
                //使用this引用当前遍历得到的多选框
                var roleId = this.id;
                //通过DOM操作获取角色名称
                var roleName = $(this).parent().next().text();
                roleArray.push({
                    "roleId": roleId,
                    "roleName": roleName,
                });
                //检查roleArray的长度是否为0
                // console.log(roleArray.length);
                if (roleArray.length === 0) {
                    layer.msg("请至少选择一个执行删除");
                    return;
                }
                //调用专门的函数打开模态框
                showConfirmModal(roleArray);
                roleArray = [];
            })
        })


    });
</script>

<body>
<%@include file="/WEB-INF/include-nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="showAddModalBtn"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="summaryBox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            <tr>
                                <td>1</td>
                                <td><input type="checkbox"></td>
                                <td>PM - 项目经理</td>
                                <td>
                                    <button type="button" class="btn btn-success btn-xs"><i
                                            class=" glyphicon glyphicon-check"></i></button>
                                    <button type="button" class="btn btn-primary btn-xs"><i
                                            class=" glyphicon glyphicon-pencil"></i></button>
                                    <button type="button" class="btn btn-danger btn-xs"><i
                                            class=" glyphicon glyphicon-remove"></i></button>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/modal-role-add.jsp" %>
<%@include file="/WEB-INF/modal-role-edit.jsp" %>
<%@include file="/WEB-INF/modal-role-confirm.jsp" %>
</body>

</html>
