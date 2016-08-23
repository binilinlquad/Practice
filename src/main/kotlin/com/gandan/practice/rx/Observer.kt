package com.gandan.practice.rx

import rx.Observable
import rx.Observer
import java.io.BufferedInputStream

fun main(args: Array<String>) {
    val input = Observable.create<String> {
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
    }.publish()

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
    }.apply { input.subscribe({ onNext(it) }, { onError(it) }, { onCompleted() }) }

    input.connect()
}