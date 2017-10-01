package com.gandan.practice.rx

import com.gandan.practice.common.log
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * AMB will subscribe to faster observable emitting
 */
fun main(args: Array<String>) {
    val a100 = Observable.timer(100, TimeUnit.MILLISECONDS).map { "a $it" }.log()
    val b500 = Observable.timer(100, TimeUnit.MILLISECONDS).map { Thread.sleep(400); "b $it" }.log()
    // it will only print a 0
    Observable.amb(a100, b500).subscribe { System.out.println(it)}

    Thread.sleep(5000)
}