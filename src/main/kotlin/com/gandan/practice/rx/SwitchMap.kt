package com.gandan.practice.rx

import rx.Observable

fun main(args: Array<String>) {
    Observable.range(1, 100)
    .switchMap {
        if (it and 1 == 1) {
            Observable.just("Odd")
        } else {
            Observable.just("Event")
        }
    }
            .subscribe{ System.out.println(it)}
}