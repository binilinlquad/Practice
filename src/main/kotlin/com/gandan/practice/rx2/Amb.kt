package com.gandan.practice.rx2

import com.gandan.practice.common.log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Amb will only emit event from fastest-emitting source. Except the fastest-emiting source, every sources
 * will be disposed
 *
 */
fun main(args: Array<String>) {
    val source1 = Observable.interval(100, TimeUnit.MILLISECONDS).map { "A$it" }.log("A")
    val source2 = Observable.interval(50, TimeUnit.MILLISECONDS).map { "B$it" }.log("B")
    val source3 = Observable.interval(200, TimeUnit.MILLISECONDS).map { "B$it" }.log("C")

    Observable.amb(listOf(source1, source2, source3))
            .subscribe { System.out.println("$it") }

    Thread.sleep(3000)

    //    Sample result
    //---------------------
    //    B Subscribed
    //    C Subscribed
    //    B onNext: B0
    //    A Disposed
    //    C Disposed
    //    B0
    //    B onNext: B1
    //    B1
    //    B onNext: B2
    //    B2
    //    B onNext: B3
    //    B3
}

