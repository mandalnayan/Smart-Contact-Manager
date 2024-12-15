
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