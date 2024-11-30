function home_main() {
    initializeChart();
}

function initializeChart() {
    const countServiceRastBrak = $(".container.main-body").data("countservicerastbrak");
    const countServiceBrak = $(".container.main-body").data("countservicebrak");
    new Chart($("#chartjs-bar"), {
        type: "bar",
        data: {
            labels: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            datasets: [{
                label: "Количество браков",
                backgroundColor: window.theme.primary,
                borderColor: window.theme.primary,
                hoverBackgroundColor: window.theme.primary,
                hoverBorderColor: window.theme.primary,
                data: countServiceBrak,
                barPercentage: .75,
                categoryPercentage: .5
            }, {
                label: "Количество разводов",
                backgroundColor: "#dee2e6",
                borderColor: "#dee2e6",
                hoverBackgroundColor: "#dee2e6",
                hoverBorderColor: "#dee2e6",
                data: countServiceRastBrak,
                barPercentage: .75,
                categoryPercentage: .5
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    gridLines: {
                        display: false
                    },
                    stacked: false
                }],
                xAxes: [{
                    stacked: false,
                    gridLines: {
                        color: "transparent"
                    }
                }]
            }
        }
    });
};

home_main();