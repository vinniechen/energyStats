var typeOfBillList = ["water", "gas", "electricity"];
var highlightsList = [];
var subjectiveTips = [typeOfBillList.length];
var billTipsList = [];

$(document).ready(function() {
    if(highlightsList.length == 0) {
        gatherHighlightsData(function() {
            loadTipsFromDB(function() {
                loadSubjectiveTips(function() {
                    applyTips();
                });
            });
        });
    }
});

function loadTipsFromDB(callback) {
    $.getJSON('./data/tipsData.json', function(json) {
        var tips = json.tips;
        $.each(typeOfBillList, function(key, type) {
            var tipsArray = tips[type];
            billTipsList[key] = [];
            billTipsList[key] = tipsArray;
        });
        callback();
    });
}

function loadSubjectiveTips(callback) {
    loadTipsFromDB(function() {
        for(var i = 0; i < typeOfBillList.length; i++) {
                subjectiveTips[i] = [];
                if(highlightsList[i] > 0) {
                    //praise
                    subjectiveTips[i][0] = "<b>You're doing better than averages! Keep it up!</b>"
                }
                else if(highlightsList[i] == 0) {
                    //keep up
                    subjectiveTips[i][0] = "<b>You're currently keeping up with the averages.</b>";
                }
                else {
                        //console.log(specificBillTipsList);
                        subjectiveTips[i][0] = "<b>You're behind on the current average: </b>";
                        for(var j = 0; j < 3; j++) {
                            var randomIndex = 0;
                            while(billTipsList[i][randomIndex] === "0") {
                                randomIndex = Math.floor(Math.random()*billTipsList[i].length);
                            }

                            subjectiveTips[i][j+1] = billTipsList[i][randomIndex];
                            billTipsList[i][randomIndex] = "0";
                        }
                }


        }

        callback();
    });

}

function printArray(array) {
    for(var i = 0; i < array.length; i++) {
        console.log(array[i]);
    }
}

function gatherHighlightsData(callback) {
    var listOfStats = [];
    $.getJSON('./data/sampleSDGEData.json', function(json) {
        var currentMonthData = json.data[0];
        console.log(currentMonthData);
        $.each(typeOfBillList, function(index, typeOfBill) {
            listOfStats = [];
            $.each(json.data[0]["statistics"], function(key, statField){
                listOfStats[key] = statField[typeOfBill];
                key++;
            });
            var stateCityAverage = (listOfStats[1]+listOfStats[2])/2;
            highlightsList[index] = listOfStats[0] - stateCityAverage;

        });
        callback();
    });
}

function applyTips() {
    $.each(typeOfBillList, function(i1, typeOfBill) {
        if(typeOfBill === localStorage.getItem('tipChosen')) {
            $("#" + typeOfBill).css({
                "backgroundColor": '#6CC417',
                "color": "white",
                "margin": "15px",
                "padding-bottom": "15px",
                "padding-top": "1px",
            });
            $("#" + typeOfBill + " .tipCategoryTitle").append("<b>" + capitalizeFirstLetter(typeOfBill) + "</b>");
        }
        else {
            $("#" + typeOfBill + " .tipCategoryTitle").append(capitalizeFirstLetter(typeOfBill));
        }


        if(subjectiveTips[i1].length > 1) {
            $("#" + typeOfBill).append("<ul>");
            $.each(subjectiveTips[i1], function(i2, subjectiveTip) {
                if(i2 == 0) {
                     $("#"+typeOfBill).append(subjectiveTip + "<br/>");
                }
                else $("#"+typeOfBill).append("<li>" + subjectiveTip + "</li>");
            });
            $("#" + typeOfBill).append("</ul>")
        }
        else {
            $.each(subjectiveTips[i1], function(i2, subjectiveTip) {
                $("#"+typeOfBill).append(subjectiveTip + "<br/>");
            });
        }

        if(typeOfBill === localStorage.getItem('tipChosen')) {
            $("#" + typeOfBill).append("<a href = 'comparisons.html'> <hr/>Return to Graph </a>");
        }


    });
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}