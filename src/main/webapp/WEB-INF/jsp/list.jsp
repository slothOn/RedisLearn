<%--
  Created by IntelliJ IDEA.
  User: zxc
  Date: 16/7/13
  Time: 下午11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Seckill List</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/jstl.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>秒杀列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>名称</th>
                            <th>库存</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>创建时间</th>
                            <th>详情页</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="listitem" items="${list}">
                        <tr>
                            <td>${listitem.name}</td>
                            <td>${listitem.number}</td>
                            <td><fmt:formatDate value="${listitem.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
                            <td><fmt:formatDate value="${listitem.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${listitem.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><a class="btn btn-info" target="_blank" href="/seckill/${listitem.seckillId}/detail">Detail</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>
