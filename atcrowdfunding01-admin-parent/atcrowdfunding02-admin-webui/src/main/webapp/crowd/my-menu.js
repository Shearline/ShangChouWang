//生成树形结构的函数

function generateTree() {
    $.ajax({
        "url": "menu/get/whole/tree.json",
        "type": "post",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                // 2.创建 JSON 对象用于存储对 zTree 所做的设置
                var setting = {
                    "view": {
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom": myAddHoverDom,
                        "removeHoverDom": myRemoveHoverDom,
                    },
                    "data": {
                        "key": {
                            "url": "maomi"
                        }
                    }
                };
                // 3.从响应体中获取用来生成树形结构的 JSON 数据
                var zNodes = response.data;
                // 4.初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }
            if (result == "FAILED") {
                layer.msg(response.message);
            }
        }
    });
}

function myAddDiyDom(treeId, treeNode) {
    console.log("treeId=" + treeId);

    console.log(treeNode);

    var spanId = treeNode.tId + "_ico";
    $("#" + spanId).removeClass().addClass(treeNode.icon);

}

//在鼠标离开节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";
    $("#" + btnGroupId).remove();
}

//在鼠标移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";

    if ($("#" + btnGroupId).length > 0) {
        return;
    }
    var addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    var level = treeNode.level;

    var btnHTML = "";

    if (level == 0) {
        btnHTML = addBtn;
    }
    if (level == 1) {
        btnHTML = addBtn + " " + editBtn;

        var length = treeNode.children.length;
        if (length == 0) {
            btnHTML = btnHTML + " " + removeBtn;

        }
    }
    if (level == 2) {
        btnHTML = editBtn + " " + removeBtn;
    }

    var anchorId = treeNode.tId + "_a";

    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>");

}