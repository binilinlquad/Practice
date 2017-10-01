package com.gandan.practice.common

import rx.Notification
import rx.Observable

fun <T> Observable<T>.log() : Observable<T> {
    return materialize().doOnNext {
        when (it.kind!!) {
            Notification.Kind.OnNext -> System.out.println(it.value)
            Notification.Kind.OnError -> System.err.println(it.value)
            Notification.Kind.OnCompleted -> System.out.println("Completed")
        }
    }
            .dematerialize<T>()
}
