package com.gandan.practice.rx

import rx.Observable

fun main(args: Array<String>) {
    Observable.range(1, 10)
            .compose { t -> t.apply { materialize()
                    .doOnCompleted { System.out.println("Complete") }
                    .subscribe { System.out.println("${it.kind} ${it.value}") } } }
            .subscribe { System.out.println(it) }
}

