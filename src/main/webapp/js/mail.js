$("#sendButton").click(function(){
    sendMail();
    return false;
});

function sendMail() {
    $.ajax({
        type : "POST",
        url : "sendmail.do",
        data : $("#mailForm").serialize(),
        datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
        beforeSend : function() {
            $("#message").removeClass("success");
            $("#message").removeClass("failure");
            $("#message").addClass("normal");
            $("#message").html("You email are sending ... ");
        },
        success : function(data) {
            $("#message").removeClass("normal");
            $("#message").removeClass("failure");
            $("#message").addClass("success");
            $("#message").html(data);
        },
        error : function() {
            $("#message").removeClass("normal");
            $("#message").removeClass("success");
            $("#message").addClass("failure");
            $("#message").html("error");
        }
    });

}