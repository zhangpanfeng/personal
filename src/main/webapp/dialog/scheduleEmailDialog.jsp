<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<form id="editCustomerForm" role="form">
    <div class="form-group">
        <label for="name">手机号</label> <input type="text" id="customerPhone" disabled="disabled" class="form-control">
    </div>
    <div class="form-group">
        <label for="name">姓名</label> <input type="text" id="customerName" disabled="disabled" class="form-control">
    </div>
    <div class="form-group pull-left">
        <label for="name">日期</label>
        <input id="datepicker" class="custom-input"></input>
    </div>
    <div class="form-group pull-right" style="text-align: left;">
        <label for="name">时间</label>
        <input id="timespinner" class="times-pinner" name="timespinner" value="08:30">
    </div>
    <div class="form-group">
        <input type="hidden" id="customerId" name="id">
        <input type="hidden" id="stringSendTime" name="stringSendTime">
        <input type="hidden" name="emailState" value="Y">
    </div>
    <div id="editCustomerMessage" class="col-center-block message clear normal">加载中...</div>
</form>
<script src="js/globalize.js" type="text/javascript"></script>
<script src="js/globalize.culture.de-DE.js" type="text/javascript"></script>
<script src="js/jquery.mousewheel.js" type="text/javascript"></script>
<script src="js/custom-datepicker.js" type="text/javascript"></script>
<script src="js/custom-spinner.js" type="text/javascript"></script>