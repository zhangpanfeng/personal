$(document).ready(function() {
    CustomWebSocket.initWebSocket("ws://panfeng.info/uploadserver", changeProgressbar);
    //CustomWebSocket.initWebSocket("ws://localhost:8080/personal/uploadserver", changeProgressbar);
    var progressbar = $("#progressbar");
    var i = 0;
    function changeProgressbar(value) {
        if (i % 5 == 0 || parseInt(value.data) == 100) {
            progressbar.width(value.data + "%");
        }

        if (parseInt(value.data) > 50) {
            progressbar.css("color", "#FFFFFF");
        }
        progressbar.text(value.data + "%");
        i++;
    }
});

$("#uploadFile").click(function() {
    var filePath = $("#file").val();
    var uploadMessage = $("#uploadMessage");
    if (filePath.length == 0) {
        MessageUtil.showNormalMessage(uploadMessage, "Please choice file to upload!");
    }
    if (!filePath.toLowerCase().endsWith(".xlsx")) {
        MessageUtil.showFailureMessage(uploadMessage, "Please choice '.xlsx' file to upload!");
    }
});

$("#file").change(function() {
    var filePath = $("#file").val();
    var fileNameObj = $("#fileName");
    fileNameObj.val(filePath);
});
