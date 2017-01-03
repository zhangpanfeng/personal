<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="batchScheduleForm" role="form" class="custom-dialog">
    <div class="form-group" id="batchScheduleContent"></div>
    <div class="form-group pull-left">
        <label for="name">日期</label> <input id="datepicker" class="custom-input"></input>
    </div>
    <div class="form-group pull-right" style="text-align: left;">
        <label for="name">时间</label>
        <input id="timespinner" class="times-pinner" name="timespinner" value="08:30">
    </div>
    <div class="form-group">
        <input type="hidden" id="customerId" name="stringId">
        <input type="hidden" id="stringSendTime" name="stringSendTime">
    </div>
    <div id="batchScheduleMessage" class="col-center-block message clear normal">加载中...</div>
</form>
<script src="js/custom-datepicker.js" type="text/javascript"></script>
<script src="js/custom-spinner.js" type="text/javascript"></script>