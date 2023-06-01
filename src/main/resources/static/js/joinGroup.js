$(document).ready(function() {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    console.log(csrfToken);


    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
        }
    });


    $("#add-group-button").click(function() {
        console.log("Button clicked.");
        $("#add-group-popup").fadeIn();
    });


    $(".close-button").click(function() {
        $("#add-group-popup").fadeOut();
    });


    $(document).keyup(function(e) {
        if (e.key === "Escape") {
            $("#add-group-popup").fadeOut();
        }
    });

    $("#group-form").submit(function(e) {
        e.preventDefault();

        var groupId = parseInt($("#groupId").val());

        var group = {
            groupId: groupId
        };

        saveGroup(group);
        console.log(group.groupId);
    });

    function saveGroup(group) {

        $.ajax({
            url: "/joinGroup",
            type: "POST",
            data: {
                groupId: group.groupId
            },
            success: function(response) {
                console.log("Group joined succesfully!");

                $("#add-group-popup").fadeOut();
                location.reload();
            },
            error: function() {
                console.log("Error joining group. Group does not exist or you are already in the group");
            }
        });
    }
});
