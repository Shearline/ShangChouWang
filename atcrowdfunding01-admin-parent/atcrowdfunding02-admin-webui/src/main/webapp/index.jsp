<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/8/4
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%-- http://localhost:8080/atcrowdfunding02_admin_webui_war_exploded/test/ssm.html --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                // console.log("hahah");
                $.ajax({
                    "url": "send/array/one.html",               //请求目标资源地址
                    "type": "post",                         //请求方式
                    "data": {
                        "array": [5, 8, 12]
                    },                     //要发送的请求参数
                    "dataType": "text",                     //如何对待服务器端返回参数
                    "success": function (response) {         //服务器端成功处理请求后的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error": function (response) {           //服务器端处理请求失败后的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            })
        });
        $(function () {
            $("#btn2").click(function () {
                // console.log("hahah");
                $.ajax({
                    "url": "send/array/two.html",               //请求目标资源地址
                    "type": "post",                         //请求方式
                    "data": {
                        "array[0]": 5,
                        "array[1]": 8,
                        "array[2]": 12
                    },                     //要发送的请求参数
                    "dataType": "text",                     //如何对待服务器端返回参数
                    "success": function (response) {         //服务器端成功处理请求后的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error": function (response) {           //服务器端处理请求失败后的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            })
        });
        $(function () {
            $("#btn3").click(function () {
                // console.log("hahah");
                //准备好要发送给服务器的数组
                var array = [5, 8, 12];
                console.log(array.length);
                var requestBody = JSON.stringify(array);
                console.log(requestBody.length);

                $.ajax({
                    "url": "send/array/three.html",            //请求目标资源地址
                    "type": "post",                          //请求方式
                    "data": requestBody,                     //要发送的请求参数
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "text",                      //如何对待服务器端返回参数
                    "success": function (response) {         //服务器端成功处理请求后的回调函数，response是响应体数据
                        alert(response);
                    },
                    "error": function (response) {           //服务器端处理请求失败后的回调函数，response是响应体数据
                        alert(response);
                    }
                });
            })
        });
        $(function () {
            $("#btn4").click(function () {
                var student = {
                    "id": 5,
                    "stuNAme": "tom",
                    "address": {
                        "provice": "广东",
                        "city": "深圳",
                        "street": "后瑞"
                    },
                    "subjectList": [
                        {
                            "subjectName": "JavaSE",
                            "subjectScore": 100
                        }, {
                            "subjectName": "SSM",
                            "subjectScore": 99
                        }
                    ],
                    "map": {
                        "k1": "v1",
                        "k2": "v2",
                    }
                };
                var requestBody = JSON.stringify(student);
                $.ajax({
                    "url": "send/compose/object.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {
                        console.log(response);
                    },
                    "error": function (response) {
                        console.log(response);

                    }
                })
            })
        });
        $(function () {
            $("#btn5").click(function () {
                layer.msg("Layer的弹框");
            });
        });

    </script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合的环境</a>
<br/>
<a href="admin/to/login/page.html">登录页面</a>

<button id="btn1">Send [5, 8, 12] One</button>
<br/>
<button id="btn2">Send [5, 8, 12] Two</button>
<br/>
<button id="btn3">Send [5, 8, 12] Three</button>
<br/>
<button id="btn4">Send Compose Object</button>
<br/>
<button id="btn5">点我弹框</button>
</body>
</html>
