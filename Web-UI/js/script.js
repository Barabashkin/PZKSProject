/**
 * Created by a.slepakurov on 13.10.2014.
 */
$(document).ready(function () {
    $("#calculate").on("click", function () {
        var expression = $("#expression").val().toString();
        var variables = $("#variables").val().toString();
        if (expression == "") {
            swal({
                title: "Validation error!",
                crossDomain: true,
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Fix"
            });
        } else {
            var jsonRequest = "{\"request\":{\"expression\":" + expression + ((variables == "") ? "" : (",\"variables\":" + variables)) + "}}";
            $.ajax({
                type: "POST",
                url: "http://" + window.location.host + ":8881/kpi/pzks/calculate",
                data: jsonRequest,
                dataType: "json",
                crossDomain: true,
                complete: function (data) {
                    if (data.responseJSON.code != "0") {
                        swal({
                            title: "Expression error!",
                            text: data.responseJSON.exception,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Ok =("
                        });
                    } else {
                        swal(data.responseJSON.result);
                    }
                }
            });
        }
    });
});