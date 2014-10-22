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
                crossDomain:true,
                type: "error",
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Fix"
            });
        } else {
//            var jsonRequest = "{\"request\":{\"expression\":"+expression+(variables=="")?"":(",\"variables\":"+variables)+"}}";
//            var jsonRequest = "{\"request\":{expression:"+expression+"}}";
            $.ajax({
                type: "POST",
//                url: "http://vm-siuesb-d1-smx:8881/kpi/pzks/calculate",
                url: "http://barabashkastuff.com:8881/kpi/pzks/calculate",
//                data: JSON.stringify(jsonRequest),
                data: "{\"request\":{expression:"+expression+"}}",
                dataType: "json",
                crossDomain: true,
                success: function (data) {
//                    swal(data);
                    console.log(data);
                }
            });
        }
    });
});