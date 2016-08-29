package com.gandan.practice.rx

import rx.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val a100 = Observable.timer(100, TimeUnit.MILLISECONDS).map { "a $it" }.doOnNext { "a onNext" }
    val b500 = Observable.timer(100, TimeUnit.MILLISECONDS).map { Thread.sleep(400); "b $it" }.doOnNext { "b onNext" }
    // it will only print a 0
    Observable.amb(a100, b500).subscribe { System.out.println("$it")}

    Thread.sleep(5000)
}