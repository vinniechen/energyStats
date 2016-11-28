var dataRows = [];
var maxHeight = 0;

$(document).ready(function() {
    setupData(function() {
        updateData(0, function() {loadGraph()});
    });
    $("#monthDropdown").on("change", function() {

        var month = parseInt($(this).val());
        console.log(month);

        updateData(month, function() {loadGraph()});

   })
    document.getElementById('energy_div').addEventListener('click', clickedEnergyDiv, false);
});

function setupData(callback) {
    $.getJSON('./data/sampleSDGEData.json', function(json) {
            var data = json.data;
            var index = 0;
            $.each(data, function(key, value) {
                var monthExpression = value.month;
                if(index == 0) monthExpression += " (Current)"
                $('#monthDropdown').append("<option value ='" + index + "'>" + monthExpression + "</option>");
                $.each(value.statistics, function(key2, stat) {
                        if(stat.electricity > maxHeight) maxHeight = stat.electricity;
                        else if (stat.water > maxHeight) maxHeight = stat.water;
                        else if(stat.gas > maxHeight) maxHeight = stat.gas;
                });
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
    data.addColumn('string', 'Link');
    data.addRows([
        ['Electricity', dataRows[0][0], dataRows[1][0], dataRows[2][0],
      'www.google.com'],
        ['Water', dataRows[0][1], dataRows[1][1], dataRows[2][1],
      'www.google.com'],
        ['Gas', dataRows[0][2], dataRows[1][2], dataRows[2][2],
      'www.google.com']
    ]);

    var view = new google.visualization.DataView(data);
    view.setColumns([0, 1, 2, 3]);

    var w = window,
            d = document,
            e = d.documentElement,
            g = d.getElementsByTagName('body')[0],
            windowWidth = w.innerWidth || e.clientWidth || g.clientWidth,
            windowHeight = w.innerHeight || e.clientHeight || g.clientHeight;
    var options = {title: 'Energy Comparison',
                    vAxis: {format: 'currency',
                  viewWindow: {max: maxHeight + 10, min: 0}}};

    var chart = new google.visualization.ColumnChart(document.getElementById('energy_div'));
    chart.draw(view, options);

    function selectHandler() {
        var selectedItem = chart.getSelection()[0];
        switch(selectedItem.row) {
            case 0:
                changeTipChosen('electricity');
                break;
            case 1:
                changeTipChosen('water');
                break;
            case 2:
                changeTipChosen('gas');
                break;
            default:
                break;
        }
        if(selectedItem.row != null) {
            window.location = 'highlights.html';
        }

      }


    // Add our selection handler.
    google.visualization.events.addListener(chart, 'select', selectHandler);


}
