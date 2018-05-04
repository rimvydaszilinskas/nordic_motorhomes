$(".delete").on("click", function(){
    var id = this.id.split("_")[1];

    $.ajax({
        url: "/motorhouse/delete/details",
        method: "POST",
        data: {id : id},
        success: function(response){
            var object = JSON.parse(response);
            $(".modal-body").html("Are you sure you want to delete " + object.model + " (ID : " + object.id + ")?");
        },
    });

    $("#deleteBtn").attr("href", "/motorhouse/delete/" + id);
    $("#deleteBtn").show();
    $("#delete_modal").modal("show");
});

$(".details").on("click", function () {
   var id = this.id.split("_")[1];

   $.ajax({
       url: "/motorhouse/details",
       method: "POST",
       data: {id: id},
       success: function (response) {
           var object = JSON.parse(response);
           var string = "<p>Manufacturer: " + object.manufacturer +
                        "<br/>Model: " + object.model +
                        "<br/>Year: " + object.year +
                        "<br/>Seats: " + object.seats +
                        "<br/>Gearbox: " + object.gearbox +
                        "<br/>Beds: " + object.bed_count +
                        "<br/>Transmission: " + object.transmission +
                        "<br/>Mileage: " + object.mileage + " km."+
                        "<br/>Power: " + object.power +
                        "kW.</p>";
           $("#deleteBtn").hide();
           $(".modal-body").html(string);
           $("#delete_modal").modal("show");
       }
   });

});

