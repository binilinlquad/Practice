package com.gandan.practice.rx

import rx.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    Observable.interval(10, TimeUnit.SECONDS)
            .timeout { Observable.timer(5, TimeUnit.SECONDS) }
            .toBlocking()
            .subscribe { System.out.println(it) }
}