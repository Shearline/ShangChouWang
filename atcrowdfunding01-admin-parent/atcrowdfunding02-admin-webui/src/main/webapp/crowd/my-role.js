//ִ�з�ҳ������ҳ��Ч�����κ�ʱ�������������������¼���ҳ��
function generatePage() {
    //1. ��ȡ��ҳ����
    var pageInfo = getPageInfoRemote();
    //2.�����
    fillTableBody(pageInfo);

}

// Զ�̷��ʷ������˳����ȡpageInfo����
function getPageInfoRemote() {

    // ����$.ajax()�����������󲢽���$.ajax()�����ķ���ֵ
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

    // �жϵ�ǰ��Ӧ״̬���Ƿ�Ϊ200
    var statusCode = ajaxResult.status;

    // �����ǰ��Ӧ״̬�벻��200��˵�������˴�������������������ʾ��ʾ��Ϣ���õ�ǰ����ִֹͣ��
    if (statusCode != 200) {
        layer.msg("ʧ�ܣ���Ӧ״̬��=" + statusCode + " ˵����Ϣ=" + ajaxResult.statusText);
        return false;
    }

    // �����Ӧ״̬����200��˵��������ɹ�����ȡpageInfo
    var resultEntity = ajaxResult.responseJSON;

    // ��resultEntity�л�ȡresult����
    var result = resultEntity.result;

    // �ж�result�Ƿ�ɹ�
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return false;
    }

    // ȷ��resultΪ�ɹ����ȡpageInfo
    var pageInfo = resultEntity.data;

    // ����pageInfo
    return pageInfo;
}


//�����
function fillTableBody(pageInfo) {
    //���tbody�оɵ�����
    $("#rolePageBody").empty();
    $("#Pagination").empty();

    //�ж�pageInfo�Ƿ���Ч
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length <= 0) {
        $("#rolePageBody").append("<tr><td colspan='4'>��Ǹ��û�в�ѯ�������������ݣ�</td></tr>");
        return;
    }

    //���tbody
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

//���ɷ�ҳҳ�뵼����
function generateNavigator(pageInfo) {
    //��ȡ�ܼ�¼��
    var totalRecord = pageInfo.total;

    //�����������
    var properties = {
        num_edge_entries: 3,                                            //��Եҳ��
        num_display_entries: 5,                                         //����ҳ��
        callback: paginationCallBack,                                   //�ص�
        items_per_page: pageInfo.pageSize,                              //ÿҳ��ʾ���ݵ�����
        current_page: pageInfo.pageNum - 1,                             //��ǰҳ��Pagination�ڲ�ʹ��pageIndex������ҳ�룬pageIndex��0��ʼ��PageNum��1��ʼ������Ҫ��һ
        prev_text: "��һҳ",
        next_text: "��һҳ",
    }

    $("#Pagination").pagination(totalRecord, properties)
}


//��ҳʱ�Ļص�����
function paginationCallBack(pageIndex, jQuery) {
    //�޸�window�����Num����
    window.pageNum = pageIndex + 1;

    //���÷�ҳ����
    generatePage();

    //ȡ��ҳ�볬���ӵ�Ĭ����Ϊ
    return false;

}
