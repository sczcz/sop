// JavaScript for the pop-up
$(document).ready(function() {
    // Open the pop-up when the button is clicked
    $("#add-transaction-button").click(function() {
        fetchGroupNames();
        fetchCategoryNames();
        $("#add-transaction-popup").fadeIn();
    });

    // Close the pop-up when the close button is clicked
    $(".close-button").click(function() {
        $("#add-transaction-popup").fadeOut();
    });

    // Close the pop-up when the escape key is pressed
    $(document).keyup(function(e) {
        if (e.key === "Escape") {
            $("#add-transaction-popup").fadeOut();
        }
    });

    // Submit the form when it is submitted
    $("#transaction-form").submit(function(e) {
        e.preventDefault();
        // Handle the form submission as per your requirements
    });

    function fetchCategoryNames() {
        $.ajax({
            url: "/categories/names",
            type: "GET",
            success: function(data) {
                populateCategoryDropdown(data);
            },
            error: function() {
                console.log("Error fetching category names.");
            }
        });
    }

    function fetchGroupNames() {
        $.ajax({
            url: "/groups/names",
            type: "GET",
            success: function(data) {
                populateGroupDropdown(data);
            },
            error: function() {
                console.log("Error fetching category names.");
            }
        });
    }

    function populateCategoryDropdown(names) {
        var dropdown = $("#category-dropdown");
        dropdown.empty(); // Clear previous options

        names.forEach(function(name) {
            dropdown.append($("<option></option>").text(name));
        });
    }

    function populateGroupDropdown(names) {
        var dropdown = $("#group-dropdown");
        dropdown.empty(); // Clear previous options

        names.forEach(function(name) {
            dropdown.append($("<option></option>").text(name));
        });
    }
});

