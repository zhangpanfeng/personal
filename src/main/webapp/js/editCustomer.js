$(".book").click(function(){
    var phone = $($(this).parent().parent().children("td").get(0)).text();
    requestData(phone);
    showEditor();

    return false;
});
function requestData(phone){
    $.ajax({
        type : "POST",
        url : "getCustomer.html",
        data : "phone=" + phone,
        datatype : "json",
        success : function(data) {
            data = JSON.parse(data);
            if(data.result == 1){
                //success
                $("#customerPhone").val(data.phone);
                $("#customerName").val(data.name);
                $("#editCustomerMessage").removeClass("failure");
                $("#editCustomerMessage").removeClass("normal");
                $("#editCustomerMessage").removeClass("success");
                $("#editCustomerMessage").html("");
            }else{
                $("#editCustomerMessage").removeClass("success");
                $("#editCustomerMessage").removeClass("normal");
                $("#editCustomerMessage").addClass("failure");
                $("#editCustomerMessage").html("加载错误！");
            }
            
        },
        error : function() {
            $("#editCustomerMessage").removeClass("success");
            $("#editCustomerMessage").removeClass("normal");
            $("#editCustomerMessage").addClass("failure");
            $("#editCustomerMessage").html("加载错误！");
        }
    });
}

function showEditor(){
    BootstrapDialog.show({
        title: '预定时间',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/bookEmail.jsp'
        },
        buttons: [{
            label: '关闭',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: '预定',
            cssClass: 'btn-success',
            action: function(dialog){
                submit(dialog);
            }
        }]
    });
}

function submit(dialog){
    var value = $("#timespinner").val();
    alert(value);
    var currentDate = $("#datepicker" ).val();
    alert(currentDate);
}