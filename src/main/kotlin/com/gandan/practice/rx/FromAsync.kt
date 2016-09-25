package com.gandan.practice.rx

import rx.AsyncEmitter
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import java.io.BufferedInputStream

fun main(args: Array<String>) {


    val asyncIn = Observable.fromEmitter<String>({ emitter: AsyncEmitter<String> ->
        emitter.run {

            // prepare onNext, onError, and onComplete before setting subscription to prevent too early unsubscribe
            System.`in`.apply {
                try {
                    var counter = 0
                    do {
                        readLine()?.run {
                            if (this != "exit") {
                                onNext(this)
                                counter++
                            } else {
                                onCompleted()
                                return@fromEmitter
                            }
                        } ?: onCompleted()
                    } while (true)
                } catch (e: Throwable) {
                    onError(e)
                }
            }

            setCancellation { System.`in`.close() }

            this
        }
    }, AsyncEmitter.BackpressureMode.LATEST)

    asyncIn.subscribe { System.out.println(it) }
}