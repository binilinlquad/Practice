package com.gandan.practice.rx

import rx.AsyncEmitter
import rx.Observable
import rx.Subscription
import rx.functions.Action1
import java.io.BufferedInputStream

fun main(args: Array<String>) {
    val innerSubscription = object : Subscription {
        var flag = false
        override fun isUnsubscribed(): Boolean {
            return flag
        }

        override fun unsubscribe() {
            System.`in`.close()
            flag = true
        }
    }

    val asyncIn = Observable.fromAsync<String>({ emitter: AsyncEmitter<String> ->
        emitter.run {
            // prepare onNext, onError, and onComplete before setting subscription to prevent too early unsubscribe
            System.`in`.apply {
                try {
                    var counter = 0
                    do {
                        if (!innerSubscription.isUnsubscribed && counter < requested()) {
                            readLine()?.run {
                                if (this != "exit") {
                                    onNext(this)
                                    counter++
                                } else {
                                    innerSubscription.unsubscribe()
                                    onCompleted()
                                    return@fromAsync
                                }
                            } ?: innerSubscription.unsubscribe()
                        }
                    } while (true)
                } catch (e: Throwable) {
                    onError(e)
                }
            }

            // do not set subscription before onNext implementation
            setSubscription(innerSubscription)

            setCancellation { System.`in`.close() }

            this
        }
    }, AsyncEmitter.BackpressureMode.LATEST)

    asyncIn.subscribe { System.out.println(it) }
}