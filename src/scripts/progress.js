//dataRows is going to be something like this:
//[users [months]]
var dataRows = [];
var monthsList = [];
var users = ['You', 'City', 'State'];

$(document).ready(function() {
    setUpDataRows("overall", loadGraph);
    $('#billDropdown').on("change", function() {
            var billType = $(this).val();

            setUpDataRows(billType, loadGraph);
    });
});


function setUpDataRows(type, callback) {
    dataRows = [];
    if(type == "water" || type == "electricity" || type == "gas") {
        //Do individual stats
        //Game plan: Iterate through each month, iterate through each stat in each month, end up with
        //dataRows[3][monthNumber], dataRows[userNumber][monthNumber] = stat[type]
        $.getJSON('./data/sampleSDGEData.json', function(json) {
            //Month number starts at zero
            var monthNumber = 0;
            $.each(json.data, function(key, month) {
                //Iterate through each month
                //Every month stat number starts at zero
                monthsList[monthNumber] = month.month;
                var userNumber = 0;
                $.each(month.statistics, function(key2, stat) {
                    //Iterate through each statistic
                    if(dataRows[userNumber] == null) {
                        dataRows[userNumber] = [];
                    }
                    dataRows[userNumber][monthNumber] = stat[type];
                    userNumber++;
                });
                //Go onto next month
                monthNumber++;
            });
            callback();
        });
    }
    else {
        //Do overall
        //Game plan: Iterate through each month, iterate through each stat in each month
        //          Append each type
        $.getJSON('./data/sampleSDGEData.json', function(json) {
            //Month number starts at zero
            var monthNumber = 0;
            $.each(json.data, function(i, month) {
                monthsList[monthNumber] = month.month;
                var userNumber = 0;
                $.each(month.statistics, function(j, stat) {
                   if(dataRows[userNumber] == null) {
                        dataRows[userNumber] = [];
                    }
                    dataRows[userNumber][monthNumber] = stat['electricity'] + stat['water'] + stat['gas'];
                    userNumber++;
                });
                monthNumber++;
            });
            callback();
        });
    }
}

function loadGraph() {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

function drawChart() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'User');
    data.addColumn('number', 'You');
    data.addColumn('number', 'City');
    data.addColumn('number', 'State');
    //Input all row data, adding every month to a row
    //then putting row in data table
    for(var mon = monthsList.length - 1; mon >= 0; mon--) {
        var row = [];
        row[0] = monthsList[mon];
        for(var usr = 0; usr < users.length; usr++) {
            row[usr+1] = dataRows[usr][mon];
        }
        data.addRow(row);
    }

    var options = {title: 'Energy Comparison',
                    vAxis: {format: 'currency'}};
    var chart = new google.visualization.LineChart(document.getElementById('energy_div'));
    chart.draw(data, options);
}

function printDataRows() {
    for(var i = 0; i < dataRows[i].length; i++) {
        var s = "";
        for(var j = 0; j < dataRows.length; j++) {
            s += dataRows[j][i] + ", ";
        }
        console.log(s);
    }
}