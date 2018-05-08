$(".delete").on("click", function(){
    var id = this.id.split("_");
    console.log(id[1]);
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