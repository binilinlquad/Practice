package com.gandan.practice.rx

import rx.Observable

fun main(args: Array<String>) {
    // There are 100 closed doors and you will :
    // open if it close
    // close if it open
    // First for each door,
    // then repeat from the start but now for per 2 door
    // then repeat from the start but now for per 3 door
    // after 100 repeating, show opened doors
    // only print opened door in last step

    // naive
    System.out.println("Naive")
    Observable.range(1, 100)
            .flatMap { e ->
                Observable.range(e, 100 - e + 1)
                        .filter { it % e == 0 }
            }
            .reduce(Array<Boolean>(100, { p -> false }), { a, b -> a[b - 1] = !a[b - 1]; a })
            .map { it.mapIndexed { i, b -> if (b) i + 1 else -1 } }
            .flatMapIterable { it.asIterable() }
            .filter { it > 0 }
            .subscribe { System.out.print("$it ") }

    // doors will be only opened for doors which has odd factorize
    System.out.println()
    System.out.println("Optimal")
    Observable.range(1, 10).map { it*it }.subscribe { System.out.print("$it ") }
}