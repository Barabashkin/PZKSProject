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
            var jsonRequest = "{\"request\":{\"expression\":\"" + expression + ((variables == "\"") ? "" : ("\",\"variables\":\"" + variables + "\"")) + "}}";
            $.ajax({
                type: "POST",
                url: "http://" + window.location.host + ":8881/kpi/pzks/calculate",
                data: jsonRequest,
                dataType: "json",
                crossDomain: true,
                complete: function (data) {
                    if (data.responseJSON.code == "0") {
                        $("#expression").css("border-color", "white");
                        $("#variables").css("border-color", "white");
                        $("#title").html("Result:");
                        $("#result").html("");
                        $("#result").append("<p>" + data.responseJSON.result + "</p>");
                        $("#title1").html("Tree:");
                        $("#result1").html("");
                        $("#result1").append("<p>" + window.btoa(data.responseJSON.tree) + "</p>");

                    }
                    if (data.responseJSON.code == "1") {
                        $("#title").html("");
                        $("#result").html("");
                        $("#title1").html("");
                        $("#result1").html("");
                        $("#expression").css("border-color", "red");
                        $("#variables").css("border-color", "white");
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
                        $("#title1").html("");
                        $("#result1").html("");
                        $("#expression").css("border-color", "white");
                        $("#variables").css("border-color", "red")
                        swal({
                            title: "Variable error!",
                            text: data.responseJSON.exception,
                            type: "error",
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Ok =("
                        });
                    }
                    if (data.responseJSON.code == "3") {
                        $("#expression").css("border-color", "white");
                        $("#variables").css("border-color", "white");
                        $("#title").html("Exception:");
                        $("#title1").html("");
                        $("#result1").html("");
                        var exceptions = data.responseJSON.exception.split(";");
                        exceptions.forEach(function (exception) {
                            $("#result").append("<p>" + exception + "</p>");
                        });
                    }
                    if (data.responseJSON.code == "4") {
                        $("#expression").css("border-color", "white");
                        $("#variables").css("border-color", "white");
                        $("#title").html("Exception:");
                        $("#result").html("");
                        $("#result").append("<p>" + data.responseJSON.exception + "</p>");
                        $("#title1").html("");
                        $("#result1").html("");
                    }
                }
            });
        }
    });
});