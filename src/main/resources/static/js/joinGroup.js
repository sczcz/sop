$(document).ready(function() {
    var csrfToken = $("meta[name='_csrf']").attr("content");
    console.log(csrfToken);

    // Include the CSRF token in your AJAX requests
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
        }
    });

    // Open the pop-up when the button is clicked
    $("#add-group-button").click(function() {
        console.log("Button clicked.");
        $("#add-group-popup").fadeIn();
    });

    // Close the pop-up when the close button is clicked
    $(".close-button").click(function() {
        $("#add-group-popup").fadeOut();
    });

    // Close the pop-up when the escape key is pressed
    $(document).keyup(function(e) {
        if (e.key === "Escape") {
            $("#add-group-popup").fadeOut();
        }
    });

    $("#group-form").submit(function(e) {
        e.preventDefault();

        var groupId = parseInt($("#groupId").val()); // Parse the amount as an integer

        var group = {
            groupId: groupId
        };

        saveGroup(group);
        console.log(group.groupId);
    });

    function saveGroup(group) {
        // Make an AJAX request to save the transaction data
        $.ajax({
            url: "/joinGroup",
            type: "POST",
            data: {
                groupId: group.groupId
            },
            success: function(response) {
                console.log("Group joined succesfully!");
                // Perform any additional actions upon successful save
                $("#add-group-popup").fadeOut();
            },
            error: function() {
                console.log("Error joining group. Group does not exist or you are already in the group");
            }
        });
    }
});
