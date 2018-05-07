$(".delete").on("click", function(){
    var id = this.id.split("_")[1];

    $.ajax({
        url: "/customers/details",
        method: "POST",
        data: {id : id},
        success: function(response){
            var object = JSON.parse(response);
            $(".modal-body").html("Are you sure you want to delete " + object.name + " (ID : " + object.id + ")?");
        },
    });

    $("#detailsBtn").hide();
    $("#deleteBtn").attr("href", "/customers/delete/" + id);
    $("#deleteBtn").show();
    $("#delete_modal").modal("show");
});

$(".details").on("click", function () {
   var id = this.id.split("_")[1];

   $.ajax({
       url: "/customers/details",
       method: "POST",
       data: {id: id},
       success: function (response) {
           var object = JSON.parse(response);
           var string = "<p>ID: " + object.id +
                        "<br/>Name: " + object.name +
                        "<br/>CPR: " + object.cpr +
                        "<br/>Phone: " + object.phone +
                        "<br/>Address: " + object.address +
                        "<br/>City: " + object.city + ", " + object.postal +
                        "<br/>Country: " + object.country +
                        "</p>";
           $("#detailsBtn").attr("href", "customers/details/" + object.id);
           $("#detailsBtn").show();
           $("#deleteBtn").hide();
           $(".modal-body").html(string);
           $("#delete_modal").modal("show");
       }
   });

});

