//init WebSocket
function initWebSocket(wcUrl) {
    if (window.WebSocket) {
        websocket = new WebSocket(encodeURI(wcUrl));
        websocket.onopen = doOpen;
        websocket.onerror = doError;
        websocket.onclose = doClose;
        websocket.onmessage = doMessage;
    } else {
        console.log("Your device doesn't support WebScoket");
    }
};

//open
function doOpen(){
    console.log("doOpen");
}

//close
function doClose(){
    console.log("doOpen");
}

//error
function doError() {
    console.log("doOpen");
}

//receive message
function doMessage(message){
    console.log("doMessage");
    console.log(message);
}

