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
        <table>
        <thead>
        <tr height="40" class="odd-line">
        <th colspan="4" style="padding-right: 10px;"><input type="text" class="form-control" name="phone" placeholder="输入手机号"></th>
        <th colspan="2" style="padding-right: 10px;"><input type="text" class="form-control" name="name" placeholder="输入姓名"></th>
        <th><button type="submit" class="btn btn-success">搜素</button>
        <button type="button" class="btn btn-warning" id="export">导出</button>
        <button type="button" class="btn btn-primary" id="addCustomer">添加</button>
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
            <tr height="40" id="tr_${customer.id}" class='<c:if test="${status.count%2==0 }">even-line</c:if><c:if test="${status.count%2!=0 }">odd-line</c:if>'>
            <td>            
                <input type="checkbox" class="checkbox" value="${customer.id}_${customer.name}">
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
    <div class="dialog-hidden" id="dialogHidden">
        <!-- batch schedule form -->
        <form id="batchScheduleForm" role="form" class="custom-dialog">
            <div class="form-group" id="batchScheduleContent"></div>
            <div class="form-group pull-left">
                <label for="name">日期</label> 
                <input id="batchScheduleDatepicker" class="custom-input"></input>
            </div>
            <div class="form-group pull-right" style="text-align: left;">
                <label for="name">时间</label>
                <input id="batchScheduleTimespinner" class="times-pinner" value="08:30">
            </div>
            <div class="form-group">
                <input type="hidden" id="batchScheduleCustomerId" name="stringId">
                <input type="hidden" id="batchScheduleStringSendTime" name="stringSendTime">
            </div>
            <div id="batchScheduleMessage" class="col-center-block message clear normal">加载中...</div>
        </form>
        
        <!-- schedule form -->
        <form id="scheduleCustomerForm" role="form">
            <div class="form-group">
                <label for="name">手机号</label> 
                <input type="text" id="scheduleCustomerPhone" disabled="disabled" class="form-control">
            </div>
            <div class="form-group">
                <label for="name">姓名</label> 
                <input type="text" id="scheduleCustomerName" disabled="disabled" class="form-control">
            </div>
            <div class="form-group pull-left">
                <label for="name">日期</label>
                <input id="scheduleDatepicker" class="custom-input"></input>
            </div>
            <div class="form-group pull-right" style="text-align: left;">
                <label for="name">时间</label>
                <input id="scheduleTimespinner" class="times-pinner" value="08:30">
            </div>
            <div class="form-group">
                <input type="hidden" id="scheduleCustomerId" name="id">
                <input type="hidden" id="scheduleStringSendTime" name="stringSendTime">
            </div>
            <div id="scheduleCustomerMessage" class="col-center-block message clear normal">加载中...</div>
        </form>
        
        <!-- edit form -->
        <form id="editCustomerForm" role="form">
            <div class="form-group">
                <label for="name">手机号</label> 
                <input type="text" id="editCustomerPhone" name="phone" class="form-control">
            </div>
            <div class="form-group">
                <label for="name">姓名</label> 
                <input type="text" id="editCustomerName" name="name" class="form-control">
            </div>
            <div class="form-group">
                <label for="name">备注</label>
                <textarea rows="5" cols="" id="editCustomerComment" name="comment" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <input type="hidden" id="editCustomerId" name="id">
            </div>
            <div id="editCustomerMessage" class="col-center-block message normal">加载中...</div>
        </form>
        
        <!-- add from -->
        <form id="addCustomerForm" role="form">
            <div class="form-group">
                <label for="name">手机号</label> 
                <input type="text" name="phone" id="addCustomerPhone" class="form-control">
            </div>
            <div class="form-group">
                <label for="name">姓名</label> 
                <input type="text" name="name" id="addCustomerName" class="form-control">
            </div>
            <div class="form-group">
                <label for="name">备注</label>
                <textarea rows="5" cols="" name="comment" id="addCustomerComment" class="form-control"></textarea>
            </div>
            <div id="addCustomerMessage" class="col-center-block message"></div>
        </form>
        
        <!-- delete from -->
        <form id="deleteCustomerForm" role="form">
            <div style="font-weight: bold; padding-bottom: 20px">你确定要删除这条记录吗？</div>
            <div id="deleteCustomerMessage" class="col-center-block message"></div>
        </form>
    </div>
    
    
    <!-- footer -->
    <jsp:include page="../../footer.jsp"></jsp:include>
    <script src="js/custom-dropdown.js"></script>
    <script src="js/custom-customer.js"></script>
    <script src="js/custom-datepicker.js" type="text/javascript"></script>
    <script src="js/custom-spinner.js" type="text/javascript"></script>
</body>
</html>
