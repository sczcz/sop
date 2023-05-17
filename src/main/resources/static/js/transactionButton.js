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
                populateCategoryDropdown(data, "#category-dropdown", "name");
            },
            error: function() {
                console.log("Error fetching groups.");
            }
        });
    }

    function fetchGroupNames() {
        $.ajax({
            url: "/groups/names",
            type: "GET",
            success: function (data) {
                populateGroupDropdown(data, "#group-dropdown", "name");
            },
            error: function () {
                console.log("Error fetching groups.");
            }
        });
    }

    function populateGroupDropdown(data, dropdownId, property) {
        var dropdown = $(dropdownId);
        dropdown.empty(); // Clear previous options
        dropdown.append($("<option disabled selected></option>").text("Grupper"));

        data.forEach(function(item) {
            var option = $("<option></option>").text(item[property]).val(item.id);
            dropdown.append(option);
        });
    }

    function populateCategoryDropdown(data, dropdownId, property) {
        var dropdown = $(dropdownId);
        dropdown.empty(); // Clear previous options
        dropdown.append($("<option disabled selected></option>").text("Kategori"));

        data.forEach(function(item) {
            var option = $("<option></option>").text(item[property]).val(item.id);
            dropdown.append(option);
        });
    }
});

