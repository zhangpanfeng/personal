$("#signin").click(function(){
    showLogin();

    return false;
});

$("#signout").click(function(){
    logout();

    return false;
});

function showLogin(){
    BootstrapDialog.show({
        title: 'Login',
        //draggable: true,
        message: function(dialog) {
            var $message = $('<div></div>');
            var pageToLoad = dialog.getData('pageToLoad');
            $message.load(pageToLoad);
    
            return $message;
        },
        data: {
            'pageToLoad': 'login.jsp'
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
                login(dialog);
            }
        }]
    });
}

function login(dialog){
    $.ajax({
        type : "POST",
        url : "login.html",
        data : $("#loginForm").serialize(),
        datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
        beforeSend : function() {
            $("#loginMessage").removeClass("success");
            $("#loginMessage").removeClass("failure");
            $("#loginMessage").addClass("normal");
            $("#loginMessage").html("login ... ");
        },
        success : function(data) {
            if(data.result == 1){
                //success
                $("#buildLink").removeClass("hide");
                
                $("#signout").removeClass("hide");
                $("#signin").addClass("hide");
                $("#name").html(data.name);
                $("#loginMessage").removeClass("failure");
                $("#loginMessage").removeClass("normal");
                $("#loginMessage").addClass("success");
                $("#loginMessage").html("login success");
                dialog.close();
            }else{
                $("#loginMessage").removeClass("success");
                $("#loginMessage").removeClass("normal");
                $("#loginMessage").addClass("failure");
                $("#loginMessage").html("login failure");
            }
            
        },
        error : function() {
            $("#loginMessage").removeClass("success");
            $("#loginMessage").removeClass("normal");
            $("#loginMessage").addClass("failure");
            $("#loginMessage").html("login failure");
        }
    });
}

function logout(){
    $.ajax({
        type : "GET",
        url : "logout.html",
        datatype : "json",
        success : function(data) {
            data = JSON.parse(data);
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