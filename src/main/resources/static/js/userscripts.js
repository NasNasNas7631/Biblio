document.addEventListener("DOMContentLoaded", function() {
    // Инициализация счетчика книг
    updateUserCount();

    // Загрузка данных для гистограммы
    loadIssueHistogram();
});

function updateUserCount() {
    var table = document.getElementById("userTable");
    var rowCount = table.tBodies[0].rows.length;
    document.getElementById("userCount").innerText = rowCount;
}

function loadIssueHistogram() {
    fetch('/ddate')
        .then(response => response.json())
        .then(data => {
            var ctx = document.getElementById('issueHistogram').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: data.dates,
                    datasets: [{
                        label: 'Количество сеансов',
                        data: data.counts,
                        backgroundColor: data.counts.map((count, index) => {
                            const hue = (index / data.counts.length) * 30 + 30;
                            return `hsl(${hue}, 55%, 75%)`;
                        }),
                        borderColor: 'black',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        x: { title: { display: true, text: 'Дата сеансов' } },
                        y: { title: { display: true, text: 'Количество фильмов'}, beginAtZero: true }
                    }
                }
            });
        })
        .catch(error => console.error('Ошибка при загрузке данных для гистограммы:', error));
}