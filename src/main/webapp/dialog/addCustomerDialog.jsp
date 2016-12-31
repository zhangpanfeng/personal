<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="addCustomerForm" role="form">
    <div class="form-group">
        <label for="name">手机号</label> <input type="text" name="phone" class="form-control">
    </div>
    <div class="form-group">
        <label for="name">姓名</label> <input type="text" name="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="name">备注</label>
        <textarea rows="5" cols="" name="comment" class="form-control"></textarea>
    </div>
    <div id="addCustomerMessage" class="col-center-block message"></div>
</form>