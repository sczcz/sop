// JavaScript for the pop-up


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
    $("#add-transaction-button").click(function() {
        fetchGroupNames();
        fetchCategoryNames();
        console.log("Button clicked.");
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

   /* // Submit the form when it is submitted
    $("#transaction-form").submit(function(e) {
        e.preventDefault();
        // Handle the form submission as per your requirements
    });*/

    $("#transaction-form").submit(function(e) {
        e.preventDefault();

        var amount = parseFloat($("#amount").val()); // Parse the amount as an integer
        var description = $("#description").val();
        var category = parseInt($("#category-dropdown").val());
        var group = parseInt($("#group-dropdown").val());

        var transactionData = {
            amount: amount,
            description: description,
            category: category,
            group: group
        };

        saveTransaction(transactionData);
        console.log(transactionData.amount);
    });

    function saveTransaction(transactionData) {
        // Make an AJAX request to save the transaction data
        $.ajax({
            url: "/transactionsSaved",
            type: "POST",
            data: {
                amount: transactionData.amount,
                description: transactionData.description,
                "group-dropdown": transactionData.group,
                "category-dropdown": transactionData.category,
            },
            success: function(response) {
                console.log("Transaction saved successfully");
                // Perform any additional actions upon successful save
                $("#add-transaction-popup").fadeOut();
            },
            error: function() {
                console.log("Error saving transaction.");
            }
        });
    }

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

