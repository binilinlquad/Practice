package com.gandan.practice.rx

import rx.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val oo = Observable.interval(100, TimeUnit.MILLISECONDS)
    val hotReplay = oo.replay()

    hotReplay.subscribe { System.out.println("First Subscriber $it")}
    hotReplay.subscribe { System.out.println("Second Subscriber $it")}
    hotReplay.connect()
    Thread.sleep(500)
    hotReplay.subscribe { System.out.println("Third Subscriber $it")}

    Thread.sleep(1000) // waiting for 1 seconds
}