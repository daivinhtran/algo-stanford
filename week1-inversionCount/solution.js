
"use strict";

var LineByLineReader = require('../node_modules/line-by-line'),
    lr = new LineByLineReader('input.txt');

var array = [];

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
    mergesort(array);
    console.log('inversionCount', inversionCount);
});

var inversionCount = 0;

function mergesort(A) {
    if (A.length <= 1) {
        return A;
    }

    let n = A.length;
    let lo = 0;
    let mid = n/2;
    let hi = n - 1;

    let B = A.slice(lo, mid);
    let C = A.slice(mid, hi + 1);

    B = mergesort(B);
    C = mergesort(C);

    let D = [];
    let i = 0;
    let j = 0;
    for (let k = 0; k < n; k++) {
        if (i >= B.length) {
            D[k] = C[j++];
        } else if (j >= C.length) {
            D[k] = B[i++];
        }
        else if (B[i] < C[j]) {
            D[k] = B[i++];
        } else {
            inversionCount += (B.length - i);
            D[k] = C[j++];
        }
    }
    return D;
}





