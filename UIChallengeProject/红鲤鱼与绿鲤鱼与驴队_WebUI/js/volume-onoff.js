$sound = $("#sound");
audioContext = new AudioContext();
audioContext.suspend();
$sound.on("click", function(){
    const state = $sound.attr("state") === "on" ? "off": "on";
    $sound.attr("state", state);
    if (state === "off"){
        $sound.removeClass("btn-success").addClass("btn-danger");
        $sound.html(`<span class="glyphicon glyphicon-volume-off" aria-hidden="true"></span>`);
        audioContext.suspend();
    }
    else{
        $sound.removeClass("btn-danger").addClass("btn-success");
        $sound.html(`<span class="glyphicon glyphicon-volume-up" aria-hidden="true"></span>`);
        audioContext.resume();
    }
});

$("#addSelf").on("click", addOneVideoContainer);

let cname = "channel123";
$("#go").click(function(){
    cname = $("#cname").val();
    main();
    $("#go").attr("disabled", "disabled");
})
