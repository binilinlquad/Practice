package com.gandan.practice.rx

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Try autoconnect with some scenario
 */
fun main(args: Array<String>) {
    System.out.println("Autoconnect will make hot observable start emit after amount of subscriber is reached")

    val source = Observable.interval(500, TimeUnit.MILLISECONDS).publish().autoConnect(3)

    val sub1 = source.subscribe {
        System.out.println("Subsriber 1 $it")
    }
    System.out.println("Subcribe 1")

    val sub2 = source.subscribe {
        System.out.println("Subsriber 2 $it")
    }
    System.out.println("Subcribe 2")

    val sub3 = Observable.just("a", "b").switchMap { source }.subscribe {
        System.out.println("Subsriber 3 $it")
    }
    System.out.println("Subcribe 3")
    Thread.sleep(2000)

    System.out.println("Observable will keep emitting eventhough one subscriber is dismissed")
    sub3.dispose()
    System.out.println("Subcriber 3 disposed")
    Thread.sleep(2000)

    System.out.println("Observable stop when no subscriber")
    sub1.dispose()
    sub2.dispose()
    System.out.println("Subcriber 1 and 2 are disposed")
    Thread.sleep(2000)

}