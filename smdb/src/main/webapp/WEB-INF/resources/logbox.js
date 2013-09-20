$(document).ready(function(){
    $("#login-link").click(function(){
        $("#login-panel").slideToggle(200);
    })
})
$(document).keydown(function(e) {
    if (e.keyCode == 27) {
        $("#login-panel").slideToggle(200);
    }
});