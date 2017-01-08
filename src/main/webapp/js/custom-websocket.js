var CustomWebSocket = {
    initWebSocket : function(serverUrl, messageCallBack) {
        if (window.WebSocket) {
            var websocket = new WebSocket(encodeURI(serverUrl));
            websocket.onopen = this.doOpen;
            websocket.onerror = this.doError;
            websocket.onclose = this.doClose;
            websocket.onmessage = this.doMessage;

            // bind websocket
            this.websocket = websocket;
            // set call back
            this.messageCallBack = messageCallBack;
        } else {
            console.log("Your device doesn't support WebScoket");
        }
    },

    // open
    doOpen : function() {
        console.log("doOpen");
    },

    // close
    doClose : function() {
        console.log("doClose");
    },

    // error
    doError : function() {
        console.log("doError");
    },

    // receive message
    doMessage : function(message) {
        CustomWebSocket.messageCallBack(message);
    }
}