package com.gandan.practice.rx

import rx.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val o = Observable.interval(100, TimeUnit.MILLISECONDS)
    val hot = o.publish().refCount()

    hot.subscribe { System.out.println("First Subscriber $it")}
    hot.subscribe { System.out.println("Second Subscriber $it")}
    Thread.sleep(500)
    hot.subscribe { System.out.println("Third Subscriber $it")}

    Thread.sleep(1000) // waiting for 1 seconds
}