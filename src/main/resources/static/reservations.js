$(".delete").on("click", function(){
    var id = this.id.split("_");

    $.ajax({
       method: "POST",
       url: "/reservation/delete",
       data: {id : id[1]},
       success: function(response){
           console.log("ok");
           var object = JSON.parse(response);
           $(".modal-body").html("Are you sure you want to delete reservation(ID: " + object.id + ") " + object.dateFrom + " - " + object.dateTo + "?");
       }
    });

    $("#deleteBtn").attr("href", "/reservation/delete/" + id[1]).show();
    $("#delete_modal").modal("show");
});

$(".motorhouseDetails").on("click", function(){
    var id = this.id.split("_")[1];

    $.ajax({
        method: "POST",
        url: "/motorhouse/details/",
        data: {id : id},
        success : function (reponse) {
            console.log("ok");
            var object = JSON.parse(reponse);
            $(".modal-body").html("<p>" +
                "<b>Model: </b>" + object.manufacturer + " " + object.model +
                "<br/><b>Year: </b>" + object.year +
                "<br/><b>Engine: </b>" + object.power + " kW. (" + object.gearbox + ")" +
                "<br/><b>Transmission: </b>" + object.transmission +
                "<br><b>Seats: </b>" + object.seats +
                "<br/><b>Beds: </b>" + object.beds +
                // "<br><b>Last reservation: </b>" + object.lastFrom + " - " + object.lastTo +
                "</p>")
        }
    });

    $("#delete_modal").modal("show");
});