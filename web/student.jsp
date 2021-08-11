<%--
  Created by IntelliJ IDEA.
  User: 林甜甜
  Date: 2021/8/3
  Time: 1:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>
<script type="text/javascript">
    $.ajax({
        type:"get",
        url:"/student.json",
        dataType:"json",
        success:function (data) {
            $.each(data.rows,
                function (i, values) {
                    $("#list").append(
                        "<tr>"
                        +"<td><input type='checkbox'></td>"
                        +"<td>"+values.name+"</td>"
                        +"<td>"+values.age+"</td>"
                        +"<td>"+values.class+"</td></tr>"
                    );

                }
            );
        },
    })
</script>
<table border="1px solid black">
    <caption>表格</caption>
    <thead>
    <tr>
        <th><input type="checkbox"></th>
        <th>姓名</th>
        <th>年龄</th>
        <th>班级</th>
    </tr>
    </thead>
    <tbody id="list"></tbody>
</table>

</body>
</html>
