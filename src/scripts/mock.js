function findMax(arr) {
    var max = 0;
    for(var i = 0; i < arr.length; i++) {
        if(max < arr[i]) max = arr[i];
    }
    return max;
}

function rgb(r, g, b) {
    return "rgb("+r+","+g+","+b+")";
}