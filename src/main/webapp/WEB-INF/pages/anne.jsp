<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact Me</title>
</head>
<body>

    <!--header-->
    <jsp:include page="../../header.jsp"></jsp:include>
    <!--contact-->
    <div class="jumbotron">
        <div class="container">
        <form role="form" class="search-form" action="anne.html" method="post">
        <table align="center" >
        <thead>
        <tr height="40" class="odd-line">
        <th colspan="4" style="padding-right: 10px;"><input type="text" class="form-control" name="phone" placeholder="输入手机号"></th>
        <th colspan="2" style="padding-right: 10px;"><input type="text" class="form-control" name="name" placeholder="输入姓名"></th>
        <th><button type="submit" class="btn btn-success">搜素</button>
        <button type="button" class="btn btn-primary pull-right" style="margin-right: 10px;" id="addCustomer">添加</button>
        </th>
        
        </tr>
        
        <tr height="35" class="even-line">
        <th width="100">
            <button type="button" class="btn btn-success" id="batchSchedule">批量预定</button>
        </th>
        <th width="120">手机号</th>
        <th width="120">姓名</th>
        <th width="120">邮件是否发送</th>
        <th width="150">预定发送时间</th>
        <th width="320">备注</th>
        <th width="209">操作</th>
        </tr>
        </thead>
        <tbody>
        
        <c:forEach items="${customerList}" var="customer" varStatus="status">
            <tr height="40" class='<c:if test="${status.count%2==0 }">even-line</c:if><c:if test="${status.count%2!=0 }">odd-line</c:if>'>
            <td>            
                <input type="checkbox" class="checkbox">
            </td>
            <td>${customer.phone}</td>
            <td>${customer.name}</td>
            <td class='<c:if test="${customer.emailState=='Y' }">green</c:if><c:if test="${customer.emailState=='N' }">red</c:if>'>
            ${customer.emailState}
            </td>
            <td>${customer.stringSendTime}</td>
            <td>${customer.comment}</td>
            <td><button type="button" class="btn btn-success schedule" value="${customer.id}">预定</button>
            <button type="button" class="btn btn-warning edit" value="${customer.id}">修改</button>
            <button type="button" class="btn btn-danger delete" value="${customer.id}">删除</button></td>
            </tr>
            </c:forEach>
        </tbody>
        </table>
            </form>
        </div>

    </div>

    <div class="container">
        <hr>
    </div>
    <!-- footer -->
    <jsp:include page="../../footer.jsp"></jsp:include>
    <script src="js/jquery-ui-1.11.1.js"></script>
    <script src="js/custom-dropdown.js"></script>
    <script src="js/custom-customer.js"></script>
</body>
</html>