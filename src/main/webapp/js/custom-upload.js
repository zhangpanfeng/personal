$("#uploadFile").click(function(){
    var filePath = $("#file").val();
    var uploadMessage = $("#uploadMessage");
    if(filePath.length == 0){
        MessageUtil.showNormalMessage(uploadMessage, "Please choice file to upload!");
    }
    if(!filePath.toLowerCase().endsWith(".xlsx")){
        MessageUtil.showFailureMessage(uploadMessage, "Please choice '.xlsx' file to upload!");
    }
});

$("#file").change(function(){
    var filePath = $("#file").val();
    var fileNameObj = $("#fileName");
    fileNameObj.val(filePath);
});