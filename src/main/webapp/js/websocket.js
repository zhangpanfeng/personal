$("#build").click(function() {
    var messageNode = $("#log");
    var websocket = initWebSocket("ws://localhost:8080/personal/textserver", build, showMessage, messageNode);
//    if (websocket) {
//        //websocket.send("build");
//    }
});

function showMessage(data, messageNode) {
    messageNode.append("<div>" + data + "</div>");
}

function build(token) {
    $.ajax({
        type : "POST",
        url : "execShell.html",
        data : "token=" + token,
        datatype : "json",
        success : function(data) {
            data = JSON.parse(data);
            if (data.result == 1) {
                // success
                $("#signout").removeClass("hide");
                $("#signin").addClass("hide");
                $("#name").html(data.name);
                $("#loginMessage").removeClass("failure");
                $("#loginMessage").removeClass("normal");
                $("#loginMessage").addClass("success");
                $("#loginMessage").html("login success");
                dialog.close();
            } else {
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

// init WebSocket
function initWebSocket(wcUrl, tokenCallBack, messageCallBack, messageNode) {
    if (window.WebSocket) {
        var websocket = new WebSocket(encodeURI(wcUrl));
        websocket.onopen = doOpen;
        websocket.onerror = doError;
        websocket.onclose = doClose;
        websocket.onmessage = doMessage;
        websocket.tokenCallBack = tokenCallBack;
        websocket.messageCallBack = messageCallBack;
        websocket.messageNode = messageNode;
        
        return websocket;
    } else {
        console.log("Your device doesn't support WebScoket");
    }
};

// open
function doOpen() {
    console.log("doOpen");
}

// close
function doClose() {
    console.log("doOpen");
}

// error
function doError() {
    console.log("doOpen");
}

// receive message
function doMessage(message) {
    console.log("doMessage");
    if(this.tokenCallBack && message.data.startsWith("token")){
        var token = message.data.substring(message.data.indexOf(":") + 1, message.data.length);
        this.tokenCallBack(token);
    }else if(this.messageCallBack){
        this.messageCallBack(message.data, this.messageNode);
    }
}
