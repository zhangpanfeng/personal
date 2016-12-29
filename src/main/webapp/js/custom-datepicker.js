$("#datepicker").datepicker({
    dateFormat : "yy-mm-dd",
});

var defaultDate = Globalize.format(new Date(), "yyyy-MM-dd");
$("#datepicker").val(defaultDate);