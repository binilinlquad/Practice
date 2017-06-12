package com.gandan.practice.rx

import rx.Observable
import rx.schedulers.Schedulers

/**
 * Simple usage of materialize for logging
 */
fun main(args: Array<String>) {
    Observable.range(1, 10)
            .compose { t -> t.apply { materialize()
                    .doOnCompleted { System.out.println("Complete") }
                    .subscribe { System.out.println("${it.kind} ${it.value}") } } }
            .subscribe { System.out.println(it) }

    Observable.range(20, 10)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .compose { t -> t.apply { materialize()
                    .doOnCompleted { System.out.println("Complete") }
                    .subscribe { System.out.println("${it.kind} ${it.value}") } } }
            .subscribe { System.out.println(it) }


    Observable.range(30, 10)
            .subscribeOn(Schedulers.newThread())
            .compose { t -> t.apply { materialize()
                    .doOnCompleted { System.out.println("Complete") }
                    .subscribe { System.out.println("${it.kind} ${it.value}") } } }
            .observeOn(Schedulers.newThread())
            .subscribe { System.out.println(it) }

}

