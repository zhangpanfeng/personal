$.widget("ui.timespinner", $.ui.spinner, {
    options : {
        // seconds
        step : 30* 60 * 1000,
        // hours
        page : 60,
        culture: "de"
    },

    _parse : function(value) {
        if (typeof value === "string") {
            // already a timestamp
            if (Number(value) == value) {
                return Number(value);
            }
            return +Globalize.parseDate(value);
        }
        return value;
    },

    _format : function(value) {
        return Globalize.format(new Date(value), "t");
    }
});
Globalize.culture("de-DE");
$("#batchScheduleTimespinner").timespinner();
$("#scheduleTimespinner").timespinner();
