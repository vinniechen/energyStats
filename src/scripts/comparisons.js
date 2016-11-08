var dataRows = [];

$(document).ready(function() {
    setupData(function() {
        updateData(0, function() {loadGraph()});
    });
    $("#monthDropdown").on("change", function() {

        var month = parseInt($(this).val());
        console.log(month);

        updateData(month, function() {loadGraph()});

   })
});

function setupData(callback) {
    $.getJSON('./data/sampleSDGEData.json', function(json) {
            var data = json.data;
            var index = 0;
            $.each(data, function(key, value) {
                var monthExpression = value.month;
                if(index == 0) monthExpression += " (Current)"
                $('#monthDropdown').append("<option value ='" + index + "'>" + monthExpression + "</option>");
                index++;
            });
            callback();
    });
}
function updateData(index, callback) {
    $.getJSON('./data/sampleSDGEData.json', function(json) {
            var currIndex = 0;
            $.each(json.data, function(key, value) {
                if(currIndex === index) {
                    var userRow = 0;
                    $.each(value.statistics, function(key2, stat) {
                            //fill in dataRows
                            dataRows[userRow] = [];
                            dataRows[userRow][0] = stat.electricity;
                            dataRows[userRow][1] = stat.water;
                            dataRows[userRow][2] = stat.gas;

                            userRow++;
                    });
                }
                currIndex++;
            });

            callback();
        }
    );
}

function loadGraph() {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

function drawChart() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Type of Bill');
    data.addColumn('number', 'You');
    data.addColumn('number', 'City');
    data.addColumn('number', 'State');
    data.addRows([
        ['Electricity', dataRows[0][0], dataRows[1][0], dataRows[2][0]],
        ['Water', dataRows[0][1], dataRows[1][1], dataRows[2][1]],
        ['Gas', dataRows[0][2], dataRows[1][2], dataRows[2][2]]
    ]);

    var w = window,
            d = document,
            e = d.documentElement,
            g = d.getElementsByTagName('body')[0],
            windowWidth = w.innerWidth || e.clientWidth || g.clientWidth,
            windowHeight = w.innerHeight || e.clientHeight || g.clientHeight;
    var options = {title: 'Energy Comparison',
                    vAxis: {format: 'currency'}};
    var chart = new google.visualization.ColumnChart(document.getElementById('energy_div'));
    chart.draw(data, options);


}