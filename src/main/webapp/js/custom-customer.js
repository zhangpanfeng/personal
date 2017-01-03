$("tbody").on("click", ".schedule", function(){
    var tds = $(this).parent().parent().children("td");
    var id = $(this).val();
    showScheduleEmaikWindow(tds, id);

    return false;
});

$("tbody").on("click", ".edit", function(){
    var tds = $(this).parent().parent().children("td");
    var id = $(this).val();
    showEditorWindow(tds, id);

    return false;
});

$("tbody").on("click", ".delete", function(){
    var tr = $(this).parent().parent();
    var id = $(this).val();
    showDeleteWindow(tr, id);

    return false;
});

$("#addCustomer").click(function(){
    var tbody = $(this).parent().parent().parent().siblings("tbody").get(0);
    showAddWindow(tbody);

    return false;
});

$("#batchSchedule").click(function(){
    var tbody = $(this).parent().parent().parent().siblings("tbody").get(0);
    showBatchScheduleWindow(tbody);

    return false;
});

$("body").on("click", ".custom-dialog .custom-close", function(){
    $(this).parent().remove();

    return false;
});

function requestCustomer(id){
    $.ajax({
        type : "POST",
        url : "getCustomer.html",
        data : "id=" + id,
        datatype : "json",
        success : function(data) {
            if(data.result == 1){
                //success
                $("#customerId").val(data.id);
                $("#customerPhone").val(data.phone);
                $("#customerName").val(data.name);
                var commentObj = $("#customerComment");
                if(commentObj){
                    commentObj.val(data.comment);
                }
                MessageUtil.cleanMessage($("#editCustomerMessage"));
            }else{
                MessageUtil.showFailureMessage($("#editCustomerMessage"), "加载错误！");
            }
            
        },
        error : function() {
            MessageUtil.showFailureMessage($("#editCustomerMessage"), "加载错误！");
        }
    });
}

function showScheduleEmaikWindow(tds, id){
    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_SUCCESS,
        title: '预定时间',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/scheduleEmailDialog.jsp'
        },
        buttons: [{
            label: '取消',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: '预定',
            cssClass: 'btn-success',
            action: function(dialog){
                updateCustomer(dialog, tds);
            }
        }],
        onshown: function(){
            requestCustomer(id);
        }
    });
}

function showEditorWindow(tds, id){
    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_WARNING,
        title: '修改客户',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/editorCustomerDialog.jsp'
        },
        buttons: [{
            label: '取消',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: '修改',
            cssClass: 'btn-warning',
            action: function(dialog){
                updateCustomer(dialog, tds);
            }
        }],
        onshown: function(){
            requestCustomer(id);
        }
    });
}

function showAddWindow(tbody){
    BootstrapDialog.show({
        title: '添加客户',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/addCustomerDialog.jsp'
        },
        buttons: [{
            label: '取消',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: '添加',
            cssClass: 'btn-primary',
            action: function(dialog){
                addCustomer(dialog, tbody);
            }
        }]
    });
}

function showBatchScheduleWindow(tbody){
   var bootstrapDialog = BootstrapDialog.show({
        type: BootstrapDialog.TYPE_SUCCESS,
        title: '批量预定',
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
            
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/batchScheduleDialog.jsp'
        },
        buttons: [{
            label: '取消',
            action: function(dialog){
                dialog.close();
            }
        }, {
            id: 'btn-batch-schedule',
            label: '预定',
            cssClass: 'btn-success',
            action: function(dialog){
                batchSchedule(dialog, tbody);
            }
        }],
        onshown: function(dialog){
            while($("body").find("#batchScheduleMessage").length == 0){
                TimerUtil.sleep(1);
            }
            var rbodyObj = $(tbody);
            var checkbox = rbodyObj.find(".checkbox:checked");
            var contentObj = $("#batchScheduleContent");
            checkbox.each(function(index, value){
                var array = value.value.split("_");
                var labelObj = $("<label class='custom-solid-block' role='"+array[0]+"'>"+array[1]+"</label>");
                var spanObj = $("<span class='custom-close'>×</span>");
                labelObj.append(spanObj);
                contentObj.append(labelObj);
            });
            
            MessageUtil.cleanMessage($("#batchScheduleMessage"));
        }
    });
}

function loadBatchSchedule(){
    
}

function showDeleteWindow(tr, id){
    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_DANGER,
        title: '删除客户',
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'dialog/deleteCustomerDialog.jsp'
        },
        buttons: [{
            label: '取消',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: '删除',
            cssClass: 'btn-danger',
            action: function(dialog){
                deleteCustomer(dialog, tr, id);
            }
        }]
    });
}

function updateCustomer(dialog, tds){
    if($("#timespinner") && $("#datepicker" )){
       var stringSendTime = $("#datepicker" ).val() + " " + $("#timespinner").val();
       $("#stringSendTime").val(stringSendTime);
    }
    
    $.ajax({
        type : "POST",
        url : "editCustomer.html",
        data : $("#editCustomerForm").serialize(),
        datatype : "json",
        beforeSend : function() {
            MessageUtil.showNormalMessage($("#editCustomerMessage"), "正在保存 ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                $($(tds.get(0)).children(".checkbox").get(0)).val(data.id + "_" + data.name);
                $(tds.get(1)).text(data.phone);
                $(tds.get(2)).text(data.name);
                var emailStateObj = $(tds.get(3));
                if(data.emailState == 'N'){
                    emailStateObj.removeClass("green");
                    emailStateObj.addClass("red");
                }else{
                    emailStateObj.removeClass("red");
                    emailStateObj.addClass("green");
                }
                emailStateObj.text(data.emailState);
                $(tds.get(4)).text(data.stringSendTime);
                $(tds.get(5)).text(data.comment);
                dialog.close();
            }else{
                MessageUtil.showFailureMessage($("#editCustomerMessage"), "保存失败！");
            }
            
        },
        error : function() {
            MessageUtil.showFailureMessage($("#editCustomerMessage"), "保存失败！");
        }
    });
}

function addCustomer(dialog, tbody){
    $.ajax({
        type : "POST",
        url : "addCustomer.html",
        data : $("#addCustomerForm").serialize(),
        datatype : "json",
        beforeSend : function() {
            MessageUtil.showNormalMessage($("#addCustomerMessage"), "正在保存 ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                var rbodyObj = $(tbody);
                var lastTrClass = rbodyObj.children("tr:last-child").attr("class");
                var trObj = $('<tr height="40">')
                if(lastTrClass == "odd-line"){
                    trObj.attr("class", "even-line");
                }else{
                    trObj.attr("class", "odd-line");
                }$(tds.get(0)).val(data.id + "_" + data.name);
                trObj.append($('<td><input type="checkbox" class="checkbox" value="' + data.id + '_' + data.name + '"></td>'));
                trObj.append($("<td>"+data.phone+"</td>"));
                trObj.append($("<td>"+data.name+"</td>"));
                if(data.emailState == 'N'){
                    trObj.append($("<td class='red'>"+data.emailState+"</td>"));
                }else{
                    trObj.append($("<td class='green'>"+data.emailState+"</td>"));
                }
                trObj.append($("<td>"+data.stringSendTime+"</td>"));
                trObj.append($("<td>"+data.comment+"</td>"));
                trObj.append($('<td><button type="button" class="btn btn-success schedule" value="'+data.id+'">预定</button> <button type="button" class="btn btn-warning edit" value="'+data.id+'">修改</button> <button type="button" class="btn btn-danger delete" value="'+data.id+'">删除</button></td>'));
                rbodyObj.append(trObj);
                dialog.close();
            }else{
                MessageUtil.showFailureMessage($("#addCustomerMessage"), "保存失败！");
            }
            
        },
        error : function() {
            MessageUtil.showFailureMessage($("#addCustomerMessage"), "保存失败！");
        }
    });
}

function deleteCustomer(dialog, tr, id){
    $.ajax({
        type : "POST",
        url : "deleteCustomer.html",
        data : "id=" + id,
        datatype : "json",
        beforeSend : function() {
            MessageUtil.showNormalMessage($("#deleteCustomerMessage"), "删除中 ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                var tbody = tr.parent();
                tr.remove();
                var trList = tbody.children();
                trList.each(function(index, value){
                    if(index % 2 == 0){
                        $(value).removeClass("even-line");
                        $(value).addClass("odd-line");
                    }else{
                        $(value).removeClass("odd-line");
                        $(value).addClass("even-line");
                    }
                })
                dialog.close();
            }else{
                MessageUtil.showFailureMessage($("#deleteCustomerMessage"), "删除失败！");
            }
            
        },
        error : function() {
            MessageUtil.showFailureMessage($("#deleteCustomerMessage"), "删除失败！");
        }
    });
}

function batchSchedule(dialog, tbody){
    var contentObj = $("#batchScheduleContent");
    var nameBlock = contentObj.find(".custom-solid-block");
    if(nameBlock.length == 0){
        MessageUtil.showFailureMessage($("#batchScheduleMessage"), "请至少选择一人");
        var actionButton = dialog.getButton("btn-batch-schedule");
        actionButton.attr("disabled", "disabled");
        
        return;
    }
    var stringId = "";
    nameBlock.each(function(index, value){
        stringId = stringId + $(value).attr("role") + ",";
    });
    stringId = stringId.substring(0, stringId.length - 1);
    $("#customerId").val(stringId);
    var stringSendTime = $("#datepicker" ).val() + " " + $("#timespinner").val();
    $("#stringSendTime").val(stringSendTime);
    $.ajax({
        type : "POST",
        url : "batchSchedule.html",
        data : $("#batchScheduleForm").serialize(),
        datatype : "json",
        beforeSend : function() {
            MessageUtil.showNormalMessage($("#batchScheduleMessage"), "预定中 ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                var length = data.customerList.length;
                for (var i = 0; i < length; i++) {
                    var customer = data.customerList[i];
                    var trObject = $("#tr_" + customer.id);
                    var tds = trObject.children("td");
                    $(tds.get(4)).text(customer.stringSendTime);
                }
                dialog.close();
            }else{
                MessageUtil.showFailureMessage($("#batchScheduleMessage"), "预定失败！");
            }
        },
        error : function() {
            MessageUtil.showFailureMessage($("#batchScheduleMessage"), "预定失败！");
        }
    });
}