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
