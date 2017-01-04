var MessageUtil = {
    showNormalMessage : function($selector, message) {
        $selector.removeClass("success");
        $selector.removeClass("failure");
        $selector.addClass("normal");
        $selector.html(message);
    },

    showFailureMessage : function(selector, message) {
        selector.removeClass("success");
        selector.removeClass("normal");
        selector.addClass("failure");
        selector.html(message);
    },

    showSuccessMessage : function(selector, message) {
        selector.removeClass("failure");
        selector.removeClass("normal");
        selector.addClass("success");
        selector.html(message);
    },

    cleanMessage : function(selector) {
        selector.removeClass("failure");
        selector.removeClass("normal");
        selector.removeClass("success");
        selector.html("");
    }
};

var TimerUtil = {
    /**
     * synchronization sleep millisecond
     */
    syncSleep : function(millisecond) {
        var i = 0;
        var start = new Date().getTime();
        while (true) {
            var now = new Date().getTime();
            if (now >= start + millisecond) {
                break;
            }
            console.log("loop " + i);
            i++;
        }
    },

    /**
     * asynchronous sleep millisecond
     */
    asyncSleep : function(millisecond, callback, parameter) {
        setTimeout(callback(parameter), millisecond);
    }
}
