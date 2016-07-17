<%--
  Created by IntelliJ IDEA.
  User: zxc
  Date: 16/7/14
  Time: 下午4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>详情页</title>
    <%@include file="common/head.jsp"%>
    <%@include file="common/jstl.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
                <h1>${seckill.name}</h1>
            </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <span class="glyphicon glyphicon-time"></span>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>
        <div id="killphoneModal" class="modal fade">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"/>
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" id="killphoneKey" name="killPhone" class="form-control" placeholder="填入手机号">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="killphoneMsg" class="glyphicon"></span>
                    <button type="button" id="killphoneBtn" class="btn-success">
                        <span class="glyphicon glyphicon-phone"/>提交
                    </button>
                </div>
            </div>
        </div>
    </div>
    <script src="/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="/bower_components/jquery.cookie/jquery.cookie.js"></script>
    <script src="/bower_components/jquery.countdown/dist/jquery.countdown.min.js"></script>
    <script src="/assets/js/seckill.js"></script>
    <script type="text/javascript">
        $(function(){
            seckill.detail.init({
                seckillId:${seckill.seckillId},
                startTime:${seckill.startTime.time},
                endTime:${seckill.endTime.time}
            });
        });
    </script>
</body>
</html>
