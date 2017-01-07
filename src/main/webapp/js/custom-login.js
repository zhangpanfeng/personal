$("#signin").click(function(){
    showLogin();

    return false;
});

$("#signout").click(function(){
    logout();

    return false;
});

function showLogin(){
    var messageObj = $("#loginMessage");
    var formObj = $("#loginForm");
    
    BootstrapDialog.show({
        title: 'Login',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            $message.append(formObj);
    
            return $message;
        },
        buttons: [{
            label: 'Close',
            action: function(dialog){
                dialog.close();
            }
        }, {
            label: 'Login',
            cssClass: 'btn-success',
            action: function(dialog){
                var formData = formObj.serialize();
                login(dialog, formData, messageObj);
            }
        }],
        onhidden: function(dialog){
            MessageUtil.cleanMessage(messageObj);
            var userNameObj = $("#loginUserName");
            var passwordObj = $("#loginPassword");
            userNameObj.val("");
            passwordObj.val("");
            $("#loginDialogHidden").append(formObj);
        }
    });
}

function login(dialog, formData, messageObj){
    $.ajax({
        type : "POST",
        url : "login.html",
        data : formData,
        datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
        beforeSend : function() {
            MessageUtil.showNormalMessage(messageObj, "login ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                $("#buildLink").removeClass("hide");
                $("#signout").removeClass("hide");
                $("#signin").addClass("hide");
                $("#name").html(data.name);
                MessageUtil.showSuccessMessage(messageObj, "login success");
                dialog.close();
            }else{
                MessageUtil.showFailureMessage(messageObj, "login failure");
            }
            
        },
        error : function() {
            MessageUtil.showFailureMessage(messageObj, "login failure");
        }
    });
}

function logout(){
    $.ajax({
        type : "GET",
        url : "logout.html",
        datatype : "json",
        success : function(data) {
            if(data.result == 1){
                //success
                $("#buildLink").addClass("hide");
                
                $("#signin").removeClass("hide");
                $("#signout").addClass("hide");
                $("#name").html("");
            }else{
                alert("log out failure");
            }
        },
        error : function() {
            alert("log out failure");
        }
    });
}