$("#build").click(function() {
    var messageNode = $("#log");
    //var websocket = initWebSocket("ws://localhost:8080/personal/textserver", build, showMessage, messageNode);
    var websocket = initWebSocket("ws://panfeng.info/textserver", build, showMessage, messageNode);
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
        beforeSend : function() {
            $("#buildMessage").removeClass("success");
            $("#buildMessage").removeClass("failure");
            $("#buildMessage").addClass("normal");
            $("#buildMessage").html("building ... ");
        },
        success : function(data) {
            if (data.result == 1) {
                // success
                $("#buildMessage").removeClass("failure");
                $("#buildMessage").removeClass("normal");
                $("#buildMessage").addClass("success");
                $("#buildMessage").html("build success");
            } else {
                $("#buildMessage").removeClass("success");
                $("#buildMessage").removeClass("normal");
                $("#buildMessage").addClass("failure");
                $("#buildMessage").html("build failed");
            }

        },
        error : function() {
            $("#buildMessage").removeClass("success");
            $("#buildMessage").removeClass("normal");
            $("#buildMessage").addClass("failure");
            $("#buildMessage").html("build failed");
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
    console.log("doClose");
}

// error
function doError() {
    console.log("doError");
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
