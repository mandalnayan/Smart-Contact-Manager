
const toggleSidebar = () => {
    if($(".sidebar").is(":visible")) {
        $(".sidebar").css("display", "none");
        $("#AdmContent").css("margin-left", "0%");
        $(".overlay").css("display", "none");
        $(".hmbrg").css("display", "block");  
        $("#AdmContent").removeClass("blurred");
    } else {
        $(".sidebar").css("display", "block");
        $("#AdmContent").css("margin-left", "18%");        
        $(".overlay").css("display", "block");
        $(".hmbrg").css("display", "none");  
        $("#AdmContent").addClass("blurred");
    }
};


// alert box
function deleteContact(cId, curPage) {
Swal.fire({
    title: "Are you sure?",
    text: "You want to delete this contact!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Yes, delete it!"
  }).then((result) => {
    if (result.isConfirmed) {
    //   Swal.fire({
    //     title: "Deleted!",
    //     text: "Your contact has been deleted.",
    //     icon: "success"
    //   });
      window.location = "/user/" + cId + "/" + curPage + "/delete";
    } else {
        // Swal("Your contact is safe !");
        Swal.fire({
            text:"Your contact is safe!"
        });
    }
  });
}

// Search-contact script
const search=()=> {
  console.log(jQuery.fn.jquery);
  let query = $("#search-input").val();
  console.log("Query ", query);
  console.log("Query length ", $("#search-input").length);

  if (query == "") {
     $(".search-result").hide();
  } else {
    let url = `http://localhost:8282/search/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        // convert into html
        let text = `<div class='list-group'>`;
        data.forEach((contact) => {
          text += `<a href='/user/${contact.cId}/0/contact' class='list-group-item list-group-item-action'> ${contact.name} </a>`
        });
        text += `</div>`;
        
        console.log(text);
        $(".search-result").html(text);
        $(".search-result").show();
      })



  }
}


