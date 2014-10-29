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
            var jsonRequest = "{\"request\":{\"expression\":\"" + expression + ((variables == "\"") ? "" : ("\",\"variables\":\"" + variables+"\"")) + "}}";
            $.ajax({
                type: "POST",
                url: "http://" + window.location.host + ":8881/kpi/pzks/calculate",
                data: jsonRequest,
                dataType: "json",
                crossDomain: true,
                complete: function (data) {
                    if (data.responseJSON.code == "0") {
                        $("#title").html("Result:");
                        $("#result").html(data.responseJSON.result);
                    }
                    if (data.responseJSON.code == "1") {
                        $("#title").html("");
                        $("#result").html("");
//                        $("#expression").style.borderColor("red");
                        swal({
                            title: "Expression error!",
                            text: data.responseJSON.exception,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Ok =("
                        });
                    }
                    if (data.responseJSON.code == "2") {
                        $("#title").html("");
                        $("#result").html("");
//                        $("#variables").style.borderColor("red");
                        swal({
                            title: "Varriable error!",
                            text: data.responseJSON.exception,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Ok =("
                        });
                    }
                    if (data.responseJSON.code == "3") {
                        $("#title").html("Exception:");
                        $("#result").html(data.responseJSON.exception);
                    }
                    if (data.responseJSON.code == "4") {
                        $("#title").html("Exception:");
                        $("#result").html(data.responseJSON.exception);
                    }
                }
            });
        }
    });
});