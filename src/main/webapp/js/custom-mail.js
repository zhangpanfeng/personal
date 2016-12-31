$("#sendButton").click(function(){
    sendMail();
    return false;
});

function sendMail() {
    $.ajax({
        type : "POST",
        url : "sendmail.html",
        data : $("#mailForm").serialize(),
        datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
        beforeSend : function() {
            $("#contactMessage").removeClass("success");
            $("#contactMessage").removeClass("failure");
            $("#contactMessage").addClass("normal");
            $("#contactMessage").html("You email are sending ... ");
        },
        success : function(data) {
            if(data.result == 1){
                $("#contactMessage").removeClass("normal");
                $("#contactMessage").removeClass("failure");
                $("#contactMessage").addClass("success");
                $("#contactMessage").html("success");
            }else{
                $("#contactMessage").removeClass("normal");
                $("#contactMessage").removeClass("success");
                $("#contactMessage").addClass("failure");
                $("#contactMessage").html("failure");
            }
        },
        error : function() {
            $("#contactMessage").removeClass("normal");
            $("#contactMessage").removeClass("success");
            $("#contactMessage").addClass("failure");
            $("#contactMessage").html("failure");
        }
    });

}