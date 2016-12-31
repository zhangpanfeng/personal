<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="editCustomerForm" role="form">
    <div class="form-group">
        <label for="name">手机号</label> <input type="text" id="customerPhone" name="phone" class="form-control">
    </div>
    <div class="form-group">
        <label for="name">姓名</label> <input type="text" id="customerName" name="name" class="form-control">
    </div>
    <div class="form-group">
        <label for="name">备注</label>
        <textarea rows="5" cols="" id="customerComment" name="comment" class="form-control"></textarea>
    </div>
    <div class="form-group">
        <input type="hidden" id="customerId" name="id">
    </div>
    <div id="editCustomerMessage" class="col-center-block message normal">加载中...</div>
</form>