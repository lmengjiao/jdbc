<%--
  Created by IntelliJ IDEA.
  User: 林甜甜
  Date: 2021/8/3
  Time: 0:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
    <select id="sheng">
        <option value="1">河南省</option>
    </select>
    <select id="shi">
        <option value="1">郑州市</option>
    </select>
    <select id="qu">
        <option value="1">金水区</option>
    </select>

    <%-- jquery --%>
    <%--<script>--%>
        <%--$(function(){--%>
            <%--//ajax访问后台--%>
            <%--$.ajax({--%>
                <%--url:'/AllCityServlet',--%>
                <%--type:'GET',--%>
                <%--data:'',--%>
                <%--dateType:'JSON',--%>
                <%--//成功才能访问--%>
                <%--success:function (res) {--%>
                    <%--//console.log(res)--%>
                    <%--//遍历--%>
                    <%--$.each(res.citylist,function (index, item) {--%>
                        <%--//console.log(index)--%>

                        <%--$("#sheng").empty();--%>
                        <%--$("#sheng").append(" <option value="+item.p+">"+item.p+"</option> ")--%>
                        <%--$.each(item.c,function (index,item) {--%>
                            <%--$("#city").empty();--%>
                            <%--$("#city").append(" <option value="+item.n+">"+item.n+"</option>")--%>
                            <%--$.each(item.a , function (index,item) {--%>
                                <%--console.log(item)--%>
                                <%--console.log($(this))--%>
                                <%--//  $("#qu").empty();--%>
                                <%--$("#qu").append(" <option value="+item.s+">"+item.s+"</option>")--%>
                            <%--});--%>
                        <%--});--%>


                    <%--});--%>
                <%--}--%>
            <%--})--%>
        <%--})--%>
    <%--</script>--%>
    <script>
        $(function () {
            //1. ajax 访问 后台
            $.ajax({
                url:'/AllCityServlet',
                type:'GET',
                data:'',
                dataType:'JSON',
                success:function (res) {
                    $.each(res.citylist,function (index, item) {
                        //console.log(index)

                        $("#sheng").empty();
                        $("#sheng").append(" <option value="+item.p+">"+item.p+"</option> ")
                        $.each(item.c,function (index,item) {
                            $("#shi").empty();
                            $("#shi").append(" <option value="+item.n+">"+item.n+"</option>")
                            $.each(item.a , function (index,item) {
                                console.log(item)
                                console.log($(this))
                                //  $("#qu").empty();
                                $("#qu").append(" <option value="+item.s+">"+item.s+"</option>")
                            });
                        });


                    });
                }

            });
        });

    </script>

</body>
</html>
