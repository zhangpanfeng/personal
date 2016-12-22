$("#build").click(function() {
    var websocket = initWebSocket("ws://localhost:8080/personal/textserver", cc);
    if (websocket) {
        //websocket.send("build");
    }
});

function cc(xx) {
    console.log(xx);
    var log = $("#log");
    log.appendChild("<span>" + xx + "</span>");
}

function build(token) {
    $.ajax({
        type : "POST",
        url : "execShell.html",
        data : token,
        datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
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
function initWebSocket(wcUrl, callBack) {
    if (window.WebSocket) {
        var websocket = new WebSocket(encodeURI(wcUrl));
        websocket.onopen = doOpen;
        websocket.onerror = doError;
        websocket.onclose = doClose;
        websocket.onmessage = doMessage;
        websocket.callBack = callBack;
        return websocket;
    } else {
        console.log("Your device doesn't support WebScoket");
    }
};

// open
function doOpen() {
    console.log("doOpen");
    this.send("build");
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
    console.log(message.data);
   // this.callBack("ss");
    var log = $("#log");
    
    log.append($("<div>" + message.data + "</div>"));
    // callBack(message);
}
