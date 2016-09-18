package com.gandan.practice.rx

import rx.Observable
import rx.Subscriber

fun main(args: Array<String>) {
    /* step 93 cannot be save into Long */
    fib(92).take(80).subscribe { num ->
        System.out.println(num)
    }
}

fun fib(step: Long): Observable<Long> {
    return Observable.create(object : Observable.OnSubscribe<Long> {
        var x: Long = 0
        var y: Long = 1
        override fun call(subscriber: Subscriber<in Long>?) {
            subscriber?.onStart()
            for (i in 0..step) {
                if (subscriber?.isUnsubscribed ?: true) {
                    subscriber?.onCompleted()
                    return
                }

                val result = x + y
                if (x < 0) {
                    subscriber?.onError(RuntimeException("Requested step $i too big for Long"))
                } else {
                    subscriber?.onNext(x)
                    x = y
                    y = result
                }
                /*
                91 4660046610375530309
                92 7540113804746346429
                93 12200160415121876738
                Long.MAX_VALUE 9223372036854775807
                */
            }
        }
    })
}

