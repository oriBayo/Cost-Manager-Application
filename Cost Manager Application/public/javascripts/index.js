/**
 *  util for handling events
 */

$(document).ready(function () {

    // handle the find cost button
    $("#find_cost").submit(function (event) {

        // change the default behavior of the form
        event.preventDefault();

        // return array of the submitted data
        var unindexed_array = $(this).serializeArray();
        var data = {};

        // mapping each input to it's value
        $.map(unindexed_array, function (n, i) {
            data[n['name']] = n['value'];
        })

        // sending the get request
        $.ajax({
            type: 'GET',
            url: `http://localhost:9000/costs/${data.id}`,

            success: function (response) {
                showCost(response);
                setTimeout(function () {
                    window.location.href = 'http://localhost:9000';
                }, 5000);

            },
            error: function (xhr) {
                alert("Cost Not Found");
            }
        });

    });

    // handle the delete cost button
    $("#delete_cost").submit(function (event) {

        // change the default behavior of the form
        event.preventDefault();

        // return array of the submitted data
        var unindexed_array = $(this).serializeArray();
        var data = {};

        // mapping each input to it's value
        $.map(unindexed_array, function (n, i) {
            data[n['name']] = n['value'];
        })

        // sending the delete request
        $.ajax({
            type: 'DELETE',
            url: `http://localhost:9000/costs/${data.id}`,

            success: function (response) {
                var msg = "Cost Item " + response.name + " Deleted Successfully";
                alert(msg);
            },
            error: function (err) {
                alert("Cost Not Found");
            }
        });

    });

    // handle the show report button
    $("#get_report").submit(function (event) {

        // change the default behavior of the form
        event.preventDefault();

        // return array of the submitted data
        var unindexed_array = $(this).serializeArray();
        var data = {};

        // mapping each input to it's value
        $.map(unindexed_array, function (n, i) {
            data[n['name']] = n['value'];
        })

        $.ajax({
            type: 'GET',
            url: `http://localhost:9000/report`,

            success: function (response) {
                generateReport(response, data.date)
            },
            error: function (err) {
                alert("Report Error");
            }
        });

    });
});

/**
 * show find cost
 * @param response
 */
function showCost(response) {

    var cost = "<table border='1px'>" + "<tr>" +
        "<th>Name</th>" +
        "<th>Price</th>" +
        "<th>Category</th>" +
        "<th>Description</th>" +
        "<th>Date</th>" + "</tr>"
        + "<tr>" + "<td>" + response.name + "</td>" +
        "<td>" + response.price + "</td>" +
        "<td>" + response.category + "</td>" +
        "<td>" + response.description + "</td>" +
        "<td>" + response.date + "</td>" + "</tr>" +
        "</table>"

    document.writeln(cost);
}

/**
 * generate report by selected month
 * @param costs
 * @param userDate
 */
function generateReport(costs, userDate) {

    var selectedMonth = new Date(userDate).getMonth();
    var selectedYear = new Date(userDate).getFullYear();

    var report = "<table style='border-collapse: collapse; border: 1px solid black;'>" + "<tr>" +
        "<th style='border: 1px solid black; padding: 8px;'>Name</th>" +
        "<th style='border: 1px solid black; padding: 8px;'>Price</th>" +
        "<th style='border: 1px solid black; padding: 8px;'>Category</th>" +
        "<th style='border: 1px solid black; padding: 8px;'>Description</th>" +
        "<th style='border: 1px solid black; padding: 8px;'>Date</th>" + "</tr>"

    for (var i = 0; i < costs.length; i++){

        var currentDate = new Date(costs[i].date);
        if (currentDate.getMonth() === selectedMonth &&
            currentDate.getFullYear() === selectedYear) {
            report += "<tr>"
            report += "<td style='border: 1px solid black; padding: 8px;'>"+ costs[i].name +"</td>";
            report += "<td style='border: 1px solid black; padding: 8px;'>"+ costs[i].price +"</td>";
            report += "<td style='border: 1px solid black; padding: 8px;'>"+ costs[i].category +"</td>";
            report += "<td style='border: 1px solid black; padding: 8px;'>"+ costs[i].description +"</td>";
            report += "<td style='border: 1px solid black; padding: 8px;'>"+ costs[i].date +"</td>";
            report += "</tr>"
        }
    }

    report += "</table>";
    document.writeln(report);
}