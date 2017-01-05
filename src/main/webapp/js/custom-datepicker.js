var defaultDate = Globalize.format(new Date(), "yyyy-MM-dd");
$("#batchScheduleDatepicker").datepicker({
    dateFormat : "yy-mm-dd",
});

$("#scheduleDatepicker").datepicker({
    dateFormat : "yy-mm-dd",
});

$("#batchScheduleDatepicker").val(defaultDate);
$("#scheduleDatepicker").val(defaultDate);