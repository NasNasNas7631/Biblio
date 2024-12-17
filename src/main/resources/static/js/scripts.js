document.addEventListener("DOMContentLoaded", function() {
    // Инициализация счетчика книг
    updateBookCount();
});

function sortTable() {
    var table = document.getElementById("bookTable");
    var tbody = table.tBodies[0];
    var rows = Array.from(tbody.rows);

    // Determine current sort order
    var ascending = table.getAttribute("data-sort-asc") === "true";
    table.setAttribute("data-sort-asc", !ascending); // Toggle sort order

    rows.sort(function(a, b) {
        // Parse year values as integers from the appropriate cell index (assuming year is in the 5th column)
        var yearA = parseInt(a.cells[4].innerText); // Year is in the 5th column (index 4)
        var yearB = parseInt(b.cells[4].innerText);

        // Handle NaN cases
        if (isNaN(yearA)) return ascending ? 1 : -1; // Treat NaN as larger when sorting ascending
        if (isNaN(yearB)) return ascending ? -1 : 1; // Treat NaN as larger when sorting descending

        return ascending ? yearA - yearB : yearB - yearA; // Sort based on ascending or descending order
    });

    // Rebuild the table with sorted rows
    rows.forEach(function(row) {
        tbody.appendChild(row);
    });
}

function updateBookCount() {
    var table = document.getElementById("bookTable");
    var rowCount = table.tBodies[0].rows.length;
    document.getElementById("bookCount").innerText = rowCount;
}

