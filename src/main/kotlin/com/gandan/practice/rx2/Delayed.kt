package com.gandan.practice.rx2

import com.gandan.practice.common.log
import rx.Observable
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    /**
     * It will delay each source emitted value until there is emitted value
     * the it will emit the source emitted value
     */

    System.out.println("==== Delay source emitted value ===")
    val one = Observable.interval(1, TimeUnit.SECONDS)
            .map { "source : ${System.currentTimeMillis()}" }
            .log()
            .delay { Observable.timer(2, TimeUnit.SECONDS)
                    .map { "timer : ${System.currentTimeMillis()}" }
                    .log() }
            .subscribe {
                System.out.println("Result : $it at ${System.currentTimeMillis()}")
            }

    Thread.sleep(10000)
    one.unsubscribe()

    /**
     * It will only delay subscription of source observable
     */

    System.out.println("==== Delay Source Subscription ===")
    Observable.interval(1, TimeUnit.SECONDS)
            .map { "source : ${System.currentTimeMillis()}" }
            .log()
            .delaySubscription { Observable.timer(2, TimeUnit.SECONDS)
                    .map { "timer : ${System.currentTimeMillis()}" }
                    .log() }
            .subscribe {
                System.out.println("Result : $it at ${System.currentTimeMillis()}")
            }
    Thread.sleep(10000)

}