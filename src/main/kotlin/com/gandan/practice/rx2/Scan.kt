package com.gandan.practice.rx2

import com.gandan.practice.common.log
import io.reactivex.Flowable
import io.reactivex.Observable

fun main(args: Array<String>) {

    // Flowable scan has different behavior with Observable scan with initial value behavior,
    // it will emit first item when publisher emit its first value
    System.out.println("Flowable")
    Flowable.fromArray(1L, 2L, 3L)
            .log<Long>()
            .scan(emptyList<Long>(), { list, value -> list + value })
            .doOnNext { System.out.println("$it") }.subscribe()


    // Flowable scan without initial value will work like Observable scan with initial value
    System.out.println("Flowable scan without initial value")
    Flowable.fromArray(1L, 2L, 3L)
            .map { listOf(it) }
            .log<List<Long>>()
            .scan({ list, value -> list + value })
            .doOnNext { System.out.println("$it") }.subscribe()

    System.out.println("Observable")

    // Observable scan with initial value will emit initial value when subscribed
    Observable.fromArray(1L, 2L, 3L)
            .log<Long>()
            .scan(emptyList<Long>(), { list, value -> list + value })
            .doOnNext { System.out.println("$it") }.subscribe()

    Thread.sleep(5000)
}