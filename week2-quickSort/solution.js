"use strict";

var LineByLineReader = require('../node_modules/line-by-line'),
    lr = new LineByLineReader('1000.txt');

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
    // quicksort1(array);
    // console.log(comparisionCount);

    // quicksort2(array);
    // console.log(comparisionCount);

    // quicksort3(array);
    // console.log(comparisionCount);
});

// use the first element of the array as the pivot element.
function quicksort1(arr) {
    if (arr.length <= 1) {
        return arr;
    }

    let n = arr.length;
    // choose the first element as pivot
    let pivot = arr[0];

    let partitioned = partition(arr);
    let less = partitioned.less;
    let greater = partitioned.greater;

    comparisionCount += (n - 1);
    return quicksort1(less).concat([pivot]).concat(quicksort1(greater));
}

// use the last element of the array as the pivot element.
function quicksort2(arr) {
    if (arr.length <= 1) {
        return arr;
    }

    let n = arr.length;
    // chosing the last element as pivot
    let temp = arr[n - 1];
    arr[n - 1] = arr[0];
    arr[0] = temp;
    let pivot = arr[0];

    let partitioned = partition(arr);
    let less = partitioned.less;
    let greater = partitioned.greater;

    comparisionCount += (n - 1);
    return quicksort2(less).concat([pivot]).concat(quicksort2(greater));
}


// Compute the number of comparisons (as in Problem 1)
// using the "median-of-three" pivot rule
function quicksort3(arr) {
    let n = arr.length;
    
    if (n <= 1) {
        return arr;
    }

    //finding the meadian of the first, middle, and last elements
    let pivotIndex = findMedian(arr);

    // swapping the first element with the median element
    let temp = arr[pivotIndex];
    arr[pivotIndex] = arr[0];
    arr[0] = temp;

    // choosing the first element as pivot
    let pivot = arr[0];

    let partitioned = partition(arr);
    let less = partitioned.less;
    let greater = partitioned.greater;
    console.log('here', comparisionCount);
    comparisionCount += (n - 1);
    return quicksort3(less).concat([pivot]).concat(quicksort3(greater));
}

function partition(arr) {
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

    return {
        less: less,
        greater: greater
    };
}

function findMedian(arr) {
    let n = arr.length;
    let i = Math.floor((n - 1) / 2);
    let first = arr[0];
    let middle = arr[i];
    let last = arr[n - 1];

    let pivotIndex = 0;
    if (first > middle) {
        if (middle > last)
            pivotIndex = i;
        else if (last > first)
            pivotIndex = 0;
        else
            pivotIndex = n - 1;
    } else { // middle > first
        if (last > middle)
            pivotIndex = i;
        else if (first > last)
            pivotIndex = 0;
        else
            pivotIndex = n - 1;
    }
    return pivotIndex;
}



