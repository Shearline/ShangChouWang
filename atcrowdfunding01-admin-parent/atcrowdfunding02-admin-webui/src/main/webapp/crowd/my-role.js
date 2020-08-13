//执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
    //1. 获取分页数据
    var pageInfo = getPageInfoRemote();
    //2.填充表格
    fillTableBody(pageInfo);

}

// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    // 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });

    console.log(ajaxResult);

    // 判断当前响应状态码是否为200
    var statusCode = ajaxResult.status;

    // 如果当前响应状态码不是200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return false;
    }

    // 如果响应状态码是200，说明请求处理成功，获取pageInfo
    var resultEntity = ajaxResult.responseJSON;

    // 从resultEntity中获取result属性
    var result = resultEntity.result;

    // 判断result是否成功
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return false;
    }

    // 确认result为成功后获取pageInfo
    var pageInfo = resultEntity.data;

    // 返回pageInfo
    return pageInfo;
}


//填充表格
function fillTableBody(pageInfo) {
    //清除tbody中旧的数据
    $("#rolePageBody").empty();
    $("#Pagination").empty();

    //判断pageInfo是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length <= 0) {
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉，没有查询到您搜索的数据！</td></tr>");
        return;
    }

    //填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";
        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>"
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }
    generateNavigator(pageInfo);

}

//生成分页页码导航条
function generateNavigator(pageInfo) {
    //获取总记录数
    var totalRecord = pageInfo.total;

    //声明相关属性
    var properties = {
        num_edge_entries: 3,                                            //边缘页数
        num_display_entries: 5,                                         //主体页数
        callback: paginationCallBack,                                   //回调
        items_per_page: pageInfo.pageSize,                              //每页显示数据的数量
        current_page: pageInfo.pageNum - 1,                             //当前页。Pagination内部使用pageIndex来管理页码，pageIndex从0开始，PageNum从1开始，所以要减一
        prev_text: "上一页",
        next_text: "下一页",
    }

    $("#Pagination").pagination(totalRecord, properties)
}


//翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    //修改window对象的Num属性
    window.pageNum = pageIndex + 1;

    //调用分页函数
    generatePage();

    //取消页码超链接的默认行为
    return false;

}
