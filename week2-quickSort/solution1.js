
"use strict";

var LineByLineReader = require('../node_modules/line-by-line'),
    lr = new LineByLineReader('100.txt');

var array = [];
var comparisionCount= 0;

lr.on('error', function (err) {
    console.log(err);
});

lr.on('line', function (line) {
    // 'line' contains the current line without the trailing newline character.
    if (!isNaN(line)) {
        array.push(parseInt(line));
    }
});

lr.on('end', function () {
    console.log(quicksort(array));
    console.log('comparision count: ', comparisionCount);
});

function quicksort(arr) {
    if (arr.length <= 1) {
        return arr;
    }
    let pivot = arr[0];
    let n = arr.length;
    var i = 1;
    for (let j = i; j < n; j++) {
        if (arr[j] < pivot) {
            let temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;
            i++;
        }
    }
    let temp = arr[i - 1];
    arr[i - 1] = pivot;
    arr[0] = temp;

    let less = arr.slice(0, i - 1);
    let greater = arr.slice(i, n);

    comparisionCount += (arr.length - 1);

    return quicksort(less).concat([pivot]).concat(quicksort(greater));
    return [];
}







