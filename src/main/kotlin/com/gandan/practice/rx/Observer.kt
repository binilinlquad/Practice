package com.gandan.practice.rx

import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.schedulers.Schedulers
import java.io.BufferedInputStream

/**
 * This sample will wait user input before doing plus operation with previous value
 */
fun main(args: Array<String>) {
    val input = Observable.create<String> {
        // blocking read from input
        BufferedInputStream(System.`in`).run {
            try {
                it.onStart()
                do {
                    if (!it.isUnsubscribed) {
                        readLine().run {
                            if (this != "Finish") {
                                it.onNext(this)
                            } else {
                                it.onCompleted()
                            }
                        }
                    }
                } while (true)
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }

    /* Observer instantiation */
    val observer = object : Observer<String> {
        override fun onCompleted() {
            System.out.println("Completes")
        }

        override fun onError(e: Throwable?) {
            System.out.println("Error")
        }

        override fun onNext(t: String?) {
            t?.run { System.out.println(t) }
        }
    }

    /* call observer in subscriber */
    input.map { it.toInt() }
            .scan(0, { s, s_ -> s + s_ })
            .subscribe({ observer.onNext(it.toString()) },
                    { observer.onError(it) },
                    { observer.onCompleted() })
}