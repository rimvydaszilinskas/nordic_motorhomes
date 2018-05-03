$(".delete").on("click", function(){
    var id = this.id.split("_")[1];

    $.ajax({
        url: "/motorhouse/getDetails",
        method: "POST",
        data: {id : id},
        success: function(response){
            var object = JSON.parse(response);
            $(".modal-body").html("Are you sure you want to delete " + object.model + " (ID : " + object.id + ")?");
        },
    });

    $("#deleteBtn").attr("href", "/delete/" + id);

    $("#delete_modal").modal("show");
});

$(".details").on("click", function () {
   var id = this.id.split("_")[1];

});

